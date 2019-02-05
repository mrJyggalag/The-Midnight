package com.mushroom.midnight.common.biome.config;

import com.google.common.collect.ImmutableList;
import com.mushroom.midnight.common.biome.MidnightBiomeDecorator;
import com.mushroom.midnight.common.world.feature.IMidnightFeature;
import com.mushroom.midnight.common.world.feature.config.IPlacementConfig;

import java.util.Collection;

public class FeatureConfig {
    public static final FeatureConfig EMPTY = new FeatureConfig(ImmutableList.of());

    private final ImmutableList<BiomeFeatureEntry> features;

    private FeatureConfig(ImmutableList<BiomeFeatureEntry> features) {
        this.features = features;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Collection<BiomeFeatureEntry> getFeatures() {
        return this.features;
    }

    public MidnightBiomeDecorator createDecorator() {
        return new MidnightBiomeDecorator(this);
    }

    public static class Builder {
        private final ImmutableList.Builder<BiomeFeatureEntry> features = new ImmutableList.Builder<>();

        Builder() {
        }

        public Builder extendsFrom(FeatureConfig config) {
            this.features.addAll(config.getFeatures());
            return this;
        }

        public Builder withFeature(IMidnightFeature feature, IPlacementConfig config) {
            this.features.add(new BiomeFeatureEntry(new IMidnightFeature[] { feature }, config));
            return this;
        }

        public Builder withFeature(IMidnightFeature[] features, IPlacementConfig config) {
            this.features.add(new BiomeFeatureEntry(features, config));
            return this;
        }

        public FeatureConfig build() {
            return new FeatureConfig(this.features.build());
        }
    }
}
