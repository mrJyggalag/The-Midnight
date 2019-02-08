package com.mushroom.midnight.common.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
    public static void onInit() {
        GameRegistry.addSmelting(ModItems.RAW_SUAVIS, new ItemStack(ModItems.COOK_SUAVIS), 0.35f);
        GameRegistry.addSmelting(ModItems.RAW_STAG_FLANK, new ItemStack(ModItems.COOK_STAG_FLANK), 0.35f);
        GameRegistry.addSmelting(ModBlocks.STINGER_EGG, new ItemStack(ModItems.COOK_STINGER_EGG), 0.35f);
        GameRegistry.addSmelting(ModItems.HUNTER_WING, new ItemStack(ModItems.COOK_HUNTER_WING), 0.35f);
        GameRegistry.addSmelting(ModBlocks.TENEBRUM_ORE, new ItemStack(ModItems.TENEBRUM_INGOT), 1f);
        GameRegistry.addSmelting(ModBlocks.NAGRILITE_ORE, new ItemStack(ModItems.NAGRILITE_INGOT), 1f);
        GameRegistry.addSmelting(ModBlocks.EBONYS_ORE, new ItemStack(ModItems.EBONYS), 1f);
        GameRegistry.addSmelting(ModBlocks.ARCHAIC_ORE, new ItemStack(ModItems.ARCHAIC_SHARD), 1f);
    }
}
