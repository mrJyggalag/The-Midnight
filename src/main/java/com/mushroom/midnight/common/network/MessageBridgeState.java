package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.world.RiftTracker;
import com.mushroom.midnight.common.world.RiftTrackerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBridgeState implements IMessage {
    private int bridgeId;
    private ByteBuf data;

    public MessageBridgeState() {
    }

    public MessageBridgeState(RiftBridge bridge) {
        this.bridgeId = bridge.getBridgeId();
        this.data = Unpooled.buffer();
        bridge.writeState(this.data);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.bridgeId = buf.readInt();
        this.data = buf.retain();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.bridgeId);
        buf.writeBytes(this.data);
    }

    public static class Handler implements IMessageHandler<MessageBridgeState, IMessage> {
        @Override
        public IMessage onMessage(MessageBridgeState message, MessageContext ctx) {
            Midnight.proxy.handleMessage(ctx, player -> {
                RiftTracker trackerHandler = RiftTrackerHandler.getClient();
                RiftBridge bridge = trackerHandler.getBridge(message.bridgeId);
                if (bridge != null) {
                    bridge.handleState(message.data);
                }
                message.data.release();
            });

            return null;
        }
    }
}
