package com.mushroom.midnight.client;

import com.mushroom.midnight.client.model.ModModelRegistry;
import com.mushroom.midnight.client.render.TileShadowrootChestRenderer;
import com.mushroom.midnight.common.CommonProxy;
import com.mushroom.midnight.common.tile.base.TileEntityShadowrootChest;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void onInit() {
        super.onInit();
        ModModelRegistry.onInit();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShadowrootChest.class, new TileShadowrootChestRenderer());
    }
}
