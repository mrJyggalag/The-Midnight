package com.mushroom.midnight.client.model;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.render.BladeshroomCapRenderer;
import com.mushroom.midnight.client.render.CrystalBugRenderer;
import com.mushroom.midnight.client.render.DeceitfulSnapperRenderer;
import com.mushroom.midnight.client.render.HunterRenderer;
import com.mushroom.midnight.client.render.NightStagRenderer;
import com.mushroom.midnight.client.render.NovaRenderer;
import com.mushroom.midnight.client.render.PenumbrianRenderer;
import com.mushroom.midnight.client.render.RiftRenderer;
import com.mushroom.midnight.client.render.RifterRenderer;
import com.mushroom.midnight.client.render.SkulkRenderer;
import com.mushroom.midnight.client.render.StingerRenderer;
import com.mushroom.midnight.client.render.TreeHopperRenderer;
import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.entity.creature.CrystalBugEntity;
import com.mushroom.midnight.common.entity.creature.DeceitfulSnapperEntity;
import com.mushroom.midnight.common.entity.creature.HunterEntity;
import com.mushroom.midnight.common.entity.creature.NightStagEntity;
import com.mushroom.midnight.common.entity.creature.NovaEntity;
import com.mushroom.midnight.common.entity.creature.PenumbrianEntity;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import com.mushroom.midnight.common.entity.creature.SkulkEntity;
import com.mushroom.midnight.common.entity.creature.StingerEntity;
import com.mushroom.midnight.common.entity.creature.TreeHopperEntity;
import com.mushroom.midnight.common.entity.projectile.BladeshroomCapEntity;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Dist.CLIENT)
public class MidnightModelRegistry {
    private static final Minecraft MC = Minecraft.getInstance();

    private static final int DEFAULT_GRASS_COLOR = 0xBF8ECC;
    private static final int DEFAULT_FOLIAGE_COLOR = 0x8F6DBC;

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {
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
        // TODO RenderSnowball code
        /*RenderingRegistry.registerEntityRenderingHandler(ThrownGeodeEntity.class, manager -> new RenderSnowball<>(manager, MidnightItems.GEODE, Minecraft.getInstance().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(SporeBombEntity.class, manager -> new RenderSnowball<SporeBombEntity>(manager, MidnightItems.SPORE_BOMB, Minecraft.getInstance().getRenderItem()) {
            @Override
            @Nonnull
            public ItemStack getStackToRender(SporeBombEntity entity) {
                return entity.getBombStack();
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(CloudEntity.class, CloudRenderer::new);

        TileEntityMidnightChest shadowrootChest = new TileEntityMidnightChest(ChestModel.SHADOWROOT);
        Item.getItemFromBlock(MidnightBlocks.SHADOWROOT_CHEST).setTileEntityItemStackRenderer(new TileEntityItemStackRenderer() {
            @Override
            public void renderByItem(ItemStack stack) {
                TileEntityRendererDispatcher.instance.render(shadowrootChest, 0.0, 0.0, 0.0, 0.0F, 1.0F);
            }
        });*/
    }

    public static void onInit() {
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
