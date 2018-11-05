package com.mushroom.midnight.common.world.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class CellSeedLayer extends GenLayer {
    public CellSeedLayer(long seed) {
        super(seed);
    }

    @Override
    public int[] getInts(int originX, int originY, int width, int height) {
        int[] result = IntCache.getIntCache(width * height);
        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                this.initChunkSeed(originX + localX, originY + localY);
                result[localX + localY * width] = this.nextInt(50);
            }
        }

        return result;
    }
}
