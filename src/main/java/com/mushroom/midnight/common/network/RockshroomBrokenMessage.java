package com.mushroom.midnight.common.network;

import com.mushroom.midnight.common.block.RockshroomBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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

    public static void handle(RockshroomBrokenMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                if (player.world.isBlockLoaded(message.pos)) {
                    ((RockshroomBlock) MidnightBlocks.ROCKSHROOM).spawnSpores(player.world, message.pos);
                }
            });

            context.setPacketHandled(true);
        }
    }
}
