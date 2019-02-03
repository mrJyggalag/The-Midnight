package com.mushroom.midnight.common.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModTabs {
    public static final CreativeTabs BUILDING_TAB = new CreativeTabs("midnight_building") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.MIDNIGHT_GRASS);
        }
    };

    public static final CreativeTabs DECORATION_TAB = new CreativeTabs("midnight_decoration") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.ROUXE);
        }
    };

    public static final CreativeTabs MIDNIGHT_ITEMS = new CreativeTabs("midnight_items") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.DARK_STICK);
        }
    };

    public static final CreativeTabs MIDNIGHT_TOOLS = new CreativeTabs("midnight_tools") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.SHADOWROOT_PICKAXE);
        }
    };

    public static final CreativeTabs MIDNIGHT_COMBAT = new CreativeTabs("midnight_combat") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.SHADOWROOT_SWORD);
        }
    };
}
