package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@GameRegistry.ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class ModItems {
    static List<Item> items;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        items = Lists.newArrayList(

        );

        items.forEach(event.getRegistry()::register);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModItems.items.stream().filter(i -> i instanceof IModelProvider).forEach(ModItems::registerItemModel);
        ModBlocks.blocks.stream().filter(b -> b instanceof IModelProvider).forEach(ModItems::registerBlockModel);

        ModelLoader.setCustomStateMapper(ModBlocks.SHADOWROOT_LEAVES, new StateMap.Builder()
                .ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE)
                .build()
        );
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
