package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.creature.EntityStinger;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageStingerAttack implements IMessage {
    private int entityId;

    public MessageStingerAttack() {
    }

    public MessageStingerAttack(EntityStinger entity) {
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

    public static class Handler implements IMessageHandler<MessageStingerAttack, IMessage> {
        @Override
        public IMessage onMessage(MessageStingerAttack message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> {
                    World world = player.world;
                    EntityStinger stinger = getEntity(world, message.entityId, EntityStinger.class);
                    if (stinger != null) {
                        stinger.setAttacking(true);
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
