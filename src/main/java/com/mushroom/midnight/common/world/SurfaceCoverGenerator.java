package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.biome.config.SurfaceConfig;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class SurfaceCoverGenerator {
    private final int minSurfaceLayer;
    private final int maxSurfaceLayer;

    public SurfaceCoverGenerator(int minSurfaceLayer, int maxSurfaceLayer) {
        this.minSurfaceLayer = minSurfaceLayer;
        this.maxSurfaceLayer = maxSurfaceLayer;
    }

    public void coverSurface(World world, SurfaceConfig config, ChunkPrimer primer, int x, int z, int depth) {
        IBlockState topBlock = config.getTopState();
        IBlockState fillerBlock = config.getFillerState();

        int currentDepth = -1;
        int localX = x & 15;
        int localZ = z & 15;

        boolean wet = false;
        int surfaceLayer = 0;

        for (int localY = 255; localY >= 0; localY--) {
            IBlockState state = primer.getBlockState(localX, localY, localZ);
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
                surfaceLayer++;
                continue;
            }

            currentDepth++;

            if (surfaceLayer >= this.minSurfaceLayer && surfaceLayer <= this.maxSurfaceLayer) {
                if (currentDepth == 0) {
                    primer.setBlockState(localX, localY, localZ, wet ? config.getWetState() : topBlock);
                } else {
                    primer.setBlockState(localX, localY, localZ, wet ? config.getWetState() : fillerBlock);
                }
            }
        }
    }
}
