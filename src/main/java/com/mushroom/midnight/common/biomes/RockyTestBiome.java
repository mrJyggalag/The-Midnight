package com.mushroom.midnight.common.biomes;

import net.minecraft.world.biome.Biome;

public class RockyTestBiome extends Biome implements IMidnightBiome {
    public RockyTestBiome(BiomeProperties properties) {
        super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }

    @Override
    public float getRidgeWeight() {
        return 1.0F;
    }
}
