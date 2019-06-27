package com.mushroom.midnight.common.network;

import com.mushroom.midnight.common.world.GlobalBridgeManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BridgeRemovalMessage {
    private int bridgeId;

    public BridgeRemovalMessage(int bridgeId) {
        this.bridgeId = bridgeId;
    }

    public void serialize(PacketBuffer buffer) {
        buffer.writeInt(this.bridgeId);
    }

    public static BridgeRemovalMessage deserialize(PacketBuffer buffer) {
        return new BridgeRemovalMessage(buffer.readInt());
    }

    public static void handle(BridgeRemovalMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                GlobalBridgeManager.getClient().removeBridge(message.bridgeId);
            });
            context.setPacketHandled(true);
        }
    }
}
