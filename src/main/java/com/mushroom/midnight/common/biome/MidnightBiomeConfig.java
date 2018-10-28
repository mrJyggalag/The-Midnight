package com.mushroom.midnight.common.biome;

import com.google.common.collect.ImmutableList;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.feature.IMidnightFeature;
import com.mushroom.midnight.common.world.feature.config.IPlacementConfig;
import net.minecraft.block.state.IBlockState;

import java.util.Collection;
import java.util.Collections;

public class MidnightBiomeConfig {
    private final IBlockState topBlock;
    private final IBlockState fillerBlock;
    private final float ridgeWeight;
    private final float densityScale;
    private final int grassColor;
    private final int foliageColor;

    private final ImmutableList<FeatureEntry> features;

    private MidnightBiomeConfig(IBlockState topBlock, IBlockState fillerBlock, float ridgeWeight, float densityScale, int grassColor, int foliageColor, ImmutableList<FeatureEntry> features) {
        this.topBlock = topBlock;
        this.fillerBlock = fillerBlock;
        this.ridgeWeight = ridgeWeight;
        this.densityScale = densityScale;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
        this.features = features;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(MidnightBiomeConfig config) {
        return new Builder(config);
    }

    public Collection<FeatureEntry> getFeatures() {
        return Collections.unmodifiableCollection(this.features);
    }

    public IBlockState getTopBlock() {
        return this.topBlock;
    }

    public IBlockState getFillerBlock() {
        return this.fillerBlock;
    }

    public float getRidgeWeight() {
        return this.ridgeWeight;
    }

    public float getDensityScale() {
        return this.densityScale;
    }

    public int getGrassColor() {
        return this.grassColor;
    }

    public int getFoliageColor() {
        return this.foliageColor;
    }

    public static class Builder {
        private IBlockState topBlock = ModBlocks.MIDNIGHT_GRASS.getDefaultState();
        private IBlockState fillerBlock = ModBlocks.MIDNIGHT_DIRT.getDefaultState();
        private float ridgeWeight = 1.0F;
        private float densityScale = 1.0F;
        private int grassColor = 0xB084BC;
        private int foliageColor = 0x8F6DBC;

        private final ImmutableList.Builder<FeatureEntry> features = new ImmutableList.Builder<>();

        Builder() {
        }

        Builder(MidnightBiomeConfig config) {
            this.topBlock = config.topBlock;
            this.fillerBlock = config.fillerBlock;
            this.ridgeWeight = config.ridgeWeight;
            this.densityScale = config.densityScale;
            this.features.addAll(config.features);
        }

        public Builder withFeature(IMidnightFeature feature, IPlacementConfig config) {
            this.features.add(new FeatureEntry(new IMidnightFeature[] { feature }, config));
            return this;
        }

        public Builder withFeature(IMidnightFeature[] features, IPlacementConfig config) {
            this.features.add(new FeatureEntry(features, config));
            return this;
        }

        public Builder withTopBlock(IBlockState state) {
            this.topBlock = state;
            return this;
        }

        public Builder withFillerBlock(IBlockState state) {
            this.fillerBlock = state;
            return this;
        }

        public Builder withRidgeWeight(float ridgeWeight) {
            this.ridgeWeight = ridgeWeight;
            return this;
        }

        public Builder withDensityScale(float densityScale) {
            this.densityScale = densityScale;
            return this;
        }

        public Builder withGrassColor(int color) {
            this.grassColor = color;
            return this;
        }

        public Builder withFoliageColor(int color) {
            this.foliageColor = color;
            return this;
        }

        public MidnightBiomeConfig build() {
            return new MidnightBiomeConfig(this.topBlock, this.fillerBlock, this.ridgeWeight, this.densityScale, this.grassColor, this.foliageColor, this.features.build());
        }
    }

    public static class FeatureEntry {
        private final IMidnightFeature[] features;
        private final IPlacementConfig placementConfig;

        private FeatureEntry(IMidnightFeature[] feature, IPlacementConfig placementConfig) {
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
}
