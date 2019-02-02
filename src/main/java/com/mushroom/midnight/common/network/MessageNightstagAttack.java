package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.creature.EntityNightStag;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageNightstagAttack implements IMessage {
    private int entityId;

    public MessageNightstagAttack() {
    }

    public MessageNightstagAttack(EntityNightStag entity) {
        this.entityId = entity.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
    }

    public static class Handler implements IMessageHandler<MessageNightstagAttack, IMessage> {
        @Override
        public IMessage onMessage(MessageNightstagAttack message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> {
                    World world = player.world;
                    EntityNightStag nighstag = getEntity(world, message.entityId, EntityNightStag.class);
                    if (nighstag != null) {
                        nighstag.setAttacking(true);
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
