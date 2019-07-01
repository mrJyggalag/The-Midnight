package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.MidnightWorldSpawners;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.GameRules;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;

import static com.mushroom.midnight.common.world.MidnightNoiseGenerator.HORIZONTAL_GRANULARITY;
import static com.mushroom.midnight.common.world.MidnightNoiseGenerator.VERTICAL_GRANULARITY;

public class MidnightChunkGenerator extends NoiseChunkGenerator<MidnightChunkGenerator.Config> {
    public static final int SURFACE_LEVEL = 78;

    public static final int MIN_CAVE_HEIGHT = 20;
    public static final int MAX_CAVE_HEIGHT = 46;

    public static final int MIN_SURFACE_LEVEL = MAX_CAVE_HEIGHT + 12;

    public static final int SEA_LEVEL = SURFACE_LEVEL + 2;

    private final World world;
    private final MidnightNoiseGenerator noiseGenerator;

    public MidnightChunkGenerator(World world, BiomeProvider biomeProvider, Config config) {
        super(world, biomeProvider, HORIZONTAL_GRANULARITY, VERTICAL_GRANULARITY, 256, config, true);
        this.world = world;
        this.noiseGenerator = new MidnightNoiseGenerator(this.randomSeed);
    }

    // TODO: Cover and generate features for cavernous biomes
    // TODO: generate cavern biomes whenever we need, we can't store them atm

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
        return SEA_LEVEL;
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
