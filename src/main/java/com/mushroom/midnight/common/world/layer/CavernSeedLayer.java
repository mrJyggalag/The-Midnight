package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.biome.BiomeSpawnEntry;
import com.mushroom.midnight.common.registry.MidnightCavernousBiomes;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class CavernSeedLayer extends GenLayer {
    private final MidnightBiomeGroup group;
    private final int closedCavernId;

    public CavernSeedLayer(long seed, MidnightBiomeGroup group) {
        super(seed);
        this.group = group;
        this.closedCavernId = MidnightCavernousBiomes.getId(MidnightCavernousBiomes.CLOSED_CAVERN);
    }

    @Override
    public int[] getInts(int originX, int originY, int width, int height) {
        int[] result = IntCache.getIntCache(width * height);

        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                result[localX + localY * width] = this.selectBiome(localX + originX, localY + originY);
            }
        }

        return result;
    }

    private int selectBiome(int globalX, int globalY) {
        this.initChunkSeed(globalX, globalY);
        if (this.nextInt(2) == 0) {
            BiomeSpawnEntry entry = this.group.getGlobalPool().selectEntry(this::nextInt);
            if (entry != null) {
                return entry.getBiomeId();
            }
        }
        return this.closedCavernId;
    }
}
