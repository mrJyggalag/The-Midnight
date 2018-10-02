package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.render.RenderRift;
import com.mushroom.midnight.common.CommonProxy;
import com.mushroom.midnight.common.entities.EntityRift;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class ClientProxy extends CommonProxy {
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityRift.class, RenderRift::new);
    }
}
