package com.mushroom.midnight.common.registry;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import static com.mushroom.midnight.Midnight.MODID;

public class MidnightTags {
    public static class Blocks {
        public static final Tag<Block> CAN_HOLD_ORES = tag("can_hold_ores");
        public static final Tag<Block> FUNGI_STEMS = tag("fungi_stems");
        public static final Tag<Block> LOGS = tag("logs");
        public static final Tag<Block> BONEMEAL_GROUNDS = tag("bonemeal_grounds");
        public static final Tag<Block> PLANTABLE_GROUNDS = tag("plantable_grounds");

        private static Tag<Block> tag(String name) {
            return new BlockTags.Wrapper(new ResourceLocation(MODID, name));
        }
    }

    public static class Items {
        public static final Tag<Item> SPORE_BOMBS = tag("spore_bombs");
        public static final Tag<Item> UNSTABLE_FRUITS = tag("unstable_fruits");
        public static final Tag<Item> MIDNIGHT_PLANKS = tag("midnight_planks");
        public static final Tag<Item> FUNGI_STEMS = tag("fungi_stems");
        public static final Tag<Item> LOGS = tag("logs");

        private static Tag<Item> tag(String name) {
            return new ItemTags.Wrapper(new ResourceLocation(MODID, name));
        }
    }

    public static class Fluids {
        public static final Tag<Fluid> MIASMA = tag("miasma");
        public static final Tag<Fluid> DARK_WATER = tag("dark_water");

        private static Tag<Fluid> tag(String name) {
            return new FluidTags.Wrapper(new ResourceLocation(MODID, name));
        }
    }
}
