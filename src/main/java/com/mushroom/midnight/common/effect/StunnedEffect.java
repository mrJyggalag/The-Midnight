package com.mushroom.midnight.common.effect;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

public class StunnedEffect extends Potion {
    private static final String SPEED_ID = "62B36A18-FC68-4385-A8DF-D70D3A438E16";
    private static final String STRENGTH_ID = "FBD308EC-3EC7-4AFF-8999-206070A8BBA5";

    public StunnedEffect() {
        super(true, 0);
    }

    public StunnedEffect registerModifiers() {
        this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, SPEED_ID, -0.1, 0);
        this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, STRENGTH_ID, -4.0, 0);
        return this;
    }
}
