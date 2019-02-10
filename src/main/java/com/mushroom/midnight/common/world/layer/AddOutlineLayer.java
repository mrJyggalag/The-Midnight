package com.mushroom.midnight.common.world.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class AddOutlineLayer extends GenLayer {
    private final int outline;

    public AddOutlineLayer(long seed, GenLayer parent, int outline) {
        super(seed);
        this.parent = parent;
        this.outline = outline;
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
                    result[localX + localY * width] = this.outline;
                } else {
                    result[localX + localY * width] = current;
                }
            }
        }

        return result;
    }
}
