package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BiomeLayerSampler;
import com.mushroom.midnight.common.biome.MidnightBiomeLayer;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.capability.MidnightWorldSpawners;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightCavernousBiomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.GameRules;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;

import static com.mushroom.midnight.common.world.MidnightNoiseGenerator.HORIZONTAL_GRANULARITY;
import static com.mushroom.midnight.common.world.MidnightNoiseGenerator.VERTICAL_GRANULARITY;

public class MidnightChunkGenerator extends NoiseChunkGenerator<MidnightChunkGenerator.Config> {
    private static final BiomeLayerSampler<CavernousBiome> DEFAULT_CAVERN_SAMPLER = new BiomeLayerSampler.Constant<>(MidnightCavernousBiomes.CLOSED_CAVERN);

    public static final int SURFACE_LEVEL = 78;

    public static final int MIN_CAVE_HEIGHT = 20;
    public static final int MAX_CAVE_HEIGHT = 46;

    public static final int MIN_SURFACE_LEVEL = MAX_CAVE_HEIGHT + 12;

    private final World world;
    private final MidnightNoiseGenerator noiseGenerator;

    public MidnightChunkGenerator(World world, BiomeProvider biomeProvider, Config config) {
        super(world, biomeProvider, HORIZONTAL_GRANULARITY, VERTICAL_GRANULARITY, 256, config, true);
        this.world = world;
        this.noiseGenerator = new MidnightNoiseGenerator(this.randomSeed);
    }

    // TODO: Cover cavernous biomes

    @Override
    public void generateBiomes(IChunk chunk) {
        super.generateBiomes(chunk);

        // TODO: chunk primer doesn't store capabilities.. PR forge for this support
        chunk.getCapability(Midnight.CAVERNOUS_BIOME_CAP)
                .ifPresent(store -> {
                    store.populate(MidnightChunkGenerator.this.cavernBiomeBuffer);
                });
    }

    @Override
    public void spawnMobs(WorldGenRegion region) {
        int chunkX = region.getMainChunkX();
        int chunkZ = region.getMainChunkZ();

        SharedSeedRandom random = new SharedSeedRandom();
        random.setDecorationSeed(region.getSeed(), chunkX << 4, chunkZ << 4);

        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            this.world.getCapability(Midnight.WORLD_SPAWNERS_CAP).ifPresent(MidnightWorldSpawners::populateChunk);
        }
    }

    @Override
    public void spawnMobs(ServerWorld world, boolean p_203222_2_, boolean p_203222_3_) {
        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            this.world.getCapability(Midnight.WORLD_SPAWNERS_CAP).ifPresent(MidnightWorldSpawners::spawnAroundPlayers);
        }
    }

    @Override
    protected void func_222548_a(double[] noise, int x, int z) {
        this.noiseGenerator.populateColumnNoise(noise, x, z);
    }

    @Override
    protected double func_222545_a(double x, double z, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected double[] func_222549_a(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getGroundHeight() {
        return this.world.getSeaLevel() + 1;
    }

    @Override
    public int getSeaLevel() {
        return SURFACE_LEVEL + 2;
    }

    private BiomeLayerSampler<CavernousBiome> getCavernousBiomeSampler() {
        return this.world.getCapability(Midnight.MULTI_LAYER_BIOME_SAMPLER_CAP, null).map(multiLayerSampler -> {
            BiomeLayerSampler<CavernousBiome> layer = multiLayerSampler.getLayer(MidnightBiomeLayer.UNDERGROUND);
            return layer != null ? layer : DEFAULT_CAVERN_SAMPLER;
        }).orElse(DEFAULT_CAVERN_SAMPLER);
    }

    public static class Config extends GenerationSettings {
        public static Config createDefault() {
            Config config = new Config();
            config.setDefaultBlock(MidnightBlocks.NIGHTSTONE.getDefaultState());
            config.setDefaultFluid(MidnightBlocks.DARK_WATER.getDefaultState());

            return config;
        }
    }
}
