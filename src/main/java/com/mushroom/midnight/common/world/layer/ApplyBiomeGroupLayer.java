package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.config.BiomeSpawnEntry;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class ApplyBiomeGroupLayer extends GenLayer {
    private final MidnightBiomeGroup layer;

    public ApplyBiomeGroupLayer(long seed, GenLayer parent, MidnightBiomeGroup layer) {
        super(seed);
        this.parent = parent;
        this.layer = layer;
    }

    @Override
    public int[] getInts(int originX, int originY, int width, int height) {
        int[] result = IntCache.getIntCache(width * height);
        int[] parent = this.parent.getInts(originX, originY, width, height);

        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                this.initChunkSeed(localX + originX, localY + originY);
                int index = localX + localY * width;
                result[index] = this.apply(parent[index]);
            }
        }

        return result;
    }

    private int apply(int parentValue) {
        BiomeSpawnEntry entry = this.layer.selectEntry(this::nextInt);
        if (entry.canReplace(Biome.getBiome(parentValue))) {
            Biome biome = entry.selectBiome(this::nextInt);
            if (biome != null) {
                return Biome.getIdForBiome(biome);
            }
        }
        return parentValue;
    }
}
