package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.EntityRifter;
import net.minecraft.entity.EntityLivingBase;

public class EntityTaskRifterCapture extends EntityTaskRifterFollow {
    public EntityTaskRifterCapture(EntityRifter owner, double followSpeed) {
        super(owner, followSpeed);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.owner.shouldCapture()) {
            return false;
        }
        return super.shouldExecute();
    }

    @Override
    protected boolean shouldFollow(EntityLivingBase target) {
        return super.shouldFollow(target) && this.owner.getPassengers().isEmpty() && !target.isRiding();
    }

    @Override
    protected void handleInteract(EntityLivingBase target) {
        this.owner.attackEntityAsMob(target);
        if (target.isEntityAlive()) {
            target.startRiding(this.owner);
        }
    }
}
