package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.creature.EntityHunter;
import com.mushroom.midnight.common.entity.util.FlyingTargetGenerator;
import com.mushroom.midnight.common.entity.util.IRandomTargetGenerator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

public class EntityTaskHunterSwoop extends EntityAIBase {
    private static final int SWOOP_COOLDOWN = 160;

    private static final int MAX_SWOOP_TICKS = 120;

    private final EntityHunter owner;
    private final double speed;

    private final IRandomTargetGenerator riseTargetGenerator;

    private int ticks;
    private boolean rising;

    public EntityTaskHunterSwoop(EntityHunter owner, double speed) {
        this.owner = owner;
        this.speed = speed;

        this.riseTargetGenerator = new FlyingTargetGenerator(owner, 6, 10, 12, 14);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase target = this.owner.getAttackTarget();
        return target != null && target.isEntityAlive() && this.owner.swoopCooldown <= 0;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (!this.shouldExecute()) {
            return false;
        }
        Path path = this.owner.getNavigator().getPath();
        return path != null && (!this.rising || !path.isFinished()) && this.ticks < MAX_SWOOP_TICKS;
    }

    @Override
    public void startExecuting() {
        EntityLivingBase target = this.owner.getAttackTarget();
        if (target == null) {
            return;
        }

        this.recalculatePath(target);
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = this.owner.getAttackTarget();
        if (target == null) {
            return;
        }

        if (this.ticks++ % 10 == 0) {
            this.recalculatePath(target);
        }

        if (this.canAttack(target)) {
            this.owner.attackEntityAsMob(target);

            float theta = (float) Math.toRadians(this.owner.rotationYaw);
            target.knockBack(this.owner, 0.3F, MathHelper.sin(theta), -MathHelper.cos(theta));

            this.rising = true;
            this.recalculatePath(target);
        }
    }

    private void recalculatePath(EntityLivingBase target) {
        this.owner.getNavigator().clearPath();

        Path path = this.produceTargetPath(target);
        if (path == null) {
            this.owner.setAttackTarget(null);
        }

        this.owner.getNavigator().setPath(path, this.speed);
    }

    @Nullable
    private Path produceTargetPath(EntityLivingBase target) {
        if (this.rising) {
            for (int i = 0; i < 16; i++) {
                Path path = this.produceRisePath(target);
                if (path != null) {
                    return path;
                }
            }
            return null;
        } else {
            return this.owner.getNavigator().getPathToEntityLiving(target);
        }
    }

    @Nullable
    private Path produceRisePath(EntityLivingBase target) {
        BlockPos flightPos = this.riseTargetGenerator.generate(target.getPosition());
        if (flightPos == null) {
            return null;
        }
        return this.owner.getNavigator().getPathToPos(flightPos);
    }

    @Override
    public void resetTask() {
        this.owner.getNavigator().clearPath();

        this.owner.swoopCooldown = SWOOP_COOLDOWN;

        this.rising = false;
        this.ticks = 0;
    }

    private boolean canAttack(EntityLivingBase target) {
        double distanceSq = this.owner.getDistanceSq(target);
        double attackReach = this.getAttackReach(target);
        return distanceSq < attackReach * attackReach;
    }

    private double getAttackReach(EntityLivingBase target) {
        double meanWidth = (this.owner.width + target.width) / 2.0;
        return meanWidth + 0.5;
    }
}
 
