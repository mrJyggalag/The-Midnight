package com.mushroom.midnight.common.biomes;

import com.mushroom.midnight.common.registry.ModBlocks;

import net.minecraft.world.biome.Biome;

public class BiomeBlackRidge extends Biome implements IMidnightBiome {

    public BiomeBlackRidge(BiomeProperties properties) {
        super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();


        this.decorator.treesPerChunk = 10;
        this.decorator.grassPerChunk = 2;
        this.decorator.deadBushPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.cactiPerChunk = 0;

        this.topBlock = ModBlocks.MIDNIGHT_GRASS.getDefaultState();
        this.fillerBlock = ModBlocks.MIDNIGHT_DIRT.getDefaultState();

    }

    @Override
    public float getRidgeWeight() {
        return 1.0F;
    }
}
