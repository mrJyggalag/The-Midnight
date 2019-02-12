package com.mushroom.midnight.common.biome.cavern;

public class CavernStructureConfig {
    private float cavernDensity = -5.0F;
    private float floorHeight = 0.0F;
    private float ceilingHeight = 1.0F;
    private float heightVariation = 0.1F;
    private float caveRadiusScale = 2.2F;
    private float pillarWeight = 1.0F;

    public CavernStructureConfig withCavernDensity(float density) {
        this.cavernDensity = density;
        return this;
    }

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

    public CavernStructureConfig withCaveRadiusScale(float caveRadiusScale) {
        this.caveRadiusScale = caveRadiusScale;
        return this;
    }

    public CavernStructureConfig withPillarWeight(float pillarWeight) {
        this.pillarWeight = pillarWeight;
        return this;
    }

    public float getCavernDensity() {
        return this.cavernDensity;
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

    public float getCaveRadiusScale() {
        return this.caveRadiusScale;
    }

    public float getPillarWeight() {
        return this.pillarWeight;
    }
}
