package com.mushroom.midnight.common.biome;

import net.minecraft.block.state.IBlockState;

import java.util.Random;

public class BiomeMoltenCraterEdge extends BiomeMoltenCrater {
    public BiomeMoltenCraterEdge(BiomeProperties properties) {
        super(properties);
    }

    @Override
    protected IBlockState chooseTopBlock(Random random) {
        return this.topBlock;
    }
}
