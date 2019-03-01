package com.mushroom.midnight.common.network;

import com.mushroom.midnight.common.block.BlockRockshroom;
import com.mushroom.midnight.common.registry.ModBlocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRockshroomBroken implements IMessage {
    private BlockPos pos;

    public MessageRockshroomBroken() {
    }

    public MessageRockshroomBroken(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
    }

    public static class Handler implements IMessageHandler<MessageRockshroomBroken, IMessage> {
        @Override
        public IMessage onMessage(MessageRockshroomBroken message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    if (Minecraft.getMinecraft().world.isBlockLoaded(message.pos)) {
                        ((BlockRockshroom) ModBlocks.ROCKSHROOM).spawnSpores(Minecraft.getMinecraft().world, message.pos);
                    }
                });
            }
            return null;
        }
    }
}
