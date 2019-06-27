package com.mushroom.midnight.common.network;

import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.world.BridgeManager;
import com.mushroom.midnight.common.world.GlobalBridgeManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BridgeStateMessage {
    private int bridgeId;
    private ByteBuf data;

    private BridgeStateMessage(int bridgeId, ByteBuf data) {
        this.bridgeId = bridgeId;
        this.data = data;
    }

    public BridgeStateMessage(RiftBridge bridge) {
        this.bridgeId = bridge.getId();
        this.data = Unpooled.buffer().retain();
        bridge.writeState(this.data);
    }

    public void serialize(PacketBuffer buffer) {
        buffer.writeInt(this.bridgeId);
        buffer.writeBytes(this.data);
        this.data.release();
    }

    public static BridgeStateMessage deserialize(PacketBuffer buffer) {
        int bridgeId = buffer.readInt();
        ByteBuf data = buffer.retain();
        return new BridgeStateMessage(bridgeId, data);
    }

    public static void handle(BridgeStateMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                BridgeManager trackerHandler = GlobalBridgeManager.getClient();
                RiftBridge bridge = trackerHandler.getBridge(message.bridgeId);
                if (bridge != null) {
                    bridge.handleState(message.data);
                }
                message.data.release();
            });
            context.setPacketHandled(true);
        }
    }
}
