package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.GlobalBridgeManager;
import net.minecraft.network.PacketBuffer;

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

    public static class Handler implements IMessageHandler<BridgeRemovalMessage, IMessage> {
        @Override
        public IMessage onMessage(BridgeRemovalMessage message, MessageContext ctx) {
            if (ctx.Dist.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> GlobalBridgeManager.getClient().removeBridge(message.bridgeId));
            }
            return null;
        }
    }
}
