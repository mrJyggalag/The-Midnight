package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import javax.annotation.Nullable;

public class RiftTrackerClient implements RiftTracker {
    private final Int2ObjectMap<RiftBridge> bridges = new Int2ObjectOpenHashMap<>();

    @Override
    public void update() {
        for (RiftBridge bridge : this.bridges.values()) {
            bridge.tickTimers();
        }
    }

    @Override
    public RiftBridge createBridge(RiftAttachment attachment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addBridge(RiftBridge bridge) {
        this.bridges.put(bridge.getBridgeId(), bridge);
    }

    @Override
    public void removeBridge(int id) {
        this.bridges.remove(id);
    }

    @Nullable
    @Override
    public RiftBridge getBridge(int id) {
        return this.bridges.get(id);
    }
}
