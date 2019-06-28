package com.mushroom.midnight.common.util;

import com.mushroom.midnight.Midnight;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MidnightDamageSource extends DamageSource {
    public MidnightDamageSource(String damageType) {
        super(damageType);
    }
    @Override
    public ITextComponent getDeathMessage(LivingEntity entity) {
        return new TranslationTextComponent("death."+ Midnight.MODID + "." + damageType, entity.getDisplayName());
    }
}
