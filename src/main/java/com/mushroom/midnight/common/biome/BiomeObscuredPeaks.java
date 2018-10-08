package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.registry.ModBlocks;

public class BiomeObscuredPeaks extends BiomeBase {

    public BiomeObscuredPeaks(BiomeProperties properties) {
        super(properties);

        this.topBlock = ModBlocks.NIGHTSTONE.getDefaultState();
        this.fillerBlock = ModBlocks.NIGHTSTONE.getDefaultState();
    }
}
