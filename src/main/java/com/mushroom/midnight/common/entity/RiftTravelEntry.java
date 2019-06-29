package com.mushroom.midnight.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.dimension.DimensionType;

public class RiftTravelEntry {
    protected final Entity entity;

    public RiftTravelEntry(Entity entity) {
        this.entity = entity;
    }

    public void travel(RiftEntity rift, DimensionType endpointDimension) {
        this.entity.changeDimension(endpointDimension);
    }

    public Entity getEntity() {
        return this.entity;
    }
}
