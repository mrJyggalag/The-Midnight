package com.mushroom.midnight.common.effect;

import net.minecraft.entity.SharedMonsterAttributes;

public class DarknessEffect extends GenericEffect {
    private static final String FOLLOW_MALUS = "3b94515a-5df6-4dc6-9c61-a37b054bb6ba";
    public DarknessEffect() {
        super(true, 0x0);
        registerPotionAttributeModifier(SharedMonsterAttributes.FOLLOW_RANGE, FOLLOW_MALUS, -5, 1);
    }
}
