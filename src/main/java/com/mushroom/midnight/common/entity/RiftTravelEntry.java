package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.Midnight;
import net.minecraft.entity.Entity;

public class RiftTravelEntry {
    protected final Entity entity;

    public RiftTravelEntry(Entity entity) {
        this.entity = entity;
    }

    public void travel(RiftEntity rift) {
        this.entity.getCapability(Midnight.RIFT_TRAVELLER_CAP).ifPresent(traveller -> traveller.enterRift(rift));
    }

    public Entity getEntity() {
        return this.entity;
    }
}
