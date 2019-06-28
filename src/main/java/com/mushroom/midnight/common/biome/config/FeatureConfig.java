package com.mushroom.midnight.common.biome.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.mushroom.midnight.common.biome.MidnightBiomeDecorator;
import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import net.minecraft.world.biome.Biome;

import java.util.Collection;
import java.util.List;

public class FeatureConfig {
    public static final FeatureConfig EMPTY = new FeatureConfig(ImmutableMultimap.of(), ImmutableList.of());

    private final ImmutableMultimap<FeatureSorting, BiomeFeatureEntry> features;
    private final ImmutableList<Biome.FlowerEntry> flowers;

    private FeatureConfig(ImmutableMultimap<FeatureSorting, BiomeFeatureEntry> features, ImmutableList<Biome.FlowerEntry> flowers) {
        this.features = features;
        this.flowers = flowers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Collection<BiomeFeatureEntry> getFeatures(FeatureSorting pass) {
        return this.features.get(pass);
    }

    public List<Biome.FlowerEntry> getFlowers() { return this.flowers; }

    public MidnightBiomeDecorator createDecorator(SurfacePlacementLevel placementLevel) {
        return new MidnightBiomeDecorator(this, placementLevel);
    }

    public static class Builder {
        private final ImmutableMultimap.Builder<FeatureSorting, BiomeFeatureEntry> features = new ImmutableMultimap.Builder<>();
        private final ImmutableList.Builder<Biome.FlowerEntry> flowers = new ImmutableList.Builder<>();

        Builder() {
        }

        public Builder extendsFrom(FeatureConfig config) {
            this.features.putAll(config.features);
            this.flowers.addAll(config.flowers);
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

        public Builder withFlower(Biome.FlowerEntry... flowers) {
            this.flowers.add(flowers);
            return this;
        }

        public FeatureConfig build() {
            return new FeatureConfig(this.features.build(), this.flowers.build());
        }
    }
}
