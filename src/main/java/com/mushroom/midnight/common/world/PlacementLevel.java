package com.mushroom.midnight.common.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;

import java.util.Random;

public interface PlacementLevel {
    BlockPos getSurfacePos(IWorld world, Heightmap.Type heightmap, BlockPos pos);

    int generateUpTo(IWorld world, Random random, int y);
}
