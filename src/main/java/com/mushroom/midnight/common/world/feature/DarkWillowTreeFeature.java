package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class DarkWillowTreeFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "trees/dark_willow_1"),
            new ResourceLocation(Midnight.MODID, "trees/dark_willow_2"),
            new ResourceLocation(Midnight.MODID, "trees/dark_willow_3")
    };

    public DarkWillowTreeFeature(IBlockState log, IBlockState leaves) {
        super(TEMPLATES, log, leaves);
    }

    public DarkWillowTreeFeature() {
        this(ModBlocks.DARK_WILLOW_LOG.getDefaultState(), ModBlocks.DARK_WILLOW_LEAVES.getDefaultState());
    }
}
