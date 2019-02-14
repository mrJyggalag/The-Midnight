package com.mushroom.midnight.client;

import com.mushroom.midnight.client.model.ModModelRegistry;
import com.mushroom.midnight.client.render.TileShadowrootChestRenderer;
import com.mushroom.midnight.common.util.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import com.mushroom.midnight.common.tile.base.TileEntityShadowrootChest;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements IProxy {
    private static final Minecraft MC = Minecraft.getMinecraft();

    @Override
    public void onInit() {
        ModModelRegistry.onInit();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShadowrootChest.class, new TileShadowrootChestRenderer());
    }

    @Override
    public boolean isClientPlayer(Entity entity) {
        return entity == MC.player;
    }
}
