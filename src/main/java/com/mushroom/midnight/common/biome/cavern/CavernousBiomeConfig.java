package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.common.biome.config.FeatureConfig;
import com.mushroom.midnight.common.biome.config.MidnightBiomeConfig;
import com.mushroom.midnight.common.biome.config.SpawnerConfig;
import com.mushroom.midnight.common.biome.config.SurfaceConfig;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public class CavernousBiomeConfig implements MidnightBiomeConfig {
    private final SurfaceConfig surfaceConfig;
    private final CavernStructureConfig structureConfig;
    private final FeatureConfig featureConfig;
    private final SpawnerConfig spawnerConfig;

    private CavernousBiomeConfig(
            @Nullable SurfaceConfig surfaceConfig,
            CavernStructureConfig structureConfig,
            FeatureConfig featureConfig,
            SpawnerConfig spawnerConfig
    ) {
        this.surfaceConfig = surfaceConfig;
        this.structureConfig = structureConfig;
        this.featureConfig = featureConfig;
        this.spawnerConfig = spawnerConfig;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    @Nullable
    public SurfaceConfig getSurfaceConfig() {
        return this.surfaceConfig;
    }

    public CavernStructureConfig getStructureConfig() {
        return this.structureConfig;
    }

    @Override
    public FeatureConfig getFeatureConfig() {
        return this.featureConfig;
    }

    @Override
    public SpawnerConfig getSpawnerConfig() {
        return this.spawnerConfig;
    }

    @Override
    public Biome.BiomeProperties buildProperties(String name) {
        return new Biome.BiomeProperties(name)
                .setBaseHeight(this.structureConfig.getFloorHeight())
                .setHeightVariation(this.structureConfig.getHeightVariation());
    }

    public static class Builder {
        private SurfaceConfig surfaceConfig;
        private FeatureConfig featureConfig = FeatureConfig.EMPTY;
        private CavernStructureConfig structureConfig = new CavernStructureConfig();
        private SpawnerConfig spawnerConfig = SpawnerConfig.EMPTY;

        Builder() {
        }

        public Builder withSurface(SurfaceConfig surfaceConfig) {
            this.surfaceConfig = surfaceConfig;
            return this;
        }

        public Builder withFeatures(FeatureConfig featureConfig) {
            this.featureConfig = featureConfig;
            return this;
        }

        public Builder withStructure(CavernStructureConfig structureConfig) {
            this.structureConfig = structureConfig;
            return this;
        }

        public Builder withSpawner(SpawnerConfig spawnerConfig) {
            this.spawnerConfig = spawnerConfig;
            return this;
        }

        public CavernousBiomeConfig build() {
            return new CavernousBiomeConfig(this.surfaceConfig, this.structureConfig, this.featureConfig, this.spawnerConfig);
        }
    }
}
