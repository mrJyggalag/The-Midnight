package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.registry.ModBlocks;

public class BiomeMoltenCraters extends BiomeBase {
    public BiomeMoltenCraters(BiomeProperties properties) {
        super(properties);

        this.topBlock = ModBlocks.NIGHTSTONE.getDefaultState();
        this.fillerBlock = ModBlocks.NIGHTSTONE.getDefaultState();
    }
}
