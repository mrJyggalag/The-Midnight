package com.mushroom.midnight.common.world.feature.placement;

import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import com.mushroom.midnight.common.world.PlacementLevel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;

import java.util.Random;

public class SurfacePlacementLevel implements PlacementLevel {
    public static final PlacementLevel INSTANCE = new SurfacePlacementLevel();

    private SurfacePlacementLevel() {
    }

    @Override
    public BlockPos getSurfacePos(IWorld world, Heightmap.Type type, BlockPos pos) {
        return world.getHeight(type, pos);
    }

    @Override
    public int generateUpTo(IWorld world, Random random, int y) {
        int bound = Math.max(y - MidnightChunkGenerator.MIN_SURFACE_LEVEL, 1);
        return random.nextInt(bound) + MidnightChunkGenerator.MIN_SURFACE_LEVEL;
    }
}
