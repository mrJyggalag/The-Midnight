package com.mushroom.midnight.common.world.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class EdgeMergeLayer extends GenLayer {
    private final int target;
    private final int replacement;

    private final GenLayer edgeParent;

    public EdgeMergeLayer(long seed, GenLayer parent, GenLayer edgeParent, int target, int replacement) {
        super(seed);
        this.parent = parent;
        this.edgeParent = edgeParent;
        this.target = target;
        this.replacement = replacement;
    }

    @Override
    public int[] getInts(int originX, int originY, int width, int height) {
        int[] result = IntCache.getIntCache(width * height);
        int[] parent = this.parent.getInts(originX, originY, width, height);
        int[] ridge = this.edgeParent.getInts(originX, originY, width, height);

        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                int index = localX + localY * width;
                int parentValue = parent[index];
                if (ridge[index] == 1 && parentValue == this.target) {
                    result[index] = this.replacement;
                } else {
                    result[index] = parentValue;
                }
            }
        }

        return result;
    }
}
