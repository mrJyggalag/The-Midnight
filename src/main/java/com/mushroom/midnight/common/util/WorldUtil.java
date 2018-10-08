package com.mushroom.midnight.common.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WorldUtil {
    @Nullable
    public static BlockPos findSurface(World world, BlockPos pos, int maxSteps) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);
        int steps = 0;
        while (!world.isSideSolid(mutablePos, EnumFacing.UP)) {
            mutablePos.move(EnumFacing.DOWN);
            if (steps++ > maxSteps) {
                return null;
            }
        }
        return mutablePos.toImmutable();
    }

    @Nonnull
    public static BlockPos findSurfaceOrInput(World world, BlockPos pos, int maxSteps) {
        BlockPos surface = findSurface(world, pos, maxSteps);
        return surface != null ? surface : pos;
    }
}
