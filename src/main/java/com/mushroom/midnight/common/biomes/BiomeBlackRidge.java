package com.mushroom.midnight.common.biomes;

import com.mushroom.midnight.common.registry.ModBlocks;

public class BiomeBlackRidge extends BiomeBase {

  public BiomeBlackRidge(BiomeProperties properties) {
      super(properties);

      this.spawnableMonsterList.clear();
      this.spawnableCreatureList.clear();
      this.spawnableWaterCreatureList.clear();
      this.spawnableCaveCreatureList.clear();

      this.decorator.treesPerChunk = 0;
      this.decorator.grassPerChunk = 0;
      this.decorator.deadBushPerChunk = 0;
      this.decorator.reedsPerChunk = 0;
      this.decorator.cactiPerChunk = 0;
      this.decorator.flowersPerChunk = 0;

      this.topBlock = ModBlocks.NIGHTSTONE.getDefaultState();
      this.fillerBlock = ModBlocks.NIGHTSTONE.getDefaultState();
  }
}
