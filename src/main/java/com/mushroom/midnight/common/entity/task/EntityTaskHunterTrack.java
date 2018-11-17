package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.creature.EntityHunter;
import com.mushroom.midnight.common.entity.util.FlyingTargetGenerator;
import com.mushroom.midnight.common.entity.util.IRandomTargetGenerator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class EntityTaskHunterTrack extends EntityAIBase {
    private final EntityHunter owner;
    private final double speed;

    private final IRandomTargetGenerator trackTargetGenerator;

    public EntityTaskHunterTrack(EntityHunter owner, double speed) {
        this.owner = owner;
        this.speed = speed;
        this.trackTargetGenerator = new FlyingTargetGenerator(owner, 6, 10, 14, 16);
    }

    @Override
    public boolean shouldExecute() {
        return this.owner.getNavigator().noPath() && this.owner.getAttackTarget() != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }

    @Override
    public void startExecuting() {
        EntityLivingBase target = this.owner.getAttackTarget();
        if (target == null) {
            return;
        }

        Path path = null;

        int retries = 0;
        while (retries++ < 16 && path == null) {
            path = this.produceTrackPath(target);
        }

        if (path != null) {
            this.owner.getNavigator().setPath(path, this.speed);
        }
    }

    @Nullable
    private Path produceTrackPath(EntityLivingBase target) {
        BlockPos flightPos = this.trackTargetGenerator.generate(target.getPosition());
        if (flightPos == null) {
            return null;
        }

        return this.owner.getNavigator().getPathToPos(flightPos);
    }
}
 
