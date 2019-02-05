package com.mushroom.midnight.common.biome.config;

import net.minecraft.world.biome.Biome;

public interface MidnightBiomeConfig {
    SurfaceConfig getSurfaceConfig();

    FeatureConfig getFeatureConfig();

    SpawnerConfig getSpawnerConfig();

    Biome.BiomeProperties buildProperties(String name);
}
