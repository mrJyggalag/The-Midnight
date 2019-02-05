package com.mushroom.midnight.common.biome.surface;

public class SurfaceTerrainConfig {
    public static final SurfaceTerrainConfig DEFAULT = new SurfaceTerrainConfig();

    private float ridgeWeight = 1.0F;
    private float densityScale = 1.0F;
    private float baseHeight = 0.1F;
    private float heightVariation = 0.1F;

    private boolean wet;

    public SurfaceTerrainConfig withBaseHeight(float baseHeight) {
        this.baseHeight = baseHeight;
        return this;
    }

    public SurfaceTerrainConfig withHeightVariation(float heightVariation) {
        this.heightVariation = heightVariation;
        return this;
    }

    public SurfaceTerrainConfig withRidgeWeight(float ridgeWeight) {
        this.ridgeWeight = ridgeWeight;
        return this;
    }

    public SurfaceTerrainConfig withDensityScale(float densityScale) {
        this.densityScale = densityScale;
        return this;
    }

    public SurfaceTerrainConfig wet() {
        this.wet = true;
        return this;
    }

    public float getRidgeWeight() {
        return this.ridgeWeight;
    }

    public float getDensityScale() {
        return this.densityScale;
    }

    public float getBaseHeight() {
        return this.baseHeight;
    }

    public float getHeightVariation() {
        return this.heightVariation;
    }

    public boolean isWet() {
        return this.wet;
    }
}
