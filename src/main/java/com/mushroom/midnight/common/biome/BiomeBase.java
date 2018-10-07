package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.generator.WorldGenMidnightGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeBase extends Biome implements IMidnightBiome {

    protected static final IBlockState NIGHT_STONE = ModBlocks.NIGHTSTONE.getDefaultState();

    protected int grassColor = 0xBF8ECC;
    protected int foliageColor = 0x8F6DBC;

    public BiomeBase(BiomeProperties properties) {
        super(properties);

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();

        this.decorator.treesPerChunk = 0;

        this.topBlock = ModBlocks.MIDNIGHT_GRASS.getDefaultState();
        this.fillerBlock = ModBlocks.MIDNIGHT_DIRT.getDefaultState();
    }

    @Override
    public float getRidgeWeight() {
        return 1.0F;
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, ChunkPrimer primer, int x, int z, double noiseVal) {
        this.genBiomeTerrain(world, rand, primer, x, z, noiseVal);
    }

    public void genBiomeTerrain(World worldIn, Random rand, ChunkPrimer primer, int z, int x, double noiseVal) {
        int seaLevel = worldIn.getSeaLevel();
        IBlockState topBlock = this.topBlock;
        IBlockState fillerBlock = this.fillerBlock;

        int currentDepth = -1;
        int fillerDepth = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int localZ = z & 15;
        int localX = x & 15;

        for (int height = 255; height >= 0; --height) {
            if (height <= rand.nextInt(5)) {
                primer.setBlockState(localX, height, localZ, BEDROCK);
            } else {
                IBlockState state = primer.getBlockState(localX, height, localZ);
                if (state.getMaterial() == Material.AIR) {
                    currentDepth = -1;
                } else if (state.getBlock() == ModBlocks.NIGHTSTONE) {
                    if (currentDepth == -1) {
                        if (fillerDepth <= 0) {
                            topBlock = AIR;
                            fillerBlock = NIGHT_STONE;
                        } else if (height >= seaLevel - 4 && height <= seaLevel + 1) {
                            topBlock = this.topBlock;
                            fillerBlock = this.fillerBlock;
                        }

                        if (height < seaLevel && (topBlock == null || topBlock.getMaterial() == Material.AIR)) {
                            topBlock = WATER;
                        }

                        currentDepth = fillerDepth;

                        if (height >= seaLevel - 1) {
                            primer.setBlockState(localX, height, localZ, topBlock);
                        } else if (height < seaLevel - 7 - fillerDepth) {
                            topBlock = AIR;
                            fillerBlock = NIGHT_STONE;
                            primer.setBlockState(localX, height, localZ, GRAVEL);
                        } else {
                            primer.setBlockState(localX, height, localZ, fillerBlock);
                        }
                    } else if (currentDepth > 0) {
                        --currentDepth;
                        primer.setBlockState(localX, height, localZ, fillerBlock);
                    }
                }
            }
        }
    }

    @Override
    public int getGrassColorAtPos(BlockPos pos) {
        return this.getModdedBiomeGrassColor(this.grassColor);
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos) {
        return this.getModdedBiomeFoliageColor(this.foliageColor);
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        return new WorldGenMidnightGrass();
    }
}
