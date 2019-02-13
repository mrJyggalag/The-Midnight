package com.mushroom.midnight.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.function.Consumer;

public interface IProxy {
    void onInit();
    void handleMessage(MessageContext context, Consumer<EntityPlayer> task);
    boolean isClientPlayer(Entity entity);
}
