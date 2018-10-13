package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.network.MessageBridgeCreate;
import com.mushroom.midnight.common.network.MessageBridgeRemoval;
import com.mushroom.midnight.common.network.MessageBridgeState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BridgeTracker {
    private final RiftBridge bridge;
    private final Set<EntityPlayerMP> currentTrackingPlayers = new HashSet<>();

    public BridgeTracker(RiftBridge bridge) {
        this.bridge = bridge;
    }

    public void update() {
        if (this.bridge.isDirty() && !this.currentTrackingPlayers.isEmpty()) {
            this.sendToTracking(new MessageBridgeState(this.bridge));
            this.bridge.clearDirt();
        }

        Collection<EntityPlayerMP> trackingPlayers = this.collectTrackingPlayers();

        for (EntityPlayerMP player : trackingPlayers) {
            if (!this.currentTrackingPlayers.contains(player)) {
                Midnight.NETWORK.sendTo(new MessageBridgeCreate(this.bridge), player);
            }
        }

        for (EntityPlayerMP player : this.currentTrackingPlayers) {
            if (!trackingPlayers.contains(player)) {
                Midnight.NETWORK.sendTo(new MessageBridgeRemoval(this.bridge.getId()), player);
            }
        }

        this.currentTrackingPlayers.clear();
        this.currentTrackingPlayers.addAll(trackingPlayers);
    }

    public void sendToTracking(IMessage message) {
        for (EntityPlayerMP player : this.currentTrackingPlayers) {
            Midnight.NETWORK.sendTo(message, player);
        }
    }

    private Collection<EntityPlayerMP> collectTrackingPlayers() {
        Collection<EntityPlayerMP> trackingPlayers = new ArrayList<>();

        EntityRift source = this.bridge.getSource();
        if (source != null) {
            this.collectTrackingPlayers(trackingPlayers, source);
        }

        EntityRift target = this.bridge.getTarget();
        if (target != null) {
            this.collectTrackingPlayers(trackingPlayers, target);
        }

        return trackingPlayers;
    }

    private void collectTrackingPlayers(Collection<EntityPlayerMP> trackingPlayers, EntityRift target) {
        if (!(target.world instanceof WorldServer)) {
            return;
        }
        WorldServer world = (WorldServer) target.world;
        trackingPlayers.addAll(world.getEntityTracker().getTrackingPlayers(target).stream()
                .filter(o -> o instanceof EntityPlayerMP)
                .map(o -> (EntityPlayerMP) o)
                .collect(Collectors.toList())
        );
    }
}
