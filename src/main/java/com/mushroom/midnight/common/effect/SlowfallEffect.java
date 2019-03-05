package com.mushroom.midnight.common.effect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class SlowfallEffect extends GenericEffect {
    public SlowfallEffect() {
        super(false, 0x0);
        setBeneficial();
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (!entity.isSneaking()) {
            if (entity.motionY < -0.079d) {
                entity.motionY -= entity.motionY * 0.3d;
                entity.fallDistance = 0f;
            }
            Vec3d lookVec = entity.getLookVec();
            entity.motionX = lookVec.x * 0.3d;
            entity.motionZ = lookVec.z * 0.3d;
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
