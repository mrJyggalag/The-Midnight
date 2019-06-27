package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.capability.RifterCapturable;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;

public class RifterCaptureGoalGoal extends RifterFollowGoal {
    public RifterCaptureGoalGoal(RifterEntity owner, double followSpeed) {
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
    protected boolean shouldFollow(LivingEntity target) {
        return super.shouldFollow(target) && !this.owner.hasCaptured() && !RifterCapturable.isCaptured(target);
    }

    @Override
    protected boolean canInteract(LivingEntity target) {
        return super.canInteract(target) && this.owner.captureCooldown <= 0;
    }

    @Override
    protected void handleInteract(LivingEntity target) {
        this.owner.captureCooldown = RifterEntity.CAPTURE_COOLDOWN;
        if (this.owner.attackEntityAsMob(target) && target.isAlive() && (MidnightConfig.general.rifterCaptureTamedAnimal.get() || !(target instanceof TameableEntity) || !((TameableEntity) target).isTamed())) {
            this.owner.setCapturedEntity(target);
        }
    }
}
