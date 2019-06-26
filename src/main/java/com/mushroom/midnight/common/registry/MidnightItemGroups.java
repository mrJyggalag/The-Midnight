package com.mushroom.midnight.common.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class MidnightItemGroups {
    public static final ItemGroup BUILDING = new ItemGroup("midnight_building") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MidnightBlocks.MIDNIGHT_GRASS);
        }
    };

    public static final ItemGroup DECORATION = new ItemGroup("midnight_decoration") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MidnightBlocks.ROUXE);
        }
    };

    public static final ItemGroup ITEMS = new ItemGroup("midnight_items") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MidnightItems.DARK_STICK);
        }
    };

    public static final ItemGroup TOOLS = new ItemGroup("midnight_tools") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MidnightItems.SHADOWROOT_PICKAXE);
        }
    };

    public static final ItemGroup COMBAT = new ItemGroup("midnight_combat") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MidnightItems.SHADOWROOT_SWORD);
        }
    };
}
