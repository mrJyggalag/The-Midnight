package com.mushroom.midnight.common.biomes;

import net.minecraft.world.biome.Biome;

public interface IMidnightBiome {
    boolean isRidged();

    static boolean isRidged(Biome biome) {
        return biome instanceof IMidnightBiome && ((IMidnightBiome) biome).isRidged();
    }
}
