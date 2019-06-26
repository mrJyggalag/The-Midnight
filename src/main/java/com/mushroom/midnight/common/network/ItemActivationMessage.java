package com.mushroom.midnight.common.network;

import com.mushroom.midnight.Midnight;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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

    public static class Handler implements IMessageHandler<ItemActivationMessage, IMessage> {
        @Override
        public IMessage onMessage(ItemActivationMessage message, MessageContext ctx) {
            if (ctx.Dist.isClient()) {
                Midnight.proxy.handleMessage(ctx, player -> Minecraft.getInstance().entityRenderer.displayItemActivation(message.stack));
            }
            return null;
        }
    }
}
