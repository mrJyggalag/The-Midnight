package com.mushroom.midnight.common;

import com.mushroom.midnight.common.util.IProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.function.Consumer;

public class ServerProxy implements IProxy {

    @Override
    public void onInit() {
    }

    @Override
    public void handleMessage(MessageContext context, Consumer<EntityPlayer> task) {
        EntityPlayerMP player = context.getServerHandler().player;
        World world = player.world;
        if (world instanceof WorldServer) {
            ((WorldServer) world).addScheduledTask(() -> task.accept(player));
        }
    }

    @Override
    public boolean isClientPlayer(Entity entity) {
        return false;
    }
}
