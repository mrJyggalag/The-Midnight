package com.mushroom.midnight.common.entity;

import net.minecraft.entity.Entity;

import java.util.Collection;
import java.util.Collections;

public interface IRiftTraveler {
    void onEnterRift(EntityRift rift);

    default Collection<Entity> getAdditionalTeleportEntities() {
        return Collections.emptyList();
    }
}
