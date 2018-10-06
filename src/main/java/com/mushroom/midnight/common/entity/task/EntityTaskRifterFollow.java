package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.EntityRifter;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public abstract class EntityTaskRifterFollow extends EntityAIBase {
    protected final EntityRifter owner;
    protected final double followSpeed;

    protected BlockPos lastSeenPos;
    protected Path path;

    protected int invalidCounter;

    protected boolean navigatingFromMemory;

    public EntityTaskRifterFollow(EntityRifter owner, double followSpeed) {
        this.owner = owner;
        this.followSpeed = followSpeed;

        this.setMutexBits(3);
    }

    protected abstract void handleInteract(EntityLivingBase target);

    @Override
    public boolean shouldExecute() {
        EntityLivingBase target = this.owner.getAttackTarget();
        if (this.shouldFollow(target)) {
            this.path = this.owner.getNavigator().getPathToEntityLiving(target);
            return this.path != null;
        }

        return false;
    }

    @Override
    public void startExecuting() {
        this.owner.getNavigator().setPath(this.path, this.followSpeed);
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = this.owner.getAttackTarget();
        if (target == null) {
            return;
        }

        if (!this.navigatingFromMemory) {
            this.owner.getLookHelper().setLookPositionWithEntity(target, 20.0F, 20.0F);
        }

        Path targetPath = this.computeFollowPath(target);
        if (targetPath == null) {
            this.owner.setAttackTarget(null);
            return;
        }

        Path currentPath = this.owner.getNavigator().getPath();
        if (currentPath != targetPath) {
            this.owner.getNavigator().setPath(targetPath, this.followSpeed);
        }

        if (this.canInteract(target)) {
            this.handleInteract(target);
        }
    }

    @Nullable
    protected Path computeFollowPath(EntityLivingBase target) {
        if (this.path.isFinished()) {
            this.path = null;
        }

        double distanceSq = this.owner.getDistanceSq(target);
        double followRange = this.owner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        if (distanceSq < followRange * followRange) {
            this.updateFollowVisible(target);
        } else if (!this.navigatingFromMemory && this.lastSeenPos != null) {
            this.path = this.owner.getNavigator().getPathToPos(this.lastSeenPos);
            this.navigatingFromMemory = true;
        }

        return this.path;
    }

    protected void updateFollowVisible(EntityLivingBase target) {
        if (this.shouldInvalidatePath(target) || this.navigatingFromMemory) {
            this.invalidCounter = 0;

            this.path = this.owner.getNavigator().getPathToEntityLiving(target);
            if (this.path != null) {
                this.lastSeenPos = target.getPosition();
                this.navigatingFromMemory = false;
            } else {
                this.navigatingFromMemory = true;
            }
        }
    }

    protected boolean shouldInvalidatePath(EntityLivingBase target) {
        int invalidateTime = this.getPathInvalidateTime(target);
        return ++this.invalidCounter > invalidateTime;
    }

    protected int getPathInvalidateTime(EntityLivingBase target) {
        double distanceSq = this.owner.getDistanceSq(target);
        if (distanceSq < 4.0 * 4.0) {
            return 5;
        } else if (distanceSq < 8.0 * 8.0) {
            return 10;
        } else {
            return 20;
        }
    }

    protected boolean canInteract(EntityLivingBase target) {
        double distanceSq = this.owner.getDistanceSq(target);
        double interactReach = this.getInteractReach(target);
        return distanceSq < interactReach * interactReach;
    }

    protected double getInteractReach(EntityLivingBase target) {
        double averageWidth = (this.owner.width + target.width) / 2.0;
        return averageWidth + 0.5;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldFollow(this.owner.getAttackTarget());
    }

    @Override
    public void resetTask() {
        this.owner.getNavigator().clearPath();
        this.path = null;
        this.lastSeenPos = null;
        this.navigatingFromMemory = false;

        if (!this.shouldFollow(this.owner.getAttackTarget())) {
            this.owner.setAttackTarget(null);
        }
    }

    protected boolean shouldFollow(EntityLivingBase target) {
        if (target == null) {
            return false;
        }
        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            if (player.isSpectator() || player.isCreative()) {
                return false;
            }
        }
        return target.isEntityAlive();
    }
}
