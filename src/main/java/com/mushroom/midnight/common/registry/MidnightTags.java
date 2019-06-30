package com.mushroom.midnight.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import static com.mushroom.midnight.Midnight.MODID;

public class MidnightTags {
    public static class Blocks {
        public static final Tag<Block> fungi_stems = tag("fungi_stems");

        private static Tag<Block> tag(String name) {
            return new BlockTags.Wrapper(new ResourceLocation(MODID, name));
        }
    }

    public static class Items {
        public static final Tag<Item> spore_bombs = tag("spore_bombs");

        private static Tag<Item> tag(String name) {
            return new ItemTags.Wrapper(new ResourceLocation(MODID, name));
        }
    }
}
