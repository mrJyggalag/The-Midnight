package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.registry.ModBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class CraterEdgeLayer extends GenLayer {
    private static final int CRATER_ID = Biome.getIdForBiome(ModBiomes.MOLTEN_CRATER);
    private static final int CRATER_EDGE_ID = Biome.getIdForBiome(ModBiomes.MOLTEN_CRATER_EDGE);

    public CraterEdgeLayer(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int originX, int originY, int width, int height) {
        int sampleWidth = width + 1;
        int sampleHeight = height + 1;

        int[] parent = this.parent.getInts(originX, originY, sampleWidth, sampleHeight);
        int[] result = IntCache.getIntCache(width * height);

        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                int sampleIndex = localX + localY * sampleWidth;

                int current = parent[sampleIndex];
                int right = parent[sampleIndex + 1];
                int bottom = parent[sampleIndex + sampleWidth];

                boolean edge = right != current || bottom != current;
                if (edge && (current == CRATER_ID || right == CRATER_ID || bottom == CRATER_ID)) {
                    result[localX + localY * width] = CRATER_EDGE_ID;
                } else {
                    result[localX + localY * width] = current;
                }
            }
        }

        return result;
    }
}
