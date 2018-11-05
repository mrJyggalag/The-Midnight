package com.mushroom.midnight.common.entity;

import java.util.Collection;
import java.util.Collections;

public interface IRiftTraveler {
    void onEnterRift(EntityRift rift);

    RiftTravelEntry createTravelEntry(EntityRift rift);

    default Collection<RiftTravelEntry> getAdditionalTravelers(EntityRift rift) {
        return Collections.emptyList();
    }
}
