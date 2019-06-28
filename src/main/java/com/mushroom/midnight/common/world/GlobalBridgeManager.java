package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GlobalBridgeManager {
    private static BridgeManager client;

    @SubscribeEvent
    public static void onClientDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        client = null;
    }

    public static BridgeManager get(boolean remote) {
        return remote ? getClient() : getServer();
    }

    public static BridgeManager getServer() {
        return BridgeManagerServer.get(LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER));
    }

    public static BridgeManager getClient() {
        if (client == null) {
            client = new BridgeManagerClient();
        }
        return client;
    }
}
