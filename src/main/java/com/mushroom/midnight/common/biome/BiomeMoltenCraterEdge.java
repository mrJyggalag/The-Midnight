package com.mushroom.midnight.common.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import java.util.Random;

public class BiomeMoltenCraterEdge extends BiomeMoltenCrater {
    public BiomeMoltenCraterEdge(BiomeProperties properties) {
        super(properties);

        this.topBlock = Blocks.OBSIDIAN.getDefaultState();
        this.fillerBlock = Blocks.OBSIDIAN.getDefaultState();
    }

    @Override
    protected IBlockState chooseTopBlock(Random random) {
        return this.topBlock;
    }
}
