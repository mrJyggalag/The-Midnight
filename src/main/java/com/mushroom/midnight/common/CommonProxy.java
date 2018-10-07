package com.mushroom.midnight.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.function.Consumer;

public class CommonProxy {
    public void onInit() {
    }

    public void handleMessage(MessageContext context, Consumer<EntityPlayer> task) {
        EntityPlayerMP player = context.getServerHandler().player;
        World world = player.world;
        if (world instanceof WorldServer) {
            ((WorldServer) world).addScheduledTask(() -> task.accept(player));
        }
    }

    public boolean isClientPlayer(Entity entity) {
        return false;
    }
}
