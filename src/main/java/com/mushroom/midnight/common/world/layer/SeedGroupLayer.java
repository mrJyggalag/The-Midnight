package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.biome.config.BiomeSpawnEntry;
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
        int[] result = IntCache.getIntCache(width * height);

        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                this.initChunkSeed(localX + originX, localY + originY);

                BiomeSpawnEntry entry = this.group.selectEntry(this::nextInt);
                result[localX + localY * width] = entry.getBiomeId();
            }
        }

        return result;
    }
}
