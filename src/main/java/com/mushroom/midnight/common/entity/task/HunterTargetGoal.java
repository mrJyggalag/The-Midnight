package com.mushroom.midnight.common.entity.task;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class HunterTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public HunterTargetGoal(CreatureEntity creature, Class<T> classTarget) {
        super(creature, classTarget, false);
    }

    @Override
    protected boolean func_220777_a(@Nullable LivingEntity target, EntityPredicate predicate) {
        return super.func_220777_a(target, predicate) && target != null && this.canSee(target);
    }

    private boolean canSee(LivingEntity target) {
        BlockPos position = target.getPosition().up();
        return target.world.canBlockSeeSky(position);
    }

    @Override
    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        // TODO @gegy check this
        BlockPos surface = this.field_75299_d.world.getHeight(this.field_75299_d.getPosition());
        return new AxisAlignedBB(surface).grow(targetDistance, 6.0, targetDistance);
    }
}
