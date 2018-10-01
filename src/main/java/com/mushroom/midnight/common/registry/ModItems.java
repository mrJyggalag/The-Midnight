package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.blocks.base.IMidnightBlock;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
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

        ModBlocks.blocks.stream().filter(block -> block instanceof IMidnightBlock).forEach(block -> {
            Item itemBlock = ((IMidnightBlock) block).getItem().setRegistryName(block.getRegistryName());
            items.add(itemBlock);
        });

        event.getRegistry().registerAll(items.toArray(new Item[0]));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        items.stream().filter(item -> item instanceof IModelProvider).forEach(item -> {
            Int2ObjectMap<String> variants = new Int2ObjectOpenHashMap<>();
            ((IModelProvider) item).gatherVariants(variants);
            for (Int2ObjectMap.Entry<String> entry : variants.int2ObjectEntrySet()){
                ModelLoader.setCustomModelResourceLocation(item, entry.getIntKey(),
                        new ModelResourceLocation(item.getRegistryName(), entry.getValue()));
            }
        });

        ModBlocks.blocks.stream().filter(block -> block instanceof IModelProvider).forEach(block -> {
            Int2ObjectMap<String> variants = new Int2ObjectOpenHashMap<>();
            ((IModelProvider) block).gatherVariants(variants);
            for (Int2ObjectMap.Entry<String> entry : variants.int2ObjectEntrySet())
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), entry.getIntKey(),
                        new ModelResourceLocation(block.getRegistryName(), entry.getValue()));
        });
    }
}