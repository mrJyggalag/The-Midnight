package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BiomeLayerSampler;
import com.mushroom.midnight.common.biome.MidnightBiomeLayer;
import com.mushroom.midnight.common.biome.cavern.CavernStructureConfig;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.biome.surface.SurfaceBiome;
import com.mushroom.midnight.common.biome.surface.SurfaceTerrainConfig;
import com.mushroom.midnight.common.capability.CavernousBiomeStore;
import com.mushroom.midnight.common.capability.MultiLayerBiomeSampler;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModCavernousBiomes;
import com.mushroom.midnight.common.util.Curve;
import com.mushroom.midnight.common.util.RegionInterpolator;
import com.mushroom.midnight.common.world.generator.WorldGenMidnightCaves;
import com.mushroom.midnight.common.world.generator.WorldGenMoltenCrater;
import com.mushroom.midnight.common.world.noise.OctaveNoiseSampler;
import com.mushroom.midnight.common.world.noise.PerlinNoiseSampler;
import com.mushroom.midnight.common.world.util.BiomeWeightTable;
import com.mushroom.midnight.common.world.util.NoiseChunkPrimer;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MidnightChunkGenerator implements IChunkGenerator, PartialChunkGenerator {
    private static final BiomeLayerSampler<CavernousBiome> DEFAULT_CAVERN_SAMPLER = new BiomeLayerSampler.Constant<>(ModCavernousBiomes.NONE);

    private static final int HORIZONTAL_GRANULARITY = 4;
    private static final int VERTICAL_GRANULARITY = 4;

    private static final int NOISE_WIDTH = 16 / HORIZONTAL_GRANULARITY;
    private static final int NOISE_HEIGHT = 256 / VERTICAL_GRANULARITY;

    private static final int BUFFER_WIDTH = NOISE_WIDTH + 1;
    private static final int BUFFER_HEIGHT = NOISE_HEIGHT + 1;

    private static final int BIOME_WEIGHT_RADIUS = 2;

    private static final int BIOME_NOISE_OFFSET = BIOME_WEIGHT_RADIUS;
    private static final int BIOME_NOISE_SIZE = BUFFER_WIDTH + BIOME_WEIGHT_RADIUS * 2;

    private static final int MIN_CAVE_HEIGHT = 20;
    private static final int MAX_CAVE_HEIGHT = 46;

    private static final int MIN_WATER_LEVEL = MAX_CAVE_HEIGHT + 6;

    private static final IBlockState STONE = ModBlocks.NIGHTSTONE.getDefaultState();
    private static final IBlockState WATER = ModBlocks.DARK_WATER.getDefaultState();

    private final World world;
    private final Random random;

    private Biome[] biomeBuffer;
    private Biome[] biomeNoiseBuffer;

    private final CavernousBiome[] cavernNoiseBuffer = new CavernousBiome[BIOME_NOISE_SIZE * BIOME_NOISE_SIZE];

    private final OctaveNoiseSampler worldNoise;
    private final OctaveNoiseSampler surfaceNoise;
    private final OctaveNoiseSampler ceilingNoise;
    private final OctaveNoiseSampler ridgedSurfaceNoise;
    private final PerlinNoiseSampler pillarNoise;

    private final OctaveNoiseSampler depthNoise;

    private final double[] worldNoiseBuffer = new double[BUFFER_WIDTH * BUFFER_WIDTH * BUFFER_HEIGHT];
    private final double[] terrainBuffer = new double[BUFFER_WIDTH * BUFFER_WIDTH * BUFFER_HEIGHT];
    private final double[] surfaceBuffer = new double[BUFFER_WIDTH * BUFFER_WIDTH];
    private final double[] ceilingBuffer = new double[BUFFER_WIDTH * BUFFER_WIDTH];
    private final double[] ridgedSurfaceBuffer = new double[BUFFER_WIDTH * BUFFER_WIDTH];
    private final double[] pillarBuffer = new double[BUFFER_WIDTH * BUFFER_WIDTH];

    private final double[] depthBuffer = new double[256];

    private final MapGenBase caveGenerator;
    private final MapGenBase craterGenerator;

    private final NoiseChunkPrimer noisePrimer;
    private final BiomeWeightTable weightTable;

    public MidnightChunkGenerator(World world) {
        this.world = world;
        this.random = new Random(world.getSeed());

        this.worldNoise = OctaveNoiseSampler.perlin(this.random, 3);
        this.worldNoise.setAmplitude(5.0);
        this.worldNoise.setFrequency(0.1);

        this.surfaceNoise = OctaveNoiseSampler.perlin(this.random, 8);
        this.surfaceNoise.setAmplitude(3.0);
        this.surfaceNoise.setFrequency(0.04);

        this.ceilingNoise = OctaveNoiseSampler.perlin(this.random, 6);
        this.ceilingNoise.setAmplitude(3.0);
        this.ceilingNoise.setFrequency(0.04);

        this.pillarNoise = new PerlinNoiseSampler(this.random);
        this.pillarNoise.setFrequency(0.2);

        this.ridgedSurfaceNoise = OctaveNoiseSampler.ridged(this.random, 3, 4.0);
        this.ridgedSurfaceNoise.setAmplitude(4.0);
        this.ridgedSurfaceNoise.setFrequency(0.08);

        this.depthNoise = OctaveNoiseSampler.perlin(this.random, 4);
        this.depthNoise.setFrequency(0.0625);

        this.noisePrimer = new NoiseChunkPrimer(HORIZONTAL_GRANULARITY, VERTICAL_GRANULARITY, NOISE_WIDTH, NOISE_HEIGHT);
        this.weightTable = new BiomeWeightTable(BIOME_WEIGHT_RADIUS);

        this.caveGenerator = TerrainGen.getModdedMapGen(new WorldGenMidnightCaves(), InitMapGenEvent.EventType.CAVE);
        this.craterGenerator = TerrainGen.getModdedMapGen(new WorldGenMoltenCrater(this.random, this), InitMapGenEvent.EventType.CUSTOM);
    }

    @Override
    public Chunk generateChunk(int chunkX, int chunkZ) {
        this.random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);

        ChunkPrimer primer = new ChunkPrimer();
        this.primeChunk(primer, chunkX, chunkZ);

        Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);

        byte[] chunkBiomes = chunk.getBiomeArray();
        for (int i = 0; i < chunkBiomes.length; i++) {
            chunkBiomes[i] = (byte) Biome.getIdForBiome(this.biomeBuffer[i]);
        }

        CavernousBiomeStore cavernousBiomeStore = chunk.getCapability(Midnight.CAVERNOUS_BIOME_CAP, null);
        if (cavernousBiomeStore != null) {
            cavernousBiomeStore.populate(this.getCavernousBiomeSampler(), chunkX << 4, chunkZ << 4);
        }

        chunk.generateSkylightMap();

        return chunk;
    }

    @Override
    public void primeChunk(ChunkPrimer primer, int chunkX, int chunkZ) {
        this.primeChunkBare(primer, chunkX, chunkZ);

        int globalX = chunkX << 4;
        int globalZ = chunkZ << 4;

        BiomeProvider biomeProvider = this.world.getBiomeProvider();
        this.biomeBuffer = biomeProvider.getBiomes(this.biomeBuffer, globalX, globalZ, 16, 16);

        this.coverSurface(primer, chunkX, chunkZ);

        // TODO
//        this.caveGenerator.generate(this.world, chunkX, chunkZ, primer);
//        this.craterGenerator.generate(this.world, chunkX, chunkZ, primer);
    }

    @Override
    public void primeChunkBare(ChunkPrimer primer, int chunkX, int chunkZ) {
        this.populateNoise(chunkX, chunkZ);

        int seaLevel = this.world.getSeaLevel();
        this.noisePrimer.primeChunk(primer, this.terrainBuffer, (density, x, y, z) -> {
            if (density > 0.0F) {
                return STONE;
            } else if (y < seaLevel && y > MIN_WATER_LEVEL) {
                return WATER;
            }
            return null;
        });
    }

    protected void populateNoise(int chunkX, int chunkZ) {
        int originX = chunkX * HORIZONTAL_GRANULARITY - BIOME_NOISE_OFFSET;
        int originZ = chunkZ * HORIZONTAL_GRANULARITY - BIOME_NOISE_OFFSET;
        this.biomeNoiseBuffer = this.world.getBiomeProvider().getBiomesForGeneration(this.biomeNoiseBuffer, originX, originZ, BIOME_NOISE_SIZE, BIOME_NOISE_SIZE);

        this.getCavernousBiomeSampler().sampleNoise(this.cavernNoiseBuffer, originX, originZ, BIOME_NOISE_SIZE, BIOME_NOISE_SIZE);

        this.worldNoise.sample3D(this.worldNoiseBuffer, chunkX * NOISE_WIDTH, 0, chunkZ * NOISE_WIDTH, BUFFER_WIDTH, BUFFER_HEIGHT, BUFFER_WIDTH);
        this.surfaceNoise.sample2D(this.surfaceBuffer, chunkX * NOISE_WIDTH, chunkZ * NOISE_WIDTH, BUFFER_WIDTH, BUFFER_WIDTH);
        this.ceilingNoise.sample2D(this.ceilingBuffer, chunkX * NOISE_WIDTH, chunkZ * NOISE_WIDTH, BUFFER_WIDTH, BUFFER_WIDTH);
        this.ridgedSurfaceNoise.sample2D(this.ridgedSurfaceBuffer, chunkX * NOISE_WIDTH, chunkZ * NOISE_WIDTH, BUFFER_WIDTH, BUFFER_WIDTH);

        Arrays.fill(this.pillarBuffer, 0.0);
        this.pillarNoise.sample2D(this.pillarBuffer, chunkX * NOISE_WIDTH, chunkZ * NOISE_WIDTH, BUFFER_WIDTH, BUFFER_WIDTH);

        float heightOrigin = 62.0F / VERTICAL_GRANULARITY;
        float maxHeight = 256.0F / VERTICAL_GRANULARITY;

        float minCaveHeight = (float) MIN_CAVE_HEIGHT / VERTICAL_GRANULARITY;
        float maxCaveHeight = (float) MAX_CAVE_HEIGHT / VERTICAL_GRANULARITY;
        float caveHeightRange = maxCaveHeight - minCaveHeight;

        int index = 0;
        int surfaceIndex = 0;

        for (int localZ = 0; localZ < NOISE_WIDTH + 1; localZ++) {
            for (int localX = 0; localX < NOISE_WIDTH + 1; localX++) {
                float heightVariation = 0.0F;
                float baseHeight = 0.0F;
                float ridgeWeight = 0.0F;
                float densityScale = 0.0F;
                float totalWeight = 0.0F;

                float cavernFloorHeight = 0.0F;
                float cavernCeilingHeight = 0.0F;
                float cavernDensityScale = 0.0F;
                float cavernHeightVariation = 0.0F;

                Biome originBiome = this.sampleNoiseBiome(localX, localZ);
                for (int neighborZ = -BIOME_WEIGHT_RADIUS; neighborZ <= BIOME_WEIGHT_RADIUS; neighborZ++) {
                    for (int neighborX = -BIOME_WEIGHT_RADIUS; neighborX <= BIOME_WEIGHT_RADIUS; neighborX++) {
                        Biome neighborBiome = this.sampleNoiseBiome(localX + neighborX, localZ + neighborZ);
                        CavernousBiome neighborCavernBiome = this.sampleNoiseCavernBiome(localX + neighborX, localZ + neighborZ);

                        SurfaceTerrainConfig terrainConfig = SurfaceBiome.getTerrainConfig(neighborBiome);
                        float nBaseHeight = terrainConfig.getBaseHeight();
                        float nHeightVariation = terrainConfig.getHeightVariation();

                        float nRidgeWeight = terrainConfig.getRidgeWeight();
                        float nDensityScale = terrainConfig.getDensityScale();

                        CavernStructureConfig cavernStructureConfig = neighborCavernBiome.getConfig().getStructureConfig();
                        float nCavernFloorHeight = cavernStructureConfig.getFloorHeight();
                        float nCavernCeilingHeight = cavernStructureConfig.getCeilingHeight();
                        float nCavernDensityScale = cavernStructureConfig.getDensityScale();
                        float nCavernHeightVariation = cavernStructureConfig.getHeightVariation();

                        float biomeWeight = this.weightTable.get(neighborX, neighborZ) / (nBaseHeight + 2.0F);
                        if (neighborBiome.getBaseHeight() > originBiome.getBaseHeight()) {
                            biomeWeight *= 2.0F;
                        }

                        heightVariation += nHeightVariation * biomeWeight;
                        baseHeight += nBaseHeight * biomeWeight;
                        ridgeWeight += nRidgeWeight * biomeWeight;
                        densityScale += nDensityScale * biomeWeight;
                        cavernFloorHeight += nCavernFloorHeight * biomeWeight;
                        cavernCeilingHeight += nCavernCeilingHeight * biomeWeight;
                        cavernDensityScale += nCavernDensityScale * biomeWeight;
                        cavernHeightVariation += nCavernHeightVariation * biomeWeight;

                        totalWeight += biomeWeight;
                    }
                }

                heightVariation /= totalWeight;
                baseHeight /= totalWeight;
                ridgeWeight /= totalWeight;
                densityScale /= totalWeight;
                cavernFloorHeight /= totalWeight;
                cavernCeilingHeight /= totalWeight;
                cavernDensityScale /= totalWeight;
                cavernHeightVariation /= totalWeight;

                baseHeight += heightOrigin;
                cavernFloorHeight = cavernFloorHeight * caveHeightRange + minCaveHeight;
                cavernCeilingHeight = cavernCeilingHeight * caveHeightRange + minCaveHeight;

                double cavernCenter = (cavernFloorHeight + cavernCeilingHeight) / 2.0;
                double cavernHeight = cavernCeilingHeight - cavernFloorHeight;

                heightVariation = heightVariation * 0.9F + 0.1F;
                cavernHeightVariation = cavernHeightVariation * 0.9F + 0.1F;

                double perlinSurfaceNoise = this.surfaceBuffer[surfaceIndex];
                double perlinCeilingNoise = this.ceilingBuffer[surfaceIndex];

                double pillarDensity = Math.pow((this.pillarBuffer[surfaceIndex] + 1.0) * 0.5, 4.0);

                double ridgedSurfaceNoise = this.ridgedSurfaceBuffer[surfaceIndex];
                surfaceIndex++;

                double surfaceHeight = perlinSurfaceNoise + (ridgedSurfaceNoise - perlinSurfaceNoise) * ridgeWeight;
                surfaceHeight = (surfaceHeight + 1.5) / 3.0;
                surfaceHeight = (surfaceHeight * heightVariation * 2.0) + baseHeight;

                double cavernRegionStart = cavernFloorHeight + perlinSurfaceNoise;
                double cavernRegionEnd = cavernCeilingHeight + perlinCeilingNoise;

                double curveRange = 8.0 / VERTICAL_GRANULARITY;
                RegionInterpolator.Region[] regions = new RegionInterpolator.Region[] {
                        RegionInterpolator.region(0.0, cavernRegionStart, 2.5, curveRange),
                        RegionInterpolator.region(cavernRegionStart, cavernRegionEnd, -5.0, curveRange),
                        RegionInterpolator.region(cavernRegionEnd, surfaceHeight, 2.5, curveRange),
                        RegionInterpolator.region(surfaceHeight, maxHeight, surfaceHeight - maxHeight, maxHeight - surfaceHeight)
                };

                RegionInterpolator interpolator = new RegionInterpolator(regions, Curve.linear());

                for (int localY = 0; localY < NOISE_HEIGHT + 1; localY++) {
                    double surfaceWeight = MathHelper.clamp((localY - cavernRegionEnd) / (surfaceHeight - cavernRegionEnd), 0.0, 1.0);
                    double cavernWeight = 1.0 - surfaceWeight;

                    double densityBias = interpolator.get(localY);

                    double cavernCenterDistance = Math.min(Math.abs(localY - cavernCenter) / cavernHeight, 1.0);
                    double pillarFalloff = Math.max(1.0 - Math.pow(cavernCenterDistance, 2.0), 0.0) * 0.125;

                    densityBias += Math.max(pillarDensity * 3.5 - pillarFalloff, 0.0) * cavernWeight * 5.0;

                    double sampledNoise = this.worldNoiseBuffer[index];
                    double surfaceNoiseDensity = sampledNoise * Math.pow(heightVariation * 2.0, 3.0);
                    double cavernNoiseDensity = sampledNoise * Math.pow(cavernHeightVariation * 2.0, 3.0);
                    double noiseDensity = (surfaceNoiseDensity * surfaceWeight) + (cavernNoiseDensity * cavernWeight);

                    this.terrainBuffer[index] = noiseDensity + densityBias;

                    index++;
                }
            }
        }
    }

    private Biome sampleNoiseBiome(int x, int z) {
        return this.biomeNoiseBuffer[(x + BIOME_NOISE_OFFSET) + (z + BIOME_NOISE_OFFSET) * BIOME_NOISE_SIZE];
    }

    private CavernousBiome sampleNoiseCavernBiome(int x, int z) {
        return this.cavernNoiseBuffer[(x + BIOME_NOISE_OFFSET) + (z + BIOME_NOISE_OFFSET) * BIOME_NOISE_SIZE];
    }

    private void coverSurface(ChunkPrimer primer, int chunkX, int chunkZ) {
        if (!ForgeEventFactory.onReplaceBiomeBlocks(this, chunkX, chunkZ, primer, this.world)) {
            return;
        }

        int globalX = chunkX << 4;
        int globalZ = chunkZ << 4;
        this.biomeBuffer = this.world.getBiomeProvider().getBiomes(this.biomeBuffer, globalX, globalZ, 16, 16);

        this.depthNoise.sample2D(this.depthBuffer, globalX, globalZ, 16, 16);

        for (int localZ = 0; localZ < 16; localZ++) {
            for (int localX = 0; localX < 16; localX++) {
                int index = localX + localZ * 16;
                Biome biome = this.biomeBuffer[index];
                double depth = this.depthBuffer[index];

                biome.genTerrainBlocks(this.world, this.random, primer, globalZ + localZ, globalX + localX, depth);
            }
        }
    }

    @Override
    public void populate(int chunkX, int chunkZ) {
        BlockFalling.fallInstantly = true;

        try {
            int globalX = chunkX << 4;
            int globalZ = chunkZ << 4;

            BlockPos origin = new BlockPos(globalX, 0, globalZ);
            Biome biome = this.world.getBiome(origin.add(16, 0, 16));

            this.random.setSeed(this.world.getSeed());
            long chunkSeedX = this.random.nextLong() / 2L * 2L + 1L;
            long chunkSeedZ = this.random.nextLong() / 2L * 2L + 1L;
            this.random.setSeed(chunkX * chunkSeedX + chunkZ * chunkSeedZ ^ this.world.getSeed());

            ForgeEventFactory.onChunkPopulate(true, this, this.world, this.random, chunkX, chunkZ, false);

            biome.decorate(this.world, this.random, new BlockPos(globalX, 0, globalZ));
            if (TerrainGen.populate(this, this.world, this.random, chunkX, chunkZ, false, PopulateChunkEvent.Populate.EventType.ANIMALS)) {
                int originX = globalX + 8;
                int originZ = globalZ + 8;
                WorldEntitySpawner.performWorldGenSpawning(this.world, biome, originX, originZ, 16, 16, this.random);
            }

            ForgeEventFactory.onChunkPopulate(false, this, this.world, this.random, chunkX, chunkZ, false);
        } finally {
            BlockFalling.fallInstantly = false;
        }
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

    private BiomeLayerSampler<CavernousBiome> getCavernousBiomeSampler() {
        MultiLayerBiomeSampler multiLayerSampler = this.world.getCapability(Midnight.MULTI_LAYER_BIOME_SAMPLER_CAP, null);
        if (multiLayerSampler != null) {
            BiomeLayerSampler<CavernousBiome> layer = multiLayerSampler.getLayer(MidnightBiomeLayer.UNDERGROUND);
            if (layer != null) {
                return layer;
            }
        }

        return DEFAULT_CAVERN_SAMPLER;
    }
}
