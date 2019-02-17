package com.mushroom.midnight.common.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface SurfacePlacementLevel {
    BlockPos getSurfacePos(World world, BlockPos pos);

    int generateUpTo(World world, Random random, int y);
}
