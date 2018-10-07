package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.registry.ModBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import java.util.List;

public class MidnightSeedLayer extends GenLayer {
    public MidnightSeedLayer(long seed) {
        super(seed);
    }

    @Override
    public int[] getInts(int originX, int originY, int width, int height) {
        List<Biome> seedBiomes = ModBiomes.getSeedBiomes();

        int[] result = IntCache.getIntCache(width * height);
        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                this.initChunkSeed(originX + localX, originY + localY);
                Biome biome = seedBiomes.get(this.nextInt(seedBiomes.size()));
                result[localX + localY * width] = Biome.getIdForBiome(biome);
            }
        }

        return result;
    }
}
