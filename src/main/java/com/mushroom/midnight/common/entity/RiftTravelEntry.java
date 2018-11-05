package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.common.world.MidnightTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.world.DimensionType;

public class RiftTravelEntry {
    protected final Entity entity;

    public RiftTravelEntry(Entity entity) {
        this.entity = entity;
    }

    public void travel(EntityRift rift, DimensionType endpointDimension) {
        this.entity.changeDimension(endpointDimension.getId(), new MidnightTeleporter(rift));
    }

    public Entity getEntity() {
        return this.entity;
    }
}
