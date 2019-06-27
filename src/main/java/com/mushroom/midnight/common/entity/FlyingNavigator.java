package com.mushroom.midnight.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlyingNavigator extends FlyingPathNavigator {
    public FlyingNavigator(MobEntity entity, World world) {
        super(entity, world);
    }

    @Override
    protected void pathFollow() {
        if (this.currentPath == null) {
            return;
        }

        this.maxDistanceToWaypoint = this.entity.getWidth() > 0.75F ? this.entity.getWidth() / 2.0F : 0.75F - this.entity.getWidth() / 2.0F;
        Vec3d pathPosition = this.currentPath.getCurrentPos();

        boolean reachedWaypoint = Math.abs(this.entity.posX - (pathPosition.x + 0.5)) < this.maxDistanceToWaypoint
                && Math.abs(this.entity.posZ - (pathPosition.z + 0.5)) < this.maxDistanceToWaypoint
                && Math.abs(this.entity.posY - pathPosition.y) < 1.0;
        if (reachedWaypoint) {
            this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
        }

        this.simplifyPath(this.currentPath);

        this.checkForStuck(this.getEntityPosition());
    }

    private void simplifyPath(Path path) {
        Vec3d entityPosition = this.getEntityPosition();
        int width = MathHelper.ceil(this.entity.getWidth());
        int height = MathHelper.ceil(this.entity.getHealth());

        for (int i = path.getCurrentPathLength() - 1; i >= path.getCurrentPathIndex(); i--) {
            if (this.isDirectPathBetweenPoints(entityPosition, path.getVectorFromIndex(this.entity, i), width, height, width)) {
                path.setCurrentPathIndex(i);
                break;
            }
        }
    }

    @Override
    protected boolean isDirectPathBetweenPoints(Vec3d origin, Vec3d target, int sizeX, int sizeY, int sizeZ) {
        int currentX = MathHelper.floor(origin.x);
        int currentY = MathHelper.floor(origin.y);
        int currentZ = MathHelper.floor(origin.z);

        double deltaX = target.x - origin.x;
        double deltaY = target.y - origin.y;
        double deltaZ = target.z - origin.z;
        double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

        if (distanceSquared < 1e-8) {
            return false;
        }

        double step = 1.0 / Math.sqrt(distanceSquared);
        deltaX = deltaX * step;
        deltaY = deltaY * step;
        deltaZ = deltaZ * step;

        double stepX = 1.0 / Math.abs(deltaX);
        double stepY = 1.0 / Math.abs(deltaY);
        double stepZ = 1.0 / Math.abs(deltaZ);

        double displacedX = currentX - origin.x;
        double displacedY = currentY - origin.y;
        double displacedZ = currentZ - origin.z;

        if (deltaX >= 0.0) {
            displacedX++;
        }

        if (deltaY >= 0.0) {
            displacedY++;
        }

        if (deltaZ >= 0.0) {
            displacedZ++;
        }

        displacedX = displacedX / deltaX;
        displacedY = displacedY / deltaY;
        displacedZ = displacedZ / deltaZ;

        int signX = deltaX < 0.0 ? -1 : 1;
        int signY = deltaY < 0.0 ? -1 : 1;
        int signZ = deltaZ < 0.0 ? -1 : 1;

        int targetX = MathHelper.floor(target.x);
        int targetY = MathHelper.floor(target.y);
        int targetZ = MathHelper.floor(target.z);

        int currentDeltaX = targetX - currentX;
        int currentDeltaY = targetY - currentY;
        int currentDeltaZ = targetZ - currentZ;

        while (currentDeltaX * signX > 0 || currentDeltaY * signY > 0 || currentDeltaZ * signZ > 0) {
            if (displacedX < displacedZ && displacedX <= displacedY) {
                displacedX += stepX;
                currentX += signX;
                currentDeltaX = targetX - currentX;
            } else if (displacedY < displacedX && displacedY <= displacedZ) {
                displacedY += stepY;
                currentY += signY;
                currentDeltaY = targetY - currentY;
            } else {
                displacedZ += stepZ;
                currentZ += signZ;
                currentDeltaZ = targetZ - currentZ;
            }

            if (!this.canPassThrough(currentX, currentY, currentZ, sizeX, sizeY, sizeZ, origin, deltaX, deltaZ)) {
                return false;
            }
        }

        return true;
    }

    private boolean canPassThrough(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3d origin, double deltaX, double deltaZ) {
        BlockPos min = new BlockPos(x, y, z);
        BlockPos max = new BlockPos(x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1);
        for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max)) {
            double displacementX = pos.getX() + 0.5 - origin.x;
            double displacementZ = pos.getZ() + 0.5 - origin.z;

            if (displacementX * deltaX + displacementZ * deltaZ >= 0.0) {
                Block block = this.world.getBlockState(pos).getBlock();
                if (!block.isPassable(this.world, pos)) {
                    return false;
                }
            }
        }

        return true;
    }
}
