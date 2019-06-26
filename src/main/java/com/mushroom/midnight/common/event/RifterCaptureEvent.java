package com.mushroom.midnight.common.event;

import com.mushroom.midnight.common.entity.creature.RifterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class RifterCaptureEvent extends Event {
    private final RifterEntity rifter;
    private final LivingEntity captured;

    public RifterCaptureEvent(RifterEntity rifter, LivingEntity captured) {
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
