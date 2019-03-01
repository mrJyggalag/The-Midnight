package com.mushroom.midnight.common;

import com.mushroom.midnight.common.util.IProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.function.Consumer;

public class ServerProxy implements IProxy {

    @Override
    public void onInit() {
    }

    @Override
    public boolean isClientPlayer(Entity entity) {
        return false;
    }

    @Override
    public void handleMessage(MessageContext context, Consumer<EntityPlayer> handler) {
        EntityPlayerMP player = context.getServerHandler().player;
        player.getServerWorld().addScheduledTask(() -> handler.accept(player));
    }
}
