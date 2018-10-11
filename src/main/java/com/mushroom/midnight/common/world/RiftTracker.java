package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;

import javax.annotation.Nullable;

public interface RiftTracker {
    void update();

    RiftBridge createBridge(RiftAttachment attachment);

    void addBridge(RiftBridge bridge);

    void removeBridge(int id);

    @Nullable
    RiftBridge getBridge(int id);
}
