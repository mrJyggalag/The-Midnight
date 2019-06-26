package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.creature.RifterEntity;

public class RifterTransportGoal extends RifterReturnGoal {
    public RifterTransportGoal(RifterEntity owner, double returnSpeed) {
        super(owner, returnSpeed);
    }

    @Override
    public boolean shouldReturn() {
        return this.owner.hasCaptured() && this.owner.shouldCapture();
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.owner.setCapturedEntity(null);
    }
}
