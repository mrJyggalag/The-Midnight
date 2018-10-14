package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.generator.WorldGenDoubleFungi;
import com.mushroom.midnight.common.world.generator.WorldGenDoubleMidnightPlant;
import com.mushroom.midnight.common.world.generator.WorldGenMidnightPlant;
import com.mushroom.midnight.common.world.generator.WorldGenMidnightTree;
import com.mushroom.midnight.common.world.generator.WorldGenSmallFungi;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BiomeBase extends Biome implements IMidnightBiome {
    protected static final IBlockState NIGHT_STONE = ModBlocks.NIGHTSTONE.getDefaultState();

    protected static final WorldGenMidnightTree SHADOWROOT_TREE_GEN = new WorldGenMidnightTree(ModBlocks.SHADOWROOT_LOG, ModBlocks.SHADOWROOT_LEAVES, 6);
    protected static final WorldGenMidnightTree DARK_WILLOW_TREE_GEN = new WorldGenMidnightTree(ModBlocks.DARK_WILLOW_LOG, ModBlocks.DARK_WILLOW_LEAVES, 6);

    protected static final WorldGenMidnightPlant GRASS_GENERATOR = new WorldGenMidnightPlant(
            ModBlocks.TALL_MIDNIGHT_GRASS.getDefaultState(),
            ((BlockBush) ModBlocks.TALL_MIDNIGHT_GRASS)::canBlockStay,
            128
    );

    protected static final WorldGenDoubleMidnightPlant DOUBLE_GRASS_GENERATOR = new WorldGenDoubleMidnightPlant(
            ModBlocks.DOUBLE_MIDNIGHT_GRASS.getDefaultState(),
            (world, pos, state) -> ModBlocks.DOUBLE_MIDNIGHT_GRASS.canPlaceBlockAt(world, pos),
            64
    );

    protected static final WorldGenMidnightPlant LUMEN_GENERATOR = new WorldGenMidnightPlant(
            ModBlocks.LUMEN_BUD.getDefaultState(),
            ((BlockBush) ModBlocks.LUMEN_BUD)::canBlockStay,
            12
    );

    protected static final WorldGenDoubleMidnightPlant DOUBLE_LUMEN_GENERATOR = new WorldGenDoubleMidnightPlant(
            ModBlocks.DOUBLE_LUMEN_BUD.getDefaultState(),
            (world, pos, state) -> ModBlocks.DOUBLE_LUMEN_BUD.canPlaceBlockAt(world, pos),
            8
    );

    protected static final WorldGenSmallFungi FUNGI_GENERATOR = new WorldGenSmallFungi(16);
    protected static final WorldGenDoubleFungi DOUBLE_FUNGI_GENERATOR = new WorldGenDoubleFungi(8);

    protected static final WorldGenerator CRYSTAL_FLOWER_GENERATOR = new WorldGenMidnightPlant(
            ModBlocks.CRYSTAL_FLOWER.getDefaultState(),
            ((BlockBush) ModBlocks.CRYSTAL_FLOWER)::canBlockStay,
            12
    );

    protected int grassColor = 0xB084BC;
    protected int foliageColor = 0x8F6DBC;

    public BiomeBase(BiomeProperties properties) {
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
        this.decorator.mushroomsPerChunk = 0;

        this.decorator.extraTreeChance = 0.0F;

        this.topBlock = ModBlocks.MIDNIGHT_GRASS.getDefaultState();
        this.fillerBlock = ModBlocks.MIDNIGHT_DIRT.getDefaultState();
    }

    @Override
    public float getRidgeWeight() {
        return 1.0F;
    }

    @Override
    public float getDensityScale() {
        return 1.0F;
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, ChunkPrimer primer, int x, int z, double noiseVal) {
        this.genBiomeTerrain(world, rand, primer, x, z, noiseVal);
    }

    public void genBiomeTerrain(World world, Random rand, ChunkPrimer primer, int z, int x, double noiseVal) {
        int seaLevel = world.getSeaLevel();
        IBlockState topBlock = this.chooseTopBlock(rand);
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

                        currentDepth = fillerDepth;

                        primer.setBlockState(localX, height, localZ, topBlock);
                    } else if (currentDepth > 0) {
                        --currentDepth;
                        primer.setBlockState(localX, height, localZ, fillerBlock);
                    }
                }
            }
        }
    }

    protected IBlockState chooseTopBlock(Random random) {
        return this.topBlock;
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.GRASS)) {
            this.generateCoverPlant(world, rand, pos, 1, DOUBLE_GRASS_GENERATOR);
        }

        super.decorate(world, rand, pos);
    }

    protected void generateCoverPlant(World world, Random rand, BlockPos pos, int count, WorldGenerator generator) {
        for (int i = 0; i < count; ++i) {
            int offsetX = rand.nextInt(16) + 8;
            int offsetZ = rand.nextInt(16) + 8;
            int maxY = world.getHeight(pos.add(offsetX, 0, offsetZ)).getY() + 32;
            if (maxY > 0) {
                int offsetY = rand.nextInt(maxY);
                generator.generate(world, rand, pos.add(offsetX, offsetY, offsetZ));
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
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return SHADOWROOT_TREE_GEN;
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        return GRASS_GENERATOR;
    }
}
