package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.capability.AnimationCapability.AnimationType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageAnimation implements IMessage {
    private int entityId, duration;
    private AnimationType animationType;

    public MessageAnimation() {
    }

    public MessageAnimation(Entity entity, AnimationType animationType, int duration) {
        this.entityId = entity.getEntityId();
        this.animationType = animationType;
        this.duration = duration;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.animationType = AnimationType.values()[buf.readInt()];
        this.duration = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.animationType.ordinal());
        buf.writeInt(this.duration);
    }

    public static class Handler implements IMessageHandler<MessageAnimation, IMessage> {
        @Override
        public IMessage onMessage(MessageAnimation message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> {
                    World world = player.world;
                    Entity entity = world.getEntityByID(message.entityId);
                    if (entity != null) {
                        AnimationCapability animationCap = entity.getCapability(Midnight.ANIMATION_CAP, null);
                        if (animationCap != null) {
                            animationCap.setAnimation(entity, message.animationType, message.duration);
                        }
                    }
                });
            }
            return null;
        }
    }
}
