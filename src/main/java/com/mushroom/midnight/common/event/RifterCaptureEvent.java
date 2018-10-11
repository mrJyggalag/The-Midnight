package com.mushroom.midnight.common.event;

import com.mushroom.midnight.common.entity.creature.EntityRifter;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RifterCaptureEvent extends Event {
    private final EntityRifter rifter;
    private final EntityLivingBase captured;

    public RifterCaptureEvent(EntityRifter rifter, EntityLivingBase captured) {
        this.rifter = rifter;
        this.captured = captured;
    }

    public EntityRifter getRifter() {
        return this.rifter;
    }

    public EntityLivingBase getCaptured() {
        return this.captured;
    }
}
