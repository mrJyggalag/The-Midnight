package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.RockshroomBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RockshroomBrokenMessage {
    private BlockPos pos;

    public RockshroomBrokenMessage(BlockPos pos) {
        this.pos = pos;
    }

    public void serialize(PacketBuffer buffer) {
        buffer.writeInt(this.pos.getX());
        buffer.writeInt(this.pos.getY());
        buffer.writeInt(this.pos.getZ());
    }

    public static RockshroomBrokenMessage deserialize(PacketBuffer buffer) {
        int x = buffer.readInt();
        int y = buffer.readInt();
        int z = buffer.readInt();
        return new RockshroomBrokenMessage(new BlockPos(x, y, z));
    }

    public static class Handler implements IMessageHandler<RockshroomBrokenMessage, IMessage> {
        @Override
        public IMessage onMessage(RockshroomBrokenMessage message, MessageContext ctx) {
            if (ctx.Dist.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> {
                    if (player.world.isBlockLoaded(message.pos)) {
                        ((RockshroomBlock) MidnightBlocks.ROCKSHROOM).spawnSpores(player.world, message.pos);
                    }
                });
            }
            return null;
        }
    }
}
