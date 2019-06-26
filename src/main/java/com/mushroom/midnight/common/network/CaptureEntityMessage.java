package com.mushroom.midnight.common.network;

import com.mushroom.midnight.common.entity.creature.RifterEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CaptureEntityMessage {
    private final int rifterId;
    private final int capturedId;

    private CaptureEntityMessage(int rifterId, int capturedId) {
        this.rifterId = rifterId;
        this.capturedId = capturedId;
    }

    public CaptureEntityMessage(RifterEntity rifter, LivingEntity captured) {
        this(rifter.getEntityId(), captured != null ? captured.getEntityId() : -1);
    }

    public void serialize(PacketBuffer buffer) {
        buffer.writeInt(this.rifterId);
        buffer.writeInt(this.capturedId);
    }

    public static CaptureEntityMessage deserialize(PacketBuffer buffer) {
        int rifterId = buffer.readInt();
        int capturedId = buffer.readInt();
        return new CaptureEntityMessage(rifterId, capturedId);
    }

    public static void handle(CaptureEntityMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                Minecraft client = Minecraft.getInstance();
                ClientPlayerEntity player = client.player;
                RifterEntity rifterEntity = getEntity(player.world, message.rifterId, RifterEntity.class);
                LivingEntity capturedEntity = getEntity(player.world, message.capturedId, LivingEntity.class);
                if (rifterEntity != null) {
                    rifterEntity.setCapturedEntity(capturedEntity);
                }
            });

            context.setPacketHandled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static <T> T getEntity(World world, int id, Class<T> type) {
        Entity entity = world.getEntityByID(id);
        if (entity != null && type.isAssignableFrom(entity.getClass())) {
            return type.cast(entity);
        }
        return null;
    }
}
