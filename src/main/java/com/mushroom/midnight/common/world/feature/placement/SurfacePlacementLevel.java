package com.mushroom.midnight.common.world.feature.placement;

import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import com.mushroom.midnight.common.world.PlacementLevel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;

public class SurfacePlacementLevel implements PlacementLevel {
    public static final PlacementLevel INSTANCE = new SurfacePlacementLevel();

    private SurfacePlacementLevel() {
    }

    @Override
    public BlockPos getSurfacePos(IWorld world, Heightmap.Type type, BlockPos pos) {
        return world.getHeight(type, pos);
    }

    @Override
    public boolean containsY(IWorld world, int y) {
        return y > MidnightChunkGenerator.SURFACE_CAVE_BOUNDARY;
    }
}
