package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BiomeLayerSampler;
import com.mushroom.midnight.common.biome.MidnightBiomeLayer;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.capability.CavernousBiomeStore;
import com.mushroom.midnight.common.capability.MidnightWorldSpawners;
import com.mushroom.midnight.common.capability.MultiLayerBiomeSampler;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModCavernousBiomes;
import com.mushroom.midnight.common.world.generator.WorldGenMidnightCaves;
import com.mushroom.midnight.common.world.generator.WorldGenMoltenCrater;
import com.mushroom.midnight.common.world.noise.OctaveNoiseSampler;
import com.mushroom.midnight.common.world.util.NoiseChunkPrimer;
import net.minecraft.block.BlockFalling;
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
import net.minecraft.world.gen.MapGenBase;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.mushroom.midnight.common.world.MidnightNoiseGenerator.*;

public class MidnightChunkGenerator implements IChunkGenerator, PartialChunkGenerator {
    private static final BiomeLayerSampler<CavernousBiome> DEFAULT_CAVERN_SAMPLER = new BiomeLayerSampler.Constant<>(ModCavernousBiomes.CLOSED_CAVERN);

    public static final int SURFACE_LEVEL = 78;

    public static final int MIN_CAVE_HEIGHT = 20;
    public static final int MAX_CAVE_HEIGHT = 46;

    public static final int MIN_SURFACE_LEVEL = MAX_CAVE_HEIGHT + 12;

    private static final IBlockState STONE = ModBlocks.NIGHTSTONE.getDefaultState();
    private static final IBlockState WATER = ModBlocks.DARK_WATER.getDefaultState();
    private static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();

    private final World world;
    private final Random random;

    private Biome[] biomeBuffer;
    private final CavernousBiome[] cavernBiomeBuffer = new CavernousBiome[256];

    private Biome[] biomeNoiseBuffer;
    private final CavernousBiome[] cavernNoiseBuffer = new CavernousBiome[BIOME_NOISE_SIZE * BIOME_NOISE_SIZE];

    private final MidnightNoiseGenerator noiseGenerator;
    private final OctaveNoiseSampler depthNoise;

    private final double[] depthBuffer = new double[256];

    private final MapGenBase caveGenerator;
    private final MapGenBase craterGenerator;

    private final NoiseChunkPrimer noisePrimer;

    public MidnightChunkGenerator(World world) {
        this.world = world;
        this.random = new Random(world.getSeed());

        this.depthNoise = OctaveNoiseSampler.perlin(this.random, 4);
        this.depthNoise.setFrequency(0.0625);

        this.noiseGenerator = new MidnightNoiseGenerator(this.random);
        this.noisePrimer = new NoiseChunkPrimer(HORIZONTAL_GRANULARITY, VERTICAL_GRANULARITY, NOISE_WIDTH, NOISE_HEIGHT);

        this.caveGenerator = TerrainGen.getModdedMapGen(new WorldGenMidnightCaves(), InitMapGenEvent.EventType.CAVE);
        this.craterGenerator = TerrainGen.getModdedMapGen(new WorldGenMoltenCrater(this.random, this), InitMapGenEvent.EventType.CUSTOM);

        this.world.setSeaLevel(SURFACE_LEVEL + 1);
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
            cavernousBiomeStore.populate(this.cavernBiomeBuffer);
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
        this.getCavernousBiomeSampler().sample(this.cavernBiomeBuffer, globalX, globalZ, 16, 16);

        this.coverSurface(primer, chunkX, chunkZ);

        this.caveGenerator.generate(this.world, chunkX, chunkZ, primer);
        this.craterGenerator.generate(this.world, chunkX, chunkZ, primer);
    }

    @Override
    public void primeChunkBare(ChunkPrimer primer, int chunkX, int chunkZ) {
        int originX = chunkX * HORIZONTAL_GRANULARITY - BIOME_NOISE_OFFSET;
        int originZ = chunkZ * HORIZONTAL_GRANULARITY - BIOME_NOISE_OFFSET;
        this.biomeNoiseBuffer = this.world.getBiomeProvider().getBiomesForGeneration(this.biomeNoiseBuffer, originX, originZ, BIOME_NOISE_SIZE, BIOME_NOISE_SIZE);

        this.getCavernousBiomeSampler().sampleNoise(this.cavernNoiseBuffer, originX, originZ, BIOME_NOISE_SIZE, BIOME_NOISE_SIZE);

        double[] terrainBuffer = this.noiseGenerator.populateNoise(chunkX, chunkZ, this.biomeNoiseBuffer, this.cavernNoiseBuffer);

        int seaLevel = this.world.getSeaLevel();
        this.noisePrimer.primeChunk(primer, terrainBuffer, (density, x, y, z) -> {
            if (density > 0.0F) {
                return STONE;
            } else if (y < seaLevel && y > MIN_SURFACE_LEVEL) {
                return WATER;
            }
            return null;
        });
    }

    private void coverSurface(ChunkPrimer primer, int chunkX, int chunkZ) {
        if (!ForgeEventFactory.onReplaceBiomeBlocks(this, chunkX, chunkZ, primer, this.world)) {
            return;
        }

        int globalX = chunkX << 4;
        int globalZ = chunkZ << 4;

        this.depthNoise.sample2D(this.depthBuffer, globalX, globalZ, 16, 16);

        for (int localZ = 0; localZ < 16; localZ++) {
            for (int localX = 0; localX < 16; localX++) {
                int index = localX + localZ * 16;

                Biome biome = this.biomeBuffer[index];
                CavernousBiome cavernousBiome = this.cavernBiomeBuffer[index];
                double depth = this.depthBuffer[index];

                for (int localY = 0; localY < 5; localY++) {
                    if (localY <= this.random.nextInt(5)) {
                        primer.setBlockState(localX, localY, localZ, BEDROCK);
                    }
                }

                biome.genTerrainBlocks(this.world, this.random, primer, globalZ + localZ, globalX + localX, depth);
                cavernousBiome.coverSurface(this.random, primer, globalX + localX, globalZ + localZ, depth);
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
            CavernousBiome cavernousBiome = CavernousBiomeStore.getBiome(this.world, globalX + 16, globalZ + 16);

            this.random.setSeed(this.world.getSeed());
            long chunkSeedX = this.random.nextLong() / 2L * 2L + 1L;
            long chunkSeedZ = this.random.nextLong() / 2L * 2L + 1L;
            this.random.setSeed(chunkX * chunkSeedX + chunkZ * chunkSeedZ ^ this.world.getSeed());

            ForgeEventFactory.onChunkPopulate(true, this, this.world, this.random, chunkX, chunkZ, false);

            biome.decorate(this.world, this.random, origin);
            cavernousBiome.decorate(this.world, this.random, origin);

            if (TerrainGen.populate(this, this.world, this.random, chunkX, chunkZ, false, PopulateChunkEvent.Populate.EventType.ANIMALS)) {
                MidnightWorldSpawners worldSpawners = this.world.getCapability(Midnight.WORLD_SPAWNERS_CAP, null);
                if (worldSpawners != null) {
                    worldSpawners.populateChunk(chunkX, chunkZ, this.random);
                }
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
