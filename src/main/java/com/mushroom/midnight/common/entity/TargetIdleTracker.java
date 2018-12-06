package com.mushroom.midnight.common.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class TargetIdleTracker {
    private final EntityLiving owner;
    private final double idleBreakDistanceSq;

    private EntityLivingBase lastTarget;

    private int idleTime;
    private Vec3d lastTargetPos;

    public TargetIdleTracker(EntityLiving owner, double idleBreakDistance) {
        this.owner = owner;
        this.idleBreakDistanceSq = idleBreakDistance * idleBreakDistance;
    }

    public void update() {
        EntityLivingBase target = this.owner.getAttackTarget();
        if (target != this.lastTarget) {
            this.reset();
        }

        if (target != null) {
            Vec3d position = target.getPositionVector();
            if (this.lastTargetPos == null || this.lastTargetPos.squareDistanceTo(position) > this.idleBreakDistanceSq) {
                this.idleTime = 0;
                this.lastTargetPos = position;
            } else {
                this.idleTime++;
            }
        }

        this.lastTarget = target;
    }

    public int getIdleTime() {
        return this.idleTime;
    }

    private void reset() {
        this.idleTime = 0;
        this.lastTargetPos = null;
    }
}
