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

    private CavernousBiomeConfig(@Nullable SurfaceConfig surfaceConfig, CavernStructureConfig structureConfig) {
        this.surfaceConfig = surfaceConfig;
        this.structureConfig = structureConfig;
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

    // TODO: Feature & spawner handling
    @Override
    public FeatureConfig getFeatureConfig() {
        return FeatureConfig.EMPTY;
    }

    @Override
    public SpawnerConfig getSpawnerConfig() {
        return SpawnerConfig.EMPTY;
    }

    @Override
    public Biome.BiomeProperties buildProperties(String name) {
        return new Biome.BiomeProperties(name)
                .setBaseHeight(this.structureConfig.getFloorHeight())
                .setHeightVariation(this.structureConfig.getHeightVariation());
    }

    public static class Builder {
        private SurfaceConfig surfaceConfig;
        private CavernStructureConfig structureConfig = new CavernStructureConfig();

        Builder() {
        }

        public Builder withSurface(SurfaceConfig surfaceConfig) {
            this.surfaceConfig = surfaceConfig;
            return this;
        }

        public Builder withStructure(CavernStructureConfig structureConfig) {
            this.structureConfig = structureConfig;
            return this;
        }

        public CavernousBiomeConfig build() {
            return new CavernousBiomeConfig(this.surfaceConfig, this.structureConfig);
        }
    }
}
