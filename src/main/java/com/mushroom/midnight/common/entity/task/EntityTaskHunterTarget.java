package com.mushroom.midnight.common.entity.task;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class EntityTaskHunterTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
    public EntityTaskHunterTarget(EntityCreature creature, Class<T> classTarget) {
        super(creature, classTarget, false);
    }

    @Override
    protected boolean isSuitableTarget(@Nullable EntityLivingBase target, boolean includeInvincibles) {
        return super.isSuitableTarget(target, includeInvincibles) && target != null && this.canSee(target);
    }

    private boolean canSee(EntityLivingBase target) {
        BlockPos position = target.getPosition().up();
        return target.world.canSeeSky(position);
    }

    @Override
    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        BlockPos surface = this.taskOwner.world.getHeight(this.taskOwner.getPosition());
        return new AxisAlignedBB(surface).grow(targetDistance, 6.0, targetDistance);
    }
}
