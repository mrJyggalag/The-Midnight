package com.mushroom.midnight.common.world.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class OutlineProducerLayer extends GenLayer {
    public OutlineProducerLayer(long seed, GenLayer parent) {
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

                if (right != current || bottom != current) {
                    result[localX + localY * width] = 1;
                } else {
                    result[localX + localY * width] = 0;
                }
            }
        }

        return result;
    }
}
