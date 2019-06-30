package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.biome.BiomeSpawnEntry;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class SeedGroupLayer extends GenLayer {
    private final MidnightBiomeGroup group;

    public SeedGroupLayer(long seed, MidnightBiomeGroup group) {
        super(seed);
        this.group = group;
    }

    @Override
    public int[] getInts(int originX, int originY, int width, int height) {
        MidnightBiomeGroup.Pool pool = this.group.getGlobalPool();
        int[] result = IntCache.getIntCache(width * height);

        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                this.initChunkSeed(localX + originX, localY + originY);

                BiomeSpawnEntry entry = pool.selectEntry(this::nextInt);
                if (entry != null) {
                    result[localX + localY * width] = entry.getBiomeId();
                } else {
                    result[localX + localY * width] = 0;
                }
            }
        }

        return result;
    }
}
