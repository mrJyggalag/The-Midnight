package com.mushroom.midnight.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;

public class DragonGuardEffect extends GenericEffect {
    public DragonGuardEffect() {
        super(EffectType.BENEFICIAL, 0);
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        entity.extinguish();
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
