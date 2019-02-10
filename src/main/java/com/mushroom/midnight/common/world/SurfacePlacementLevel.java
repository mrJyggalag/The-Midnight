package com.mushroom.midnight.common.world;

import net.minecraft.util.math.BlockPos;

import java.util.Random;

public interface SurfacePlacementLevel {
    BlockPos getSurfacePos(BlockPos pos);

    int generateUpTo(Random random, int y);
}
