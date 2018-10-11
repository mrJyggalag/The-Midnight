package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.capability.RifterCapturedCapability;
import com.mushroom.midnight.common.entity.creature.EntityRifter;
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
        return super.shouldFollow(target) && !this.owner.hasCaptured() && !RifterCapturedCapability.isCaptured(target);
    }

    @Override
    protected boolean canInteract(EntityLivingBase target) {
        return super.canInteract(target) && this.owner.captureCooldown <= 0;
    }

    @Override
    protected void handleInteract(EntityLivingBase target) {
        this.owner.captureCooldown = EntityRifter.CAPTURE_COOLDOWN;
        if (this.owner.attackEntityAsMob(target) && target.isEntityAlive()) {
            this.owner.setCapturedEntity(target);
        }
    }
}
