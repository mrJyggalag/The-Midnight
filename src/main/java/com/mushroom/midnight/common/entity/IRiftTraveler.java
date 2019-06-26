package com.mushroom.midnight.common.entity;

import java.util.Collection;
import java.util.Collections;

public interface IRiftTraveler {
    void onEnterRift(RiftEntity rift);

    RiftTravelEntry createTravelEntry(RiftEntity rift);

    default Collection<RiftTravelEntry> getAdditionalTravelers(RiftEntity rift) {
        return Collections.emptyList();
    }
}
