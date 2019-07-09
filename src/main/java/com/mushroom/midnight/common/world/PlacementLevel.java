package com.mushroom.midnight.common.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;

public interface PlacementLevel {
    BlockPos getSurfacePos(IWorld world, Heightmap.Type heightmap, BlockPos pos);

    boolean containsY(IWorld world, int y);
}
