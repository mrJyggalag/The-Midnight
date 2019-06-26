package com.mushroom.midnight.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.function.Consumer;

public interface IProxy {
    void onInit();

    boolean isClientPlayer(Entity entity);

    void handleMessage(MessageContext context, Consumer<PlayerEntity> handler);
}
