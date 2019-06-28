package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;

public class DarkWillowTreeFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "trees/dark_willow_1"),
            new ResourceLocation(Midnight.MODID, "trees/dark_willow_2"),
            new ResourceLocation(Midnight.MODID, "trees/dark_willow_3")
    };

    public DarkWillowTreeFeature(BlockState log, BlockState leaves) {
        super(TEMPLATES, log, leaves);
    }

    public DarkWillowTreeFeature() {
        this(MidnightBlocks.DARK_WILLOW_LOG.getDefaultState(), MidnightBlocks.DARK_WILLOW_LEAVES.getDefaultState());
    }
}
