package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.capability.RifterCapturable;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import net.minecraft.entity.LivingEntity;

public class RifterMeleeGoal extends RifterFollowGoal {
    public RifterMeleeGoal(RifterEntity owner, double followSpeed) {
        super(owner, followSpeed);
    }

    @Override
    protected boolean shouldFollow(LivingEntity target) {
        return super.shouldFollow(target) && !this.owner.hasCaptured() && !RifterCapturable.isCaptured(target);
    }

    @Override
    protected void handleInteract(LivingEntity target) {
        this.owner.attackEntityAsMob(target);
    }
}
