package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class RiftTrackerHandler {
    private static RiftTracker client;
    private static RiftTracker server;

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event) {
        World world = event.getWorld();
        if (!world.isRemote && world.provider.getDimensionType() == DimensionType.OVERWORLD) {
            server = null;
        }
    }

    @SubscribeEvent
    public static void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        client = null;
    }

    public static RiftTracker get(boolean remote) {
        return remote ? getClient() : getServer();
    }

    public static RiftTracker getServer() {
        if (server == null) {
            server = new RiftTrackerServer();
        }
        return server;
    }

    public static RiftTracker getClient() {
        if (client == null) {
            client = new RiftTrackerClient();
        }
        return client;
    }
}
