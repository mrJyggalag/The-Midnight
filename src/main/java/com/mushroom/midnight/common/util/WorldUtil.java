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
        mutablePos.move(EnumFacing.UP);
        return mutablePos.toImmutable();
    }

    @Nonnull
    public static BlockPos findSurfaceOrInput(World world, BlockPos pos, int maxSteps) {
        BlockPos surface = findSurface(world, pos, maxSteps);
        return surface != null ? surface : pos;
    }

    public static BlockPos min(BlockPos a, BlockPos b) {
        return new BlockPos(Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()));
    }

    public static BlockPos max(BlockPos a, BlockPos b) {
        return new BlockPos(Math.max(a.getX(), b.getX()), Math.max(a.getY(), b.getY()), Math.max(a.getZ(), b.getZ()));
    }
}
