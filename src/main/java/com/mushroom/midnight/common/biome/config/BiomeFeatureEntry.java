package com.mushroom.midnight.common.biome.config;

import com.mushroom.midnight.common.world.feature.IMidnightFeature;
import com.mushroom.midnight.common.world.feature.config.IPlacementConfig;

public class BiomeFeatureEntry {
    private final IMidnightFeature[] features;
    private final IPlacementConfig placementConfig;

    public BiomeFeatureEntry(IMidnightFeature[] feature, IPlacementConfig placementConfig) {
        this.features = feature;
        this.placementConfig = placementConfig;
    }

    public IMidnightFeature[] getFeatures() {
        return this.features;
    }

    public IPlacementConfig getPlacementConfig() {
        return this.placementConfig;
    }
}
