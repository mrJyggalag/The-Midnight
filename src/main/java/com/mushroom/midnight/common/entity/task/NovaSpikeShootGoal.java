package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.creature.NovaEntity;
import com.mushroom.midnight.common.entity.projectile.NovaSpikeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class NovaSpikeShootGoal extends Goal {
    private final NovaEntity nova;
    private int seeTime;
    private final float maxAttackDistance;
    private int rangedAttackTime = -1;
    private LivingEntity targetEntity;

    public NovaSpikeShootGoal(NovaEntity novaEntity, float maxAttackDistanceIn) {
        this.nova = novaEntity;
        this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity target = this.nova.getAttackTarget();
        if (target != null && this.nova.canEntityBeSeen(target) && target.isAlive()) {
            this.targetEntity = target;
            return true;
        } else {
            return false;
        }
    }

    public boolean shouldContinueExecuting() {
        return this.shouldExecute() || !this.nova.getNavigator().noPath();
    }

    @Override
    public void resetTask() {
        this.targetEntity = null;
        this.seeTime = 0;
        this.rangedAttackTime = -1;
    }

    public void tick() {
        double d0 = this.nova.getDistanceSq(this.targetEntity.posX, this.targetEntity.getBoundingBox().minY, this.targetEntity.posZ);
        boolean flag = this.nova.getEntitySenses().canSee(this.targetEntity);
        if (flag) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (d0 < this.maxAttackDistance && d0 > 40) {
            if (--this.rangedAttackTime == 0) {
                if (!flag) {
                    return;
                }

                this.nova.getNavigator().clearPath();
                this.shootSpike();
                this.rangedAttackTime = 45;
            } else if (this.rangedAttackTime < 0) {
                this.rangedAttackTime = 60;
            }
        }
    }

    private void shootSpike() {
        NovaSpikeEntity spike = new NovaSpikeEntity(this.nova.world, this.nova);
        spike.setPosition(nova.posX, nova.posY + (double) nova.getEyeHeight(), nova.posZ);
        spike.shoot(nova, nova.rotationPitch, nova.rotationYaw, -8.0F, 1.05F, 1.0F);
        this.nova.world.addEntity(spike);
    }
}
