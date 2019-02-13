package com.mushroom.midnight.client;

import com.mushroom.midnight.client.model.ModModelRegistry;
import com.mushroom.midnight.client.render.TileShadowrootChestRenderer;
import com.mushroom.midnight.common.util.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.mushroom.midnight.common.tile.base.TileEntityShadowrootChest;

import java.util.function.Consumer;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements IProxy {
    private static final Minecraft MC = Minecraft.getMinecraft();

    @Override
    public void onInit() {
        ModModelRegistry.onInit();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShadowrootChest.class, new TileShadowrootChestRenderer());
    }

    @Override
    public void handleMessage(MessageContext context, Consumer<EntityPlayer> task) {
        if (context.side.isClient()) {
            MC.addScheduledTask(() -> task.accept(MC.player));
        }
    }

    @Override
    public boolean isClientPlayer(Entity entity) {
        return entity == MC.player;
    }
}
