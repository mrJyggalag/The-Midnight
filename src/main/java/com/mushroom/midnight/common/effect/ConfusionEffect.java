package com.mushroom.midnight.common.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ConfusionEffect extends GenericEffect {
    public ConfusionEffect() {
        super(EffectType.HARMFUL, 0x0);
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof MobEntity) {
            MobEntity mob = (MobEntity) entity;
            if (mob.getRNG().nextInt(5) == 0) {
                List<Entity> list = mob.world.getEntitiesInAABBexcluding(mob, mob.getBoundingBox().grow(2d), p -> p instanceof MobEntity && p.isAlive());
                if (list.isEmpty()) {
                    mob.setAttackTarget(null);
                    mob.setMotion(Vec3d.ZERO);
                } else {
                    mob.setAttackTarget((LivingEntity) list.get(mob.world.rand.nextInt(list.size())));
                }
            } else if (mob.getAttackTarget() == null) {
                mob.setMotion(Vec3d.ZERO);
            }
        } else if (entity instanceof PlayerEntity && entity.getRNG().nextBoolean()) {
            entity.setPositionAndRotation(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ, entity.getRNG().nextFloat() * 360f, entity.rotationPitch);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
