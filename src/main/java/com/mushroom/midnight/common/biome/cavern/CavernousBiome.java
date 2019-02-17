package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.common.biome.EntitySpawnConfigured;
import com.mushroom.midnight.common.biome.MidnightBiomeDecorator;
import com.mushroom.midnight.common.biome.config.SpawnerConfig;
import com.mushroom.midnight.common.biome.config.SurfaceConfig;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import com.mushroom.midnight.common.world.SurfaceCoverGenerator;
import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Random;

public class CavernousBiome extends IForgeRegistryEntry.Impl<CavernousBiome> implements EntitySpawnConfigured {
    private final CavernousBiomeConfig config;
    private final MidnightBiomeDecorator decorator;

    private final SurfaceCoverGenerator coverGenerator = new SurfaceCoverGenerator(1, Integer.MAX_VALUE);

    public CavernousBiome(CavernousBiomeConfig config) {
        this.config = config;
        this.decorator = this.config.getFeatureConfig().createDecorator(PlacementLevel.INSTANCE);
    }

    public void decorate(World world, Random random, BlockPos pos) {
        this.decorator.decorate(world, random, Biomes.DEFAULT, pos);
    }

    public void coverSurface(Random random, ChunkPrimer primer, int x, int z, double noiseVal) {
        SurfaceConfig config = this.config.getSurfaceConfig();
        if (config == null) {
            return;
        }

        int fillerDepth = (int) (noiseVal / 3.0 + 3.0 + random.nextDouble() * 0.25);
        this.coverGenerator.coverSurface(config, primer, x, z, fillerDepth);
    }

    public CavernousBiomeConfig getConfig() {
        return this.config;
    }

    @Override
    public SpawnerConfig getSpawnerConfig() {
        return this.config.getSpawnerConfig();
    }

    public static class PlacementLevel implements SurfacePlacementLevel {
        public static final SurfacePlacementLevel INSTANCE = new PlacementLevel();

        private PlacementLevel() {
        }

        @Override
        public BlockPos getSurfacePos(World world, BlockPos pos) {
            Chunk chunk = world.getChunk(pos);

            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            for (int y = 5; y < MidnightChunkGenerator.MIN_SURFACE_LEVEL; y++) {
                mutablePos.setPos(pos.getX(), y, pos.getZ());

                IBlockState state = chunk.getBlockState(mutablePos);
                if (state.getMaterial() == Material.AIR) {
                    return mutablePos.toImmutable();
                }
            }

            return pos;
        }

        @Override
        public int generateUpTo(World world, Random random, int y) {
            return random.nextInt(Math.min(y, MidnightChunkGenerator.MIN_SURFACE_LEVEL));
        }
    }
}
