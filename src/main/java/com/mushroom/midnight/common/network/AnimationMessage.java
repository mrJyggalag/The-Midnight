package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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

    public static void handle(AnimationMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                Entity entity = Minecraft.getInstance().player.world.getEntityByID(message.entityId);
                if (entity != null) {
                    entity.getCapability(Midnight.ANIMATION_CAP, null).ifPresent(animationCap -> animationCap.setAnimation(entity, message.animationType, message.duration));
                }
            });
            context.setPacketHandled(true);
        }
    }
}
