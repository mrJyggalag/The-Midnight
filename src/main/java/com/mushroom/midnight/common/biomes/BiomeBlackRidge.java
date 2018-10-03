package com.mushroom.midnight.common.biomes;

import java.util.Random;

import com.mushroom.midnight.common.world.generator.WorldGenShadowrootTrees;

import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeBlackRidge extends BiomeBase{

  public final WorldGenShadowrootTrees SHADOWROOT_TREE_GEN = new WorldGenShadowrootTrees();

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

  @Override
  public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
    return SHADOWROOT_TREE_GEN;
  }
}
