package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.world.BridgeManager;
import com.mushroom.midnight.common.world.GlobalBridgeManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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

    public static class Handler implements IMessageHandler<BridgeCreateMessage, IMessage> {
        @Override
        public IMessage onMessage(BridgeCreateMessage message, MessageContext ctx) {
            if (ctx.Dist.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> {
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
            }
            return null;
        }
    }
}
