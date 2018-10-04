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

public class BiomeBase extends Biome implements IMidnightBiome {

  protected static final IBlockState NIGHT_STONE = ModBlocks.NIGHTSTONE.getDefaultState();

  public BiomeBase(BiomeProperties properties) {
    super(properties);

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
    int seaLevel = worldIn.getSeaLevel();
    IBlockState topBlock = this.topBlock;
    IBlockState fillerBlock = this.fillerBlock;
    int j = -1;
    int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
    int l = x & 15;
    int i1 = z & 15;
    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

    for (int height = 255; height >= 0; --height) {
      if (height <= rand.nextInt(5)) {
        chunkPrimerIn.setBlockState(i1, height, l, BEDROCK);
      } else {
        IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, height, l);

        if (iblockstate2.getMaterial() == Material.AIR) {
          j = -1;
        } else if (iblockstate2.getBlock() == ModBlocks.NIGHTSTONE) {
          if (j == -1) {
            if (k <= 0) {
              topBlock = AIR;
              fillerBlock = NIGHT_STONE;
            } else if (height >= seaLevel - 4 && height <= seaLevel + 1) {
              topBlock = this.topBlock;
              fillerBlock = this.fillerBlock;
            }

            if (height < seaLevel && (topBlock == null || topBlock.getMaterial() == Material.AIR)) {
              if (this.getTemperature(blockpos$mutableblockpos.setPos(x, height, z)) < 0.15F) {
                topBlock = ICE;
              } else {
                topBlock = WATER;
              }
            }

            j = k;

            if (height >= seaLevel - 1) {
              chunkPrimerIn.setBlockState(i1, height, l, topBlock);
            } else if (height < seaLevel - 7 - k) {
              topBlock = AIR;
              fillerBlock = NIGHT_STONE;
              chunkPrimerIn.setBlockState(i1, height, l, GRAVEL);
            } else {
              chunkPrimerIn.setBlockState(i1, height, l, fillerBlock);
            }
          } else if (j > 0) {
            --j;
            chunkPrimerIn.setBlockState(i1, height, l, fillerBlock);

            if (j == 0 && fillerBlock.getBlock() == Blocks.SAND && k > 1) {
              j = rand.nextInt(4) + Math.max(0, height - 63);
              fillerBlock = fillerBlock.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? RED_SANDSTONE : SANDSTONE;
            }
          }
        }
      }
    }
  }
}
