package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.registry.ModBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class ValleyMergeLayer extends GenLayer {
    private static final int VALLEY_ID = Biome.getIdForBiome(ModBiomes.PHANTASMAL_VALLEY);
    private static final int PLATEUA_ID = Biome.getIdForBiome(ModBiomes.OBSCURED_PLATEAU);

    private final GenLayer ridgeParent;

    public ValleyMergeLayer(long seed, GenLayer parent, GenLayer ridgeParent) {
        super(seed);
        this.parent = parent;
        this.ridgeParent = ridgeParent;
    }

    @Override
    public int[] getInts(int originX, int originY, int width, int height) {
        int[] result = IntCache.getIntCache(width * height);
        int[] parent = this.parent.getInts(originX, originY, width, height);
        int[] ridge = this.ridgeParent.getInts(originX, originY, width, height);

        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                int index = localX + localY * width;
                int parentValue = parent[index];
                if (ridge[index] == 1 && parentValue == PLATEUA_ID) {
                    result[index] = VALLEY_ID;
                } else {
                    result[index] = parentValue;
                }
            }
        }

        return result;
    }
}
