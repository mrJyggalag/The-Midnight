package com.mushroom.midnight.common.biome.config;

import com.google.common.collect.ImmutableMultimap;
import com.mushroom.midnight.common.biome.MidnightBiomeDecorator;
import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import com.mushroom.midnight.common.world.feature.FeatureSorting;
import com.mushroom.midnight.common.world.feature.IMidnightFeature;
import com.mushroom.midnight.common.world.feature.config.IPlacementConfig;

import java.util.Collection;

public class FeatureConfig {
    public static final FeatureConfig EMPTY = new FeatureConfig(ImmutableMultimap.of());

    private final ImmutableMultimap<FeatureSorting, BiomeFeatureEntry> features;

    private FeatureConfig(ImmutableMultimap<FeatureSorting, BiomeFeatureEntry> features) {
        this.features = features;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Collection<BiomeFeatureEntry> getFeatures(FeatureSorting pass) {
        return this.features.get(pass);
    }

    public MidnightBiomeDecorator createDecorator(SurfacePlacementLevel placementLevel) {
        return new MidnightBiomeDecorator(this, placementLevel);
    }

    public static class Builder {
        private final ImmutableMultimap.Builder<FeatureSorting, BiomeFeatureEntry> features = new ImmutableMultimap.Builder<>();

        Builder() {
        }

        public Builder extendsFrom(FeatureConfig config) {
            this.features.putAll(config.features);
            return this;
        }

        public Builder withFeature(IMidnightFeature feature, IPlacementConfig config) {
            return this.withFeature(FeatureSorting.NORMAL, feature, config);
        }

        public Builder withFeature(IMidnightFeature[] features, IPlacementConfig config) {
            return this.withFeature(FeatureSorting.NORMAL, features, config);
        }

        public Builder withFeature(FeatureSorting sorting, IMidnightFeature feature, IPlacementConfig config) {
            this.features.put(sorting, new BiomeFeatureEntry(new IMidnightFeature[] { feature }, config));
            return this;
        }

        public Builder withFeature(FeatureSorting sorting, IMidnightFeature[] features, IPlacementConfig config) {
            this.features.put(sorting, new BiomeFeatureEntry(features, config));
            return this;
        }

        public FeatureConfig build() {
            return new FeatureConfig(this.features.build());
        }
    }
}
