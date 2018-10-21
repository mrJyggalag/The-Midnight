package com.mushroom.midnight.common.effect;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

public class StunnedEffect extends Potion {
    private static final String STRENGTH_ID = "FBD308EC-3EC7-4AFF-8999-206070A8BBA5";

    public StunnedEffect() {
        super(true, 0);
    }

    public StunnedEffect registerModifiers() {
        this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, STRENGTH_ID, -4.0, 0);
        return this;
    }
}
