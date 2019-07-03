package com.mushroom.midnight.common.effect;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

public class GenericEffect extends Effect {

    public GenericEffect(EffectType type, int color) {
        super(type, color);
    }

    @Override
    public final String getName() {
        return "potion." + super.getName() + ".name";
    }
}
