package com.mushroom.midnight.common.biome;

public class BiomeWarpedFields extends BiomeBase {
    public BiomeWarpedFields(BiomeProperties properties) {
        super(properties);
    }

    @Override
    public float getDensityScale() {
        return 0.5F;
    }
}
