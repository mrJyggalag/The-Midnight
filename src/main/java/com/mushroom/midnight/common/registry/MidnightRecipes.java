package com.mushroom.midnight.common.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MidnightRecipes {
    public static void onInit() {
        GameRegistry.addSmelting(MidnightItems.RAW_SUAVIS, new ItemStack(MidnightItems.COOK_SUAVIS), 0.35f);
        GameRegistry.addSmelting(MidnightItems.RAW_STAG_FLANK, new ItemStack(MidnightItems.COOK_STAG_FLANK), 0.35f);
        GameRegistry.addSmelting(MidnightBlocks.STINGER_EGG, new ItemStack(MidnightItems.COOK_STINGER_EGG), 0.35f);
        GameRegistry.addSmelting(MidnightItems.HUNTER_WING, new ItemStack(MidnightItems.COOK_HUNTER_WING), 0.35f);
        GameRegistry.addSmelting(MidnightBlocks.TENEBRUM_ORE, new ItemStack(MidnightItems.TENEBRUM_INGOT), 1f);
        GameRegistry.addSmelting(MidnightBlocks.NAGRILITE_ORE, new ItemStack(MidnightItems.NAGRILITE_INGOT), 1f);
        GameRegistry.addSmelting(MidnightBlocks.EBONYS_ORE, new ItemStack(MidnightItems.EBONYS), 1f);
        GameRegistry.addSmelting(MidnightBlocks.ARCHAIC_ORE, new ItemStack(MidnightItems.ARCHAIC_SHARD), 1f);
    }
}
