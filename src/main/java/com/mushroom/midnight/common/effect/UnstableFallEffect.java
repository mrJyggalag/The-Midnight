package com.mushroom.midnight.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.Vec3d;

public class UnstableFallEffect extends GenericEffect {
    public UnstableFallEffect() {
        super(EffectType.BENEFICIAL, 0x0);
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        if (!entity.isSneaking()) {
            Vec3d motion = entity.getMotion();
            if (motion.y < -0.079d) {
                entity.setMotion(motion.subtract(0.0, motion.y * 0.3, 0.0));
                entity.fallDistance = 0f;
            }

            Vec3d lookVec = entity.getLookVec();
            entity.setMotion(new Vec3d(lookVec.x * 0.3, entity.getMotion().y, lookVec.z * 0.3));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
