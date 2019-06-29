package com.mushroom.midnight.common.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkPrimer;

public class SurfaceCoverGenerator {
    private final int minSurfaceLayer;
    private final int maxSurfaceLayer;

    private int maxY = 255;

    public SurfaceCoverGenerator(int minSurfaceLayer, int maxSurfaceLayer) {
        this.minSurfaceLayer = minSurfaceLayer;
        this.maxSurfaceLayer = maxSurfaceLayer;
    }

    public SurfaceCoverGenerator withMaxY(int maxY) {
        this.maxY = maxY;
        return this;
    }

    public void coverSurface(SurfaceConfig config, ChunkPrimer primer, int x, int z, int depth) {
        BlockState topBlock = config.getTopState();
        BlockState fillerBlock = config.getFillerState();

        int currentDepth = -1;
        int localX = x & 15;
        int localZ = z & 15;

        boolean wet = false;
        int surfaceLayer = 0;

        for (int localY = this.maxY; localY >= 0; localY--) {
            BlockPos pos = new BlockPos(localX, localY, localZ);
            BlockState state = primer.getBlockState(pos);
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
                    primer.setBlockState(pos, wet ? config.getWetState() : topBlock, false);
                } else {
                    primer.setBlockState(pos, wet ? config.getWetState() : fillerBlock, false);
                }
            }
        }
    }
}
