package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.capability.RifterCapturable;
import com.mushroom.midnight.common.entity.creature.EntityRifter;
import net.minecraft.entity.EntityLivingBase;

public class EntityTaskRifterMelee extends EntityTaskRifterFollow {
    public EntityTaskRifterMelee(EntityRifter owner, double followSpeed) {
        super(owner, followSpeed);
    }

    @Override
    protected boolean shouldFollow(EntityLivingBase target) {
        return super.shouldFollow(target) && !this.owner.hasCaptured() && !RifterCapturable.isCaptured(target);
    }

    @Override
    protected void handleInteract(EntityLivingBase target) {
        this.owner.attackEntityAsMob(target);
    }
}
