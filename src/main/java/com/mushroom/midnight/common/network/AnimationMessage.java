package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.capability.AnimationCapability.Type;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AnimationMessage {
    private int entityId;
    private Type animationType;
    private int duration;

    private AnimationMessage(int entityId, Type animationType, int duration) {
        this.entityId = entityId;
        this.animationType = animationType;
        this.duration = duration;
    }

    public AnimationMessage(Entity entity, Type animationType, int duration) {
        this(entity.getEntityId(), animationType, duration);
    }

    public void serialize(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.animationType.ordinal());
        buffer.writeInt(this.duration);
    }

    public static AnimationMessage deserialize(PacketBuffer buffer) {
        int entityId = buffer.readInt();
        Type animationType = Type.values()[buffer.readInt() % Type.values().length];
        int duration = buffer.readInt();
        return new AnimationMessage(entityId, animationType, duration);
    }

    public static class Handler implements IMessageHandler<AnimationMessage, IMessage> {
        @Override
        public IMessage onMessage(AnimationMessage message, MessageContext ctx) {
            if (ctx.Dist.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> {
                    Entity entity = player.world.getEntityByID(message.entityId);
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
