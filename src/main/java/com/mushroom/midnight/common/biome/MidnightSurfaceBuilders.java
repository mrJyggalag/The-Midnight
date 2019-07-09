package com.mushroom.midnight.common.biome;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.LayeredSurfaceBuilder;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public final class MidnightSurfaceBuilders {
    public static final SurfaceBuilder<SurfaceBuilderConfig> SURFACE = new LayeredSurfaceBuilder(SurfaceBuilderConfig::deserialize, 0, 0);

    public static final SurfaceBuilder<SurfaceBuilderConfig> CAVERN = new LayeredSurfaceBuilder(SurfaceBuilderConfig::deserialize, 1, Integer.MAX_VALUE)
            .withMaxY(MidnightChunkGenerator.SURFACE_CAVE_BOUNDARY);

    public static final SurfaceBuilderConfig GRASS_DIRT_MUD_CONFIG = new SurfaceBuilderConfig(
            MidnightBlocks.GRASS_BLOCK.getDefaultState(),
            MidnightBlocks.DIRT.getDefaultState(),
            MidnightBlocks.DECEITFUL_MUD.getDefaultState()
    );

    public static final SurfaceBuilderConfig NIGHTSTONE_CONFIG = new SurfaceBuilderConfig(
            MidnightBlocks.NIGHTSTONE.getDefaultState(),
            MidnightBlocks.NIGHTSTONE.getDefaultState(),
            MidnightBlocks.NIGHTSTONE.getDefaultState()
    );

    public static final SurfaceBuilderConfig PEAT_CONFIG = new SurfaceBuilderConfig(
            MidnightBlocks.DECEITFUL_PEAT.getDefaultState(),
            MidnightBlocks.DECEITFUL_PEAT.getDefaultState(),
            MidnightBlocks.DECEITFUL_MUD.getDefaultState()
    );

    public static final SurfaceBuilderConfig MUD_CONFIG = new SurfaceBuilderConfig(
            MidnightBlocks.DECEITFUL_MUD.getDefaultState(),
            MidnightBlocks.DECEITFUL_MUD.getDefaultState(),
            MidnightBlocks.DECEITFUL_MUD.getDefaultState()
    );

    public static final SurfaceBuilderConfig MYCELIUM_CONFIG = new SurfaceBuilderConfig(
            MidnightBlocks.MYCELIUM.getDefaultState(),
            MidnightBlocks.NIGHTSTONE.getDefaultState(),
            MidnightBlocks.NIGHTSTONE.getDefaultState()
    );

    public static final SurfaceBuilder<SurfaceBuilderConfig> BOG = new BogSurfaceBuilder(SurfaceBuilderConfig::deserialize);

    private static class BogSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
        public BogSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> deserialize) {
            super(deserialize);
        }

        @Override
        public void buildSurface(Random random, IChunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
            noise += (random.nextDouble() - 0.5) * 0.1;
            if (noise < 0.05) {
                config = MUD_CONFIG;
            }

            SURFACE.buildSurface(random, chunk, biome, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, config);
        }
    }
}
