package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.EntityRifter;
import net.minecraft.entity.EntityLivingBase;

public class EntityTaskRifterMelee extends EntityTaskRifterFollow {
    public EntityTaskRifterMelee(EntityRifter owner, double followSpeed) {
        super(owner, followSpeed);
    }

    @Override
    protected boolean shouldFollow(EntityLivingBase target) {
        return super.shouldFollow(target) && this.owner.getPassengers().isEmpty() && !target.isRiding();
    }

    @Override
    protected void handleInteract(EntityLivingBase target) {
        this.owner.attackEntityAsMob(target);
    }
}
