package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.creature.EntityHunter;
import com.mushroom.midnight.common.entity.util.FlyingTargetGenerator;
import com.mushroom.midnight.common.entity.util.IRandomTargetGenerator;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class EntityTaskHunterIdle extends EntityAIBase {
    private final EntityHunter owner;
    private final double speed;

    private final IRandomTargetGenerator targetGenerator;

    public EntityTaskHunterIdle(EntityHunter owner, double speed) {
        this.owner = owner;
        this.speed = speed;

        this.targetGenerator = new FlyingTargetGenerator(owner, 20, 40, EntityHunter.FLIGHT_HEIGHT - 2, EntityHunter.FLIGHT_HEIGHT + 2);
    }

    @Override
    public boolean shouldExecute() {
        return this.owner.getNavigator().noPath();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }

    @Override
    public void startExecuting() {
        Path path = null;

        int retries = 0;
        while (retries++ < 16 && path == null) {
            path = this.produceWanderPath();
        }

        if (path != null) {
            this.owner.getNavigator().setPath(path, this.speed);
        }
    }

    @Nullable
    private Path produceWanderPath() {
        BlockPos homePos = this.owner.hasHome() ? this.owner.getHomePosition() : null;
        BlockPos flightPos = this.targetGenerator.generate(homePos);
        if (flightPos == null) {
            return null;
        }

        return this.owner.getNavigator().getPathToPos(flightPos);
    }
}
 
