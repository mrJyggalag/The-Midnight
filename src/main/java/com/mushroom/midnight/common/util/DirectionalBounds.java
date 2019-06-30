package com.mushroom.midnight.common.util;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;

public class DirectionalBounds {
    private final double minX;
    private final double minY;
    private final double minZ;
    private final double maxX;
    private final double maxY;
    private final double maxZ;

    private final VoxelShape[] cache = new VoxelShape[Direction.values().length];

    public DirectionalBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public VoxelShape get(Direction facing) {
        VoxelShape cached = this.cache[facing.getIndex()];
        if (cached == null) {
            VoxelShape computed = this.compute(facing);
            this.cache[facing.getIndex()] = computed;
            return computed;
        }
        return cached;
    }

    private VoxelShape compute(Direction facing) {
        switch (facing) {
            case NORTH:
            default:
                return Block.makeCuboidShape(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
            case WEST:
                return Block.makeCuboidShape(this.minZ, this.minY, 1.0 - this.maxX, this.maxZ, this.maxY, 1.0 - this.minX);
            case SOUTH:
                return Block.makeCuboidShape(1.0 - this.maxX, this.minY, 1.0 - this.maxZ, 1.0 - this.minX, this.maxY, 1.0 - this.minZ);
            case EAST:
                return Block.makeCuboidShape(1.0 - this.maxZ, this.minY, this.minX, 1.0 - this.minZ, this.maxY, this.maxX);
            case DOWN:
                return Block.makeCuboidShape(1.0 - this.maxX, this.minZ, 1.0 - this.maxY, 1.0 - this.minX, this.maxZ, 1.0 - this.minY);
            case UP:
                return Block.makeCuboidShape(this.minX, 1.0 - this.maxZ, this.minY, this.maxX, 1.0 - this.minZ, this.maxY);
        }
    }
}
