package com.mushroom.midnight.common.world.feature.config;

import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Consumer;

public class SurfacePlacementConfig implements IPlacementConfig {
    private final int minCount;
    private final int maxCount;

    public SurfacePlacementConfig(int minCount, int maxCount) {
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public SurfacePlacementConfig(int count) {
        this(count, count);
    }

    @Override
    public void apply(World world, SurfacePlacementLevel placementLevel, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator) {
        int count = random.nextInt(this.maxCount - this.minCount + 1) + this.minCount;

        for (int i = 0; i < count; i++) {
            int offsetX = random.nextInt(16) + 8;
            int offsetZ = random.nextInt(16) + 8;
            generator.accept(placementLevel.getSurfacePos(chunkOrigin.add(offsetX, 0, offsetZ)));
        }
    }
}
