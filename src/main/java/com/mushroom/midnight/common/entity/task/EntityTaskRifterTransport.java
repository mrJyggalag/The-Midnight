package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.entity.EntityRifter;
import com.mushroom.midnight.common.util.WorldUtil;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Optional;

public class EntityTaskRifterTransport extends EntityAIBase {
    private static final int INVALIDATE_TIME = 10;

    private final EntityRifter owner;
    private final double transportSpeed;

    private int invalidCount;
    private Path path;

    public EntityTaskRifterTransport(EntityRifter owner, double transportSpeed) {
        this.owner = owner;
        this.transportSpeed = transportSpeed;

        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        return this.owner.hasCaptured() && this.owner.shouldCapture();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.owner.hasCaptured() && this.owner.shouldCapture();
    }

    @Override
    public void startExecuting() {
        this.path = this.computeFollowPath();
        this.owner.getNavigator().setPath(this.path, this.transportSpeed);
    }

    @Override
    public void updateTask() {
        Path targetPath = this.computeFollowPath();
        if (targetPath == null) {
            // TODO: Do something so we don't just pick them up again
            this.owner.setCapturedEntity(null);
            return;
        }

        Path currentPath = this.owner.getNavigator().getPath();
        if (currentPath != targetPath) {
            this.owner.getNavigator().setPath(targetPath, this.transportSpeed);
        }
    }

    @Nullable
    private Path computeFollowPath() {
        if (++this.invalidCount > INVALIDATE_TIME || this.isPathComplete(this.path)) {
            this.path = null;
            this.invalidCount = 0;
        }

        if (this.path == null) {
            Optional<EntityRift> derefRift = this.owner.getHomeRift().deref(true);
            if (!derefRift.isPresent()) {
                return null;
            }

            EntityRift homeRift = derefRift.get();
            BlockPos surface = WorldUtil.findSurfaceOrInput(homeRift.world, homeRift.getPosition(), 16).up();
            this.path = this.owner.getNavigator().getPathToPos(surface);

            if (this.path == null) {
                this.path = this.computePathTowards(surface);
            }
        }

        return this.path;
    }

    @Nullable
    private Path computePathTowards(BlockPos surface) {
        Vec3d target = new Vec3d(surface);
        for (int i = 0; i < 16; i++) {
            Vec3d pathPos = RandomPositionGenerator.findRandomTargetBlockTowards(this.owner, 24, 4, target);
            if (pathPos == null) {
                continue;
            }
            Path path = this.owner.getNavigator().getPathToXYZ(pathPos.x, pathPos.y, pathPos.z);
            if (path != null) {
                return path;
            }
        }
        return null;
    }

    private boolean isPathComplete(Path path) {
        if (path == null || path.isFinished()) {
            return true;
        }
        PathPoint finalPoint = path.getFinalPathPoint();
        return finalPoint == null || this.owner.getDistanceSq(finalPoint.x, finalPoint.y, finalPoint.z) < 1.5 * 1.5;
    }

    @Override
    public void resetTask() {
        this.owner.getNavigator().clearPath();
        this.owner.setCapturedEntity(null);

        this.path = null;
        this.invalidCount = 0;
    }
}
