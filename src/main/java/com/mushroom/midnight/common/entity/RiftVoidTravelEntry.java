package com.mushroom.midnight.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.DimensionType;

public class RiftVoidTravelEntry extends RiftTravelEntry {
    public RiftVoidTravelEntry(Entity entity) {
        super(entity);
    }

    @Override
    public void travel(EntityRift rift, DimensionType endpointDimension) {
        this.entity.setDead();
    }
}
