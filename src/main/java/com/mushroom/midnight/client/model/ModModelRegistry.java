package com.mushroom.midnight.client.model;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.client.render.RenderRift;
import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.registry.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Side.CLIENT)
public class ModModelRegistry {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private static final int DEFAULT_GRASS_COLOR = 0xBF8ECC;
    private static final int DEFAULT_FOLIAGE_COLOR = 0x8F6DBC;

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityRift.class, RenderRift::new);

        ModItems.getItems().stream().filter(i -> i instanceof IModelProvider).forEach(ModModelRegistry::registerItemModel);
        ModBlocks.getBlocks().stream().filter(b -> b instanceof IModelProvider).forEach(ModModelRegistry::registerBlockModel);

        ModelLoader.setCustomStateMapper(ModBlocks.SHADOWROOT_LEAVES, new StateMap.Builder()
                .ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE)
                .build()
        );
        ModelLoader.setCustomStateMapper(ModBlocks.DARK_WILLOW_LEAVES, new StateMap.Builder()
                .ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE)
                .build()
        );
        ModelLoader.setCustomStateMapper(ModBlocks.DEAD_WOOD_LEAVES, new StateMap.Builder()
                .ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE)
                .build()
        );

        ModelLoader.setCustomStateMapper(ModBlocks.SHADOWROOT_DOOR, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(ModBlocks.DARK_WILLOW_DOOR, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(ModBlocks.DEAD_WOOD_DOOR, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
    }

    public static void onInit() {
        BlockColors blockColors = MC.getBlockColors();
        ItemColors itemColors = MC.getItemColors();

        blockColors.registerBlockColorHandler(ModModelRegistry::computeGrassColor, ModBlocks.MIDNIGHT_GRASS);
        itemColors.registerItemColorHandler(ModModelRegistry::defaultGrassColor, ModBlocks.MIDNIGHT_GRASS);

        blockColors.registerBlockColorHandler(ModModelRegistry::computeFoliageColor, ModBlocks.SHADOWROOT_LEAVES, ModBlocks.DARK_WILLOW_LEAVES, ModBlocks.DEAD_WOOD_LEAVES);
        itemColors.registerItemColorHandler(ModModelRegistry::defaultFoliageColor, ModBlocks.SHADOWROOT_LEAVES, ModBlocks.DARK_WILLOW_LEAVES, ModBlocks.DEAD_WOOD_LEAVES);

        blockColors.registerBlockColorHandler(ModModelRegistry::computeFoliageColor, ModBlocks.TALL_MIDNIGHT_GRASS, ModBlocks.DOUBLE_MIDNIGHT_GRASS);
        itemColors.registerItemColorHandler(ModModelRegistry::defaultFoliageColor, ModBlocks.TALL_MIDNIGHT_GRASS, ModBlocks.DOUBLE_MIDNIGHT_GRASS);
    }

    private static int computeGrassColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
        if (world == null || pos == null || MC.world.provider.getDimensionType() != ModDimensions.MIDNIGHT) {
            return DEFAULT_FOLIAGE_COLOR;
        }
        return BiomeColorHelper.getGrassColorAtPos(world, pos);
    }

    private static int defaultGrassColor(ItemStack stack, int tintIndex) {
        return DEFAULT_GRASS_COLOR;
    }

    private static int computeFoliageColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
        if (world == null || pos == null || MC.world.provider.getDimensionType() != ModDimensions.MIDNIGHT) {
            return DEFAULT_FOLIAGE_COLOR;
        }
        return BiomeColorHelper.getFoliageColorAtPos(world, pos);
    }

    private static int defaultFoliageColor(ItemStack stack, int tintIndex) {
        return DEFAULT_FOLIAGE_COLOR;
    }

    @SideOnly(Side.CLIENT)
    private static void registerBlockModel(Block block) {
        ResourceLocation identifier = block.getRegistryName();
        Item item = Item.getItemFromBlock(block);
        if (identifier == null || item == null) {
            throw new IllegalStateException("Cannot register model for improperly registered block");
        }

        Int2ObjectMap<String> variants = new Int2ObjectOpenHashMap<>();
        ((IModelProvider) block).gatherVariants(variants);
        for (Int2ObjectMap.Entry<String> entry : variants.int2ObjectEntrySet()) {
            int metadata = entry.getIntKey();
            String variant = entry.getValue();
            ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(identifier, variant));
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Item item) {
        ResourceLocation identifier = item.getRegistryName();
        if (identifier == null) {
            throw new IllegalStateException("Cannot register model for block without identifier");
        }

        Int2ObjectMap<String> variants = new Int2ObjectOpenHashMap<>();
        ((IModelProvider) item).gatherVariants(variants);
        for (Int2ObjectMap.Entry<String> entry : variants.int2ObjectEntrySet()) {
            int metadata = entry.getIntKey();
            String variant = entry.getValue();
            ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(identifier, variant));
        }
    }
}
