package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.biomes.IMidnightBiome;
import com.mushroom.midnight.common.world.noise.OctavedNoiseGenerator;
import com.mushroom.midnight.common.world.util.BiomeWeightTable;
import com.mushroom.midnight.common.world.util.NoiseChunkPrimer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class MidnightChunkGenerator implements IChunkGenerator {
    private static final int HORIZONTAL_GRANULARITY = 4;
    private static final int VERTICAL_GRANULARITY = 4;

    private static final int NOISE_WIDTH = 16 / HORIZONTAL_GRANULARITY;
    private static final int NOISE_HEIGHT = 256 / VERTICAL_GRANULARITY;

    private static final int BUFFER_WIDTH = NOISE_WIDTH + 1;
    private static final int BUFFER_HEIGHT = NOISE_HEIGHT + 1;

    private static final int BIOME_WEIGHT_RADIUS = 2;

    private static final int BIOME_NOISE_OFFSET = BIOME_WEIGHT_RADIUS;
    private static final int BIOME_NOISE_SIZE = BUFFER_WIDTH + BIOME_WEIGHT_RADIUS * 2;

    private static final IBlockState STONE = Blocks.STONE.getDefaultState();

    private final World world;
    private final Random random;

    private Biome[] biomeBuffer;
    private Biome[] biomeNoiseBuffer;

    private final OctavedNoiseGenerator worldNoise;
    private final OctavedNoiseGenerator surfaceNoise;

    private final double[] worldNoiseBuffer = new double[BUFFER_WIDTH * BUFFER_WIDTH * BUFFER_HEIGHT];
    private final double[] terrainBuffer = new double[BUFFER_WIDTH * BUFFER_WIDTH * BUFFER_HEIGHT];
    private final double[] surfaceBuffer = new double[BUFFER_WIDTH * BUFFER_WIDTH];

    private final NoiseChunkPrimer noisePrimer;
    private final BiomeWeightTable weightTable;

    public MidnightChunkGenerator(World world) {
        this.world = world;
        this.random = new Random(world.getSeed());

        this.worldNoise = new OctavedNoiseGenerator(this.random, 3, 5.0, 0.1, 0.51, 2.01, BUFFER_WIDTH, BUFFER_HEIGHT, BUFFER_WIDTH);
        this.surfaceNoise = new OctavedNoiseGenerator(this.random, 8, 3.0, 0.04, 0.51, 2.01, BUFFER_WIDTH, BUFFER_WIDTH);

        this.noisePrimer = new NoiseChunkPrimer(HORIZONTAL_GRANULARITY, VERTICAL_GRANULARITY, NOISE_WIDTH, NOISE_HEIGHT);
        this.weightTable = new BiomeWeightTable(BIOME_WEIGHT_RADIUS);
    }

    @Override
    public Chunk generateChunk(int chunkX, int chunkZ) {
        int globalX = chunkX << 4;
        int globalZ = chunkZ << 4;

        BiomeProvider biomeProvider = this.world.getBiomeProvider();
        this.biomeBuffer = biomeProvider.getBiomes(this.biomeBuffer, globalX, globalZ, 16, 16);

        ChunkPrimer primer = new ChunkPrimer();
        this.primeChunk(primer, chunkX, chunkZ);

        Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);

        byte[] chunkBiomes = chunk.getBiomeArray();
        for (int i = 0; i < chunkBiomes.length; i++) {
            chunkBiomes[i] = (byte) Biome.getIdForBiome(this.biomeBuffer[i]);
        }

        chunk.generateSkylightMap();

        return chunk;
    }

    protected void primeChunk(ChunkPrimer primer, int chunkX, int chunkZ) {
        int originX = chunkX * HORIZONTAL_GRANULARITY - BIOME_NOISE_OFFSET;
        int originZ = chunkZ * HORIZONTAL_GRANULARITY - BIOME_NOISE_OFFSET;
        this.biomeNoiseBuffer = this.world.getBiomeProvider().getBiomesForGeneration(this.biomeNoiseBuffer, originX, originZ, BIOME_NOISE_SIZE, BIOME_NOISE_SIZE);

        this.populateNoise(chunkX, chunkZ);
        this.noisePrimer.primeChunk(primer, this.terrainBuffer, (density, x, y, z) -> {
            if (density > 0.0F) {
                return STONE;
            } else {
                return null;
            }
        });
    }

    protected void populateNoise(int chunkX, int chunkZ) {
        this.worldNoise.generateOctavedNoise3D(this.worldNoiseBuffer, chunkX * NOISE_WIDTH, 0, chunkZ * NOISE_WIDTH);
        this.surfaceNoise.generateOctavedNoise2D(this.surfaceBuffer, chunkX * NOISE_WIDTH, chunkZ * NOISE_WIDTH);

        float heightOrigin = 62.0F / VERTICAL_GRANULARITY;

        int index = 0;
        int surfaceIndex = 0;

        for (int localZ = 0; localZ < NOISE_WIDTH + 1; localZ++) {
            for (int localX = 0; localX < NOISE_WIDTH + 1; localX++) {
                float heightVariation = 0.0F;
                float baseHeight = 0.0F;
                float ridgeWeight = 0.0F;
                float totalWeight = 0.0F;

                Biome originBiome = this.sampleNoiseBiome(localX, localZ);
                for (int neighbourZ = -BIOME_WEIGHT_RADIUS; neighbourZ <= BIOME_WEIGHT_RADIUS; neighbourZ++) {
                    for (int neighbourX = -BIOME_WEIGHT_RADIUS; neighbourX <= BIOME_WEIGHT_RADIUS; neighbourX++) {
                        Biome neighbourBiome = this.sampleNoiseBiome(localX + neighbourX, localZ + neighbourZ);
                        float neighbourBaseHeight = neighbourBiome.getBaseHeight();
                        float neighbourHeightVariation = neighbourBiome.getHeightVariation();
                        boolean ridged = IMidnightBiome.isRidged(neighbourBiome);

                        float biomeWeight = this.weightTable.get(neighbourX, neighbourZ) / (neighbourBaseHeight + 2.0F);
                        if (neighbourBiome.getBaseHeight() > originBiome.getBaseHeight()) {
                            biomeWeight *= 2.0F;
                        }

                        heightVariation += neighbourHeightVariation * biomeWeight;
                        baseHeight += neighbourBaseHeight * biomeWeight;
                        ridgeWeight += ridged ? biomeWeight : 0.0F;

                        totalWeight += biomeWeight;
                    }
                }

                heightVariation /= totalWeight;
                baseHeight /= totalWeight;
                ridgeWeight /= totalWeight;
                baseHeight += heightOrigin;

                heightVariation = heightVariation * 0.9F + 0.1F;

                double surfaceNoise = (this.surfaceBuffer[surfaceIndex++] + 1.5) / 3.0;
                surfaceNoise = (surfaceNoise * heightVariation * 0.5) + baseHeight;

                for (int localY = 0; localY < NOISE_HEIGHT + 1; localY++) {
                    double sampledNoise = this.worldNoiseBuffer[index];
                    double density = sampledNoise * Math.pow(heightVariation * 5.0, 3.0);

                    double densityBias = surfaceNoise - localY;
                    if (localY < baseHeight) {
                        densityBias *= 3.0;
                    }

                    this.terrainBuffer[index] = density + densityBias;

                    index++;
                }
            }
        }
    }

    private Biome sampleNoiseBiome(int x, int z) {
        return this.biomeNoiseBuffer[(x + BIOME_NOISE_OFFSET) + (z + BIOME_NOISE_OFFSET) * BIOME_NOISE_SIZE];
    }

    @Override
    public void populate(int x, int z) {
    }

    @Override
    public boolean generateStructures(Chunk chunk, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return this.world.getBiome(pos).getSpawnableList(creatureType);
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World world, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z) {
    }

    @Override
    public boolean isInsideStructure(World world, String structureName, BlockPos pos) {
        return false;
    }
}
