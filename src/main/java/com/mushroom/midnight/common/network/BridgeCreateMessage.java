package com.mushroom.midnight.common.network;

import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.world.BridgeManager;
import com.mushroom.midnight.common.world.GlobalBridgeManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BridgeCreateMessage {
    private int bridgeId;
    private RiftAttachment attachment;
    private ByteBuf data;

    private BridgeCreateMessage(int bridgeId, RiftAttachment attachment, ByteBuf data) {
        this.bridgeId = bridgeId;
        this.attachment = attachment;
        this.data = data;
    }

    public BridgeCreateMessage(RiftBridge bridge) {
        this.bridgeId = bridge.getId();
        this.attachment = bridge.getAttachment();
        this.data = Unpooled.buffer().retain();
        bridge.writeState(this.data);
    }

    public void serialize(PacketBuffer buffer) {
        buffer.writeInt(this.bridgeId);
        this.attachment.write(buffer);
        buffer.writeBytes(this.data);
        this.data.release();
    }

    public static BridgeCreateMessage deserialize(PacketBuffer buffer) {
        int bridgeId = buffer.readInt();
        RiftAttachment attachment = RiftAttachment.read(buffer);
        ByteBuf data = buffer.retain();
        return new BridgeCreateMessage(bridgeId, attachment, data);
    }

    public static void handle(BridgeCreateMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                BridgeManager trackerHandler = GlobalBridgeManager.getClient();

                RiftBridge bridge = trackerHandler.getBridge(message.bridgeId);
                if (bridge == null) {
                    bridge = new RiftBridge(message.bridgeId, message.attachment);
                    trackerHandler.addBridge(bridge);
                }

                bridge.setAttachment(message.attachment);
                bridge.handleState(message.data);

                message.data.release();
            });
            context.setPacketHandled(true);
        }
    }
}
