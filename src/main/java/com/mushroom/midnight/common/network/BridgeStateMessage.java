package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.world.BridgeManager;
import com.mushroom.midnight.common.world.GlobalBridgeManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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

    public static class Handler implements IMessageHandler<BridgeStateMessage, IMessage> {
        @Override
        public IMessage onMessage(BridgeStateMessage message, MessageContext ctx) {
            if (ctx.Dist.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> {
                    BridgeManager trackerHandler = GlobalBridgeManager.getClient();
                    RiftBridge bridge = trackerHandler.getBridge(message.bridgeId);
                    if (bridge != null) {
                        bridge.handleState(message.data);
                    }
                    message.data.release();
                });
            }
            return null;
        }
    }
}
