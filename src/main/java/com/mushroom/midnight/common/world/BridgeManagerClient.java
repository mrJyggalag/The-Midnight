package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import javax.annotation.Nullable;

public class BridgeManagerClient implements BridgeManager {
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
        this.bridges.put(bridge.getId(), bridge);
    }

    @Override
    public void removeBridge(int id) {
        RiftBridge bridge = this.bridges.remove(id);
        if (bridge != null) {
            bridge.remove();
        }
    }

    @Nullable
    @Override
    public RiftBridge getBridge(int id) {
        return this.bridges.get(id);
    }
}
