package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.particle.MidnightParticles;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBombExplosion implements IMessage {
    private double posX, posY, posZ;
    private int color;

    public MessageBombExplosion() {
    }

    public MessageBombExplosion(double posX, double posY, double posZ, int color) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.color = color;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readDouble();
        this.posY = buf.readDouble();
        this.posZ = buf.readDouble();
        this.color = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.posX);
        buf.writeDouble(this.posY);
        buf.writeDouble(this.posZ);
        buf.writeInt(this.color);
    }

    public static class Handler implements IMessageHandler<MessageBombExplosion, IMessage> {
        @Override
        public IMessage onMessage(MessageBombExplosion message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> {
                    if (player.world.isBlockLoaded(new BlockPos(message.posX, message.posY, message.posZ))) {
                        MidnightParticles.BOMB_EXPLOSION.spawn(player.world, message.posX, message.posY, message.posZ, 1d, 0d, 0d, message.color);
                    }
                });
            }
            return null;
        }
    }
}
