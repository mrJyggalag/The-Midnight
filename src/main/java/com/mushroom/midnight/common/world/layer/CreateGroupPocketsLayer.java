package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.biome.config.BiomeSpawnEntry;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class CreateGroupPocketsLayer extends GenLayer {
    private final MidnightBiomeGroup group;
    private final int chance;

    public CreateGroupPocketsLayer(long seed, GenLayer parent, MidnightBiomeGroup group, int chance) {
        super(seed);
        this.parent = parent;
        this.group = group;
        this.chance = chance;
    }

    @Override
    public int[] getInts(int originX, int originY, int width, int height) {
        int[] result = IntCache.getIntCache(width * height);
        int[] parent = this.parent.getInts(originX, originY, width, height);

        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                this.initChunkSeed(localX + originX, localY + originY);
                int index = localX + localY * width;
                if (this.nextInt(this.chance) == 0) {
                    result[index] = this.apply(parent[index]);
                } else {
                    result[index] = parent[index];
                }
            }
        }

        return result;
    }

    private int apply(int parentValue) {
        MidnightBiomeGroup.Pool pool = this.group.getPoolForBiome(parentValue);
        BiomeSpawnEntry entry = pool.selectEntry(this::nextInt);
        if (entry != null) {
            return entry.getBiomeId();
        }
        return parentValue;
    }
}
