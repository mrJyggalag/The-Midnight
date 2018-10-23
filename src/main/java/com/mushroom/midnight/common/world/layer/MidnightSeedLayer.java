package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.IMidnightBiomeSpawnEntry;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class MidnightSeedLayer extends GenLayer {
    public MidnightSeedLayer(long seed) {
        super(seed);
    }

    @Override
    public int[] getInts(int originX, int originY, int width, int height) {
        int[] result = IntCache.getIntCache(width * height);

        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                this.initChunkSeed(originX + localX, originY + localY);

                Biome biome = Biomes.DEFAULT;
                IMidnightBiomeSpawnEntry entry = MidnightBiomeGroup.BASE.selectEntry(this::nextInt);
                if (entry != null) {
                    Biome selectedBiome = entry.selectBiome(this::nextInt);
                    if (selectedBiome != null) {
                        biome = selectedBiome;
                    }
                }

                result[localX + localY * width] = Biome.getIdForBiome(biome);
            }
        }

        return result;
    }
}
