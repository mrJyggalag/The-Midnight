package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.config.FeatureConfig;
import com.mushroom.midnight.common.biome.config.MidnightBiomeConfig;
import com.mushroom.midnight.common.biome.config.SpawnerConfig;
import com.mushroom.midnight.common.biome.config.SurfaceConfig;
import net.minecraft.world.biome.Biome;

public class SurfaceBiomeConfig implements MidnightBiomeConfig {
    private final SurfaceConfig surfaceConfig;
    private final SurfaceTerrainConfig terrainConfig;
    private final FeatureConfig featureConfig;
    private final SpawnerConfig spawnerConfig;

    private final int grassColor;
    private final int foliageColor;

    private SurfaceBiomeConfig(
            SurfaceConfig surfaceConfig,
            SurfaceTerrainConfig terrainConfig,
            FeatureConfig featureConfig,
            SpawnerConfig spawnerConfig,
            int grassColor, int foliageColor
    ) {
        this.surfaceConfig = surfaceConfig;
        this.terrainConfig = terrainConfig;
        this.featureConfig = featureConfig;
        this.spawnerConfig = spawnerConfig;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(SurfaceBiomeConfig config) {
        return new Builder(config);
    }

    @Override
    public SurfaceConfig getSurfaceConfig() {
        return this.surfaceConfig;
    }

    public SurfaceTerrainConfig getTerrainConfig() {
        return this.terrainConfig;
    }

    @Override
    public FeatureConfig getFeatureConfig() {
        return this.featureConfig;
    }

    @Override
    public SpawnerConfig getSpawnerConfig() {
        return this.spawnerConfig;
    }

    public int getGrassColor() {
        return this.grassColor;
    }

    public int getFoliageColor() {
        return this.foliageColor;
    }

    @Override
    public Biome.BiomeProperties buildProperties(String name) {
        return new Biome.BiomeProperties(name)
                .setBaseHeight(this.terrainConfig.getBaseHeight())
                .setHeightVariation(this.terrainConfig.getHeightVariation());
    }

    public static class Builder {
        private SurfaceConfig surfaceConfig = new SurfaceConfig();
        private SurfaceTerrainConfig terrainConfig = new SurfaceTerrainConfig();
        private FeatureConfig featureConfig = FeatureConfig.EMPTY;
        private SpawnerConfig spawnerConfig = SpawnerConfig.EMPTY;

        private int grassColor = 0xB084BC;
        private int foliageColor = 0x8F6DBC;

        Builder() {
        }

        Builder(SurfaceBiomeConfig config) {
            this.surfaceConfig = config.surfaceConfig;
            this.terrainConfig = config.terrainConfig;
            this.featureConfig = config.featureConfig;
            this.spawnerConfig = config.spawnerConfig;
            this.grassColor = config.grassColor;
            this.foliageColor = config.foliageColor;
        }

        public Builder withSurface(SurfaceConfig surfaceConfig) {
            this.surfaceConfig = surfaceConfig;
            return this;
        }

        public Builder withTerrain(SurfaceTerrainConfig terrainConfig) {
            this.terrainConfig = terrainConfig;
            return this;
        }

        public Builder withFeatures(FeatureConfig featureConfig) {
            this.featureConfig = featureConfig;
            return this;
        }

        public Builder withSpawner(SpawnerConfig spawnerConfig) {
            this.spawnerConfig = spawnerConfig;
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

        public SurfaceBiomeConfig build() {
            return new SurfaceBiomeConfig(
                    this.surfaceConfig,
                    this.terrainConfig,
                    this.featureConfig,
                    this.spawnerConfig,
                    this.grassColor, this.foliageColor
            );
        }
    }
}
