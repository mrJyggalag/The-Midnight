package com.mushroom.midnight.common.util;

import com.mushroom.midnight.Midnight;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class MidnightDamageSource extends DamageSource {
    private final String deathMessage;
    public MidnightDamageSource(String deathMessage) {
        super("midnight_damage");
        this.deathMessage = deathMessage;
    }
    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entity) {
        return new TextComponentTranslation("death."+ Midnight.MODID + "." + deathMessage, entity.getDisplayName());
    }
}
