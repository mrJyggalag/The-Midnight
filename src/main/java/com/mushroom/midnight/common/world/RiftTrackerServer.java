package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.network.MessageBridgeCreate;
import com.mushroom.midnight.common.network.MessageBridgeRemoval;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

import javax.annotation.Nullable;

public class RiftTrackerServer implements RiftTracker {
    private final Int2ObjectMap<RiftBridge> bridges = new Int2ObjectOpenHashMap<>();
    private int currentId;

    @Override
    public void update() {
        ObjectIterator<RiftBridge> iterator = this.bridges.values().iterator();
        while (iterator.hasNext()) {
            RiftBridge bridge = iterator.next();
            bridge.tickTimers();

            if (bridge.tickState()) {
                bridge.exists = false;
                bridge.sendToTracking(new MessageBridgeRemoval(bridge.getBridgeId()));

                iterator.remove();
            }
        }
    }

    @Override
    public RiftBridge createBridge(RiftAttachment attachment) {
        return new RiftBridge(this.currentId++, attachment);
    }

    @Override
    public void addBridge(RiftBridge bridge) {
        this.bridges.put(bridge.getBridgeId(), bridge);
        bridge.sendToTracking(new MessageBridgeCreate(bridge));
    }

    @Override
    public void removeBridge(int id) {
        RiftBridge bridge = this.bridges.remove(id);
        if (bridge != null) {
            bridge.sendToTracking(new MessageBridgeRemoval(bridge.getBridgeId()));
        }
    }

    @Nullable
    @Override
    public RiftBridge getBridge(int id) {
        return this.bridges.get(id);
    }
}
