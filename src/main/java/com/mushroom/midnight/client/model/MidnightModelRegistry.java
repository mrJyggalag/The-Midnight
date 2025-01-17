package com.mushroom.midnight.client.model;

import com.mushroom.midnight.client.gui.CacheScreen;
import com.mushroom.midnight.client.render.BladeshroomCapRenderer;
import com.mushroom.midnight.client.render.CacheTileEntityRenderer;
import com.mushroom.midnight.client.render.CloudRenderer;
import com.mushroom.midnight.client.render.CrystalBugRenderer;
import com.mushroom.midnight.client.render.DeceitfulSnapperRenderer;
import com.mushroom.midnight.client.render.HunterRenderer;
import com.mushroom.midnight.client.render.MidnightChestTileEntityRenderer;
import com.mushroom.midnight.client.render.NightStagRenderer;
import com.mushroom.midnight.client.render.NovaRenderer;
import com.mushroom.midnight.client.render.NovaSpikeRenderer;
import com.mushroom.midnight.client.render.PenumbrianRenderer;
import com.mushroom.midnight.client.render.RiftRenderer;
import com.mushroom.midnight.client.render.RifterRenderer;
import com.mushroom.midnight.client.render.ShadeSquirrelRenderer;
import com.mushroom.midnight.client.render.SkulkRenderer;
import com.mushroom.midnight.client.render.StingerRenderer;
import com.mushroom.midnight.client.render.TreeHopperRenderer;
import com.mushroom.midnight.common.entity.CloudEntity;
import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.entity.creature.CrystalBugEntity;
import com.mushroom.midnight.common.entity.creature.DeceitfulSnapperEntity;
import com.mushroom.midnight.common.entity.creature.HunterEntity;
import com.mushroom.midnight.common.entity.creature.NightStagEntity;
import com.mushroom.midnight.common.entity.creature.NovaEntity;
import com.mushroom.midnight.common.entity.creature.PenumbrianEntity;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import com.mushroom.midnight.common.entity.creature.ShadeSquirrelEntity;
import com.mushroom.midnight.common.entity.creature.SkulkEntity;
import com.mushroom.midnight.common.entity.creature.StingerEntity;
import com.mushroom.midnight.common.entity.creature.TreeHopperEntity;
import com.mushroom.midnight.common.entity.projectile.BladeshroomCapEntity;
import com.mushroom.midnight.common.entity.projectile.NovaSpikeEntity;
import com.mushroom.midnight.common.entity.projectile.SporeBombEntity;
import com.mushroom.midnight.common.entity.projectile.ThrownGeodeEntity;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightContainers;
import com.mushroom.midnight.common.tile.base.CacheTileEntity;
import com.mushroom.midnight.common.tile.base.MidnightChestTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class MidnightModelRegistry {
    private static final Minecraft MC = Minecraft.getInstance();

    private static final int DEFAULT_GRASS_COLOR = 0xBF8ECC;
    private static final int DEFAULT_FOLIAGE_COLOR = 0x8F6DBC;

    public static void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(RiftEntity.class, RiftRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(RifterEntity.class, RifterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HunterEntity.class, HunterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BladeshroomCapEntity.class, BladeshroomCapRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NovaEntity.class, NovaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CrystalBugEntity.class, CrystalBugRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(PenumbrianEntity.class, PenumbrianRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TreeHopperEntity.class, TreeHopperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(StingerEntity.class, StingerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NightStagEntity.class, NightStagRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DeceitfulSnapperEntity.class, DeceitfulSnapperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SkulkEntity.class, SkulkRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ShadeSquirrelEntity.class, ShadeSquirrelRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ThrownGeodeEntity.class, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(SporeBombEntity.class, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(CloudEntity.class, CloudRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NovaSpikeEntity.class, NovaSpikeRenderer::new);
        ClientRegistry.bindTileEntitySpecialRenderer(MidnightChestTileEntity.class, new MidnightChestTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(CacheTileEntity.class, new CacheTileEntityRenderer());

        ScreenManager.registerFactory(MidnightContainers.CACHE, CacheScreen::new);

        BlockColors blockColors = MC.getBlockColors();
        ItemColors itemColors = MC.getItemColors();

        blockColors.register(MidnightModelRegistry::computeGrassColor, MidnightBlocks.GRASS_BLOCK);
        itemColors.register(MidnightModelRegistry::defaultGrassColor, MidnightBlocks.GRASS_BLOCK);

        blockColors.register(MidnightModelRegistry::computeFoliageColor, MidnightBlocks.SHADOWROOT_LEAVES);
        itemColors.register(MidnightModelRegistry::defaultFoliageColor, MidnightBlocks.SHADOWROOT_LEAVES);

        blockColors.register(MidnightModelRegistry::computeGrassColor, MidnightBlocks.GRASS, MidnightBlocks.TALL_GRASS);
        itemColors.register(MidnightModelRegistry::defaultGrassColor, MidnightBlocks.GRASS, MidnightBlocks.TALL_GRASS);
    }

    private static int computeGrassColor(BlockState state, IEnviromentBlockReader world, BlockPos pos, int tintIndex) {
        if (world == null || pos == null || !isMidnight()) {
            return DEFAULT_FOLIAGE_COLOR;
        }
        return BiomeColors.getGrassColor(world, pos);
    }

    private static int defaultGrassColor(ItemStack stack, int tintIndex) {
        return DEFAULT_GRASS_COLOR;
    }

    private static int computeFoliageColor(BlockState state, IEnviromentBlockReader world, BlockPos pos, int tintIndex) {
        if (world == null || pos == null || !isMidnight()) {
            return DEFAULT_FOLIAGE_COLOR;
        }
        return BiomeColors.getFoliageColor(world, pos);
    }

    private static int defaultFoliageColor(ItemStack stack, int tintIndex) {
        return DEFAULT_FOLIAGE_COLOR;
    }

    private static boolean isMidnight() {
        return Helper.isMidnightDimension(MC.world);
    }
}
