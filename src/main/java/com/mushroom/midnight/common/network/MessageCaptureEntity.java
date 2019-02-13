package com.mushroom.midnight.common.network;

import com.mushroom.midnight.common.entity.creature.EntityRifter;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageCaptureEntity implements IMessage {
    private int rifterId;
    private int capturedId;

    public MessageCaptureEntity() {
    }

    public MessageCaptureEntity(EntityRifter rifter, EntityLivingBase captured) {
        this.rifterId = rifter.getEntityId();
        this.capturedId = captured != null ? captured.getEntityId() : -1;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.rifterId = buf.readInt();
        this.capturedId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.rifterId);
        buf.writeInt(this.capturedId);
    }

    public static class Handler implements IMessageHandler<MessageCaptureEntity, IMessage> {
        @Override
        public IMessage onMessage(MessageCaptureEntity message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    World world = Minecraft.getMinecraft().world;
                    EntityRifter rifterEntity = getEntity(world, message.rifterId, EntityRifter.class);
                    EntityLivingBase capturedEntity = getEntity(world, message.capturedId, EntityLivingBase.class);
                    if (rifterEntity != null) {
                        rifterEntity.setCapturedEntity(capturedEntity);
                    }
                });
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        private static <T> T getEntity(World world, int id, Class<T> type) {
            Entity entity = world.getEntityByID(id);
            if (entity != null && type.isAssignableFrom(entity.getClass())) {
                return (T) entity;
            }
            return null;
        }
    }
}
