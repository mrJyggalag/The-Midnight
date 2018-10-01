package com.mushroom.midnight.common.biomes;

import net.minecraft.world.biome.Biome;

public interface IMidnightBiome {
    float getRidgeWeight();

    static float getRidgeWeight(Biome biome) {
        return biome instanceof IMidnightBiome ? ((IMidnightBiome) biome).getRidgeWeight() : 0.0F;
    }
}
