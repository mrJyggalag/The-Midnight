package com.mushroom.midnight.common.biome.cavern;

public class CavernStructureConfig {
    private float densityScale;
    private float floorHeight;
    private float ceilingHeight;
    private float heightVariation;

    public CavernStructureConfig withFloorHeight(float floorHeight) {
        this.floorHeight = floorHeight;
        return this;
    }

    public CavernStructureConfig withCeilingHeight(float ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
        return this;
    }

    public CavernStructureConfig withHeightVariation(float heightVariation) {
        this.heightVariation = heightVariation;
        return this;
    }

    public CavernStructureConfig withDensityScale(float densityScale) {
        this.densityScale = densityScale;
        return this;
    }

    public float getDensityScale() {
        return this.densityScale;
    }

    public float getFloorHeight() {
        return this.floorHeight;
    }

    public float getCeilingHeight() {
        return this.ceilingHeight;
    }

    public float getHeightVariation() {
        return this.heightVariation;
    }
}
