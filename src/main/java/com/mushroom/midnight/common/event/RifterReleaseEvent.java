package com.mushroom.midnight.common.event;

import com.mushroom.midnight.common.entity.creature.RifterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class RifterReleaseEvent extends Event {
    private final RifterEntity rifter;
    private final LivingEntity captured;

    public RifterReleaseEvent(RifterEntity rifter, LivingEntity captured) {
        this.rifter = rifter;
        this.captured = captured;
    }

    public RifterEntity getRifter() {
        return this.rifter;
    }

    public LivingEntity getCaptured() {
        return this.captured;
    }
}
