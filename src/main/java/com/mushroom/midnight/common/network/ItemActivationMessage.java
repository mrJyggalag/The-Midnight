package com.mushroom.midnight.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ItemActivationMessage {
    private ItemStack stack;

    public ItemActivationMessage(ItemStack stack) {
        this.stack = stack;
    }

    public void serialize(PacketBuffer buffer) {
        buffer.writeItemStack(this.stack);
    }

    public static ItemActivationMessage deserialize(PacketBuffer buffer) {
        ItemStack stack = buffer.readItemStack();
        return new ItemActivationMessage(stack);
    }

    public static void handle(ItemActivationMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                Minecraft.getInstance().gameRenderer.displayItemActivation(message.stack);
            });
            context.setPacketHandled(true);
        }
    }
}
