package com.mushroom.midnight.common.world;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class LayeredSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    private final int minSurfaceLayer;
    private final int maxSurfaceLayer;

    private int maxY = 255;

    public LayeredSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> deserialize, int minSurfaceLayer, int maxSurfaceLayer) {
        super(deserialize);
        this.minSurfaceLayer = minSurfaceLayer;
        this.maxSurfaceLayer = maxSurfaceLayer;
    }

    public LayeredSurfaceBuilder withMaxY(int maxY) {
        this.maxY = maxY;
        return this;
    }

    @Override
    public void buildSurface(Random random, IChunk chunk, Biome biome, int x, int z, int maxY, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        BlockState top = config.getTop();
        BlockState under = config.getUnder();
        BlockState underWater = config.getUnderWaterMaterial();

        int depth = (int) (noise / 3.0 + 3.0 + random.nextDouble() * 0.25);

        int currentDepth = -1;
        int localX = x & 15;
        int localZ = z & 15;

        boolean wet = false;
        int surfaceLayer = 0;

        for (int localY = Math.min(this.maxY, maxY); localY >= 0; localY--) {
            BlockPos pos = new BlockPos(localX, localY, localZ);
            BlockState state = chunk.getBlockState(pos);
            Material material = state.getMaterial();
            if (material == Material.WATER) {
                wet = true;
            } else if (material == Material.AIR) {
                wet = false;
            }

            if (material != Material.ROCK) {
                currentDepth = -1;
                continue;
            }

            if (currentDepth >= depth) {
                wet = false;
                if (surfaceLayer++ > this.maxSurfaceLayer) {
                    break;
                } else {
                    continue;
                }
            }

            currentDepth++;

            if (surfaceLayer >= this.minSurfaceLayer && surfaceLayer <= this.maxSurfaceLayer) {
                if (currentDepth == 0) {
                    chunk.setBlockState(pos, wet ? underWater : top, false);
                } else {
                    chunk.setBlockState(pos, wet ? underWater : under, false);
                }
            }
        }
    }
}
