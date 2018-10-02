package com.mushroom.midnight.common.biomes;

import java.util.Random;

import com.mushroom.midnight.common.registry.ModBlocks;

import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeBlackRidge extends Biome implements IMidnightBiome {

  protected static final IBlockState NIGHT_STONE = ModBlocks.NIGHTSTONE.getDefaultState();

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

  @Override
  public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
    this.genBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
  }

  public void genBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
    int i = worldIn.getSeaLevel();
    IBlockState iblockstate = this.topBlock;
    IBlockState iblockstate1 = this.fillerBlock;
    int j = -1;
    int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
    int l = x & 15;
    int i1 = z & 15;
    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

    for (int j1 = 255; j1 >= 0; --j1) {
      if (j1 <= rand.nextInt(5)) {
        chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
      } else {
        IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

        if (iblockstate2.getMaterial() == Material.AIR) {
          j = -1;
        } else if (iblockstate2.getBlock() == ModBlocks.NIGHTSTONE) {
          if (j == -1) {
            if (k <= 0) {
              iblockstate = AIR;
              iblockstate1 = NIGHT_STONE;
            } else if (j1 >= i - 4 && j1 <= i + 1) {
              iblockstate = this.topBlock;
              iblockstate1 = this.fillerBlock;
            }

            if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
              if (this.getTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F) {
                iblockstate = ICE;
              } else {
                iblockstate = WATER;
              }
            }

            j = k;

            if (j1 >= i - 1) {
              chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
            } else if (j1 < i - 7 - k) {
              iblockstate = AIR;
              iblockstate1 = NIGHT_STONE;
              chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
            } else {
              chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
            }
          } else if (j > 0) {
            --j;
            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);

            if (j == 0 && iblockstate1.getBlock() == Blocks.SAND && k > 1) {
              j = rand.nextInt(4) + Math.max(0, j1 - 63);
              iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? RED_SANDSTONE : SANDSTONE;
            }
          }
        }
      }
    }
  }
}
