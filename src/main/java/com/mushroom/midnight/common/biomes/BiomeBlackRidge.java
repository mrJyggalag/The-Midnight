package com.mushroom.midnight.common.biomes;

public class BiomeBlackRidge extends BiomeBase{

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
  }

}
