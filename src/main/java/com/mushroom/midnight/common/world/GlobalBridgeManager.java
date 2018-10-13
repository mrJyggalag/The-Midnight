package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class GlobalBridgeManager {
    private static BridgeManager client;

    @SubscribeEvent
    public static void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        client = null;
    }

    public static BridgeManager get(boolean remote) {
        return remote ? getClient() : getServer();
    }

    public static BridgeManager getServer() {
        return BridgeManagerServer.get(FMLCommonHandler.instance().getMinecraftServerInstance());
    }

    public static BridgeManager getClient() {
        if (client == null) {
            client = new BridgeManagerClient();
        }
        return client;
    }
}
