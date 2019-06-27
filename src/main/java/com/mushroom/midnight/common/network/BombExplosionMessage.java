package com.mushroom.midnight.common.network;

import com.mushroom.midnight.client.particle.MidnightParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BombExplosionMessage {
    private double posX, posY, posZ;
    private int color;

    public BombExplosionMessage(double posX, double posY, double posZ, int color) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.color = color;
    }

    public void serialize(PacketBuffer buffer) {
        buffer.writeDouble(this.posX);
        buffer.writeDouble(this.posY);
        buffer.writeDouble(this.posZ);
        buffer.writeInt(this.color);
    }

    public static BombExplosionMessage deserialize(PacketBuffer buffer) {
        double posX = buffer.readDouble();
        double posY = buffer.readDouble();
        double posZ = buffer.readDouble();
        int color = buffer.readInt();
        return new BombExplosionMessage(posX, posY, posZ, color);
    }

    public static void handle(BombExplosionMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                if (player.world.isBlockLoaded(new BlockPos(message.posX, message.posY, message.posZ))) {
                    MidnightParticles.BOMB_EXPLOSION.spawn(player.world, message.posX, message.posY, message.posZ, 1d, 0d, 0d, message.color);
                }
            });
            context.setPacketHandled(true);
        }
    }
}
