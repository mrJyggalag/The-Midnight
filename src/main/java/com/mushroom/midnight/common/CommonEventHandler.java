package com.mushroom.midnight.common;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RiftCooldownCapability;
import com.mushroom.midnight.common.capability.RifterCapturedCapability;
import com.mushroom.midnight.common.world.RiftSpawnHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class CommonEventHandler {
    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new ResourceLocation(Midnight.MODID, "rift_cooldown"), new RiftCooldownCapability.Impl());
        if (event.getObject() instanceof EntityLivingBase) {
            event.addCapability(new ResourceLocation(Midnight.MODID, "rifter_captured"), new RifterCapturedCapability.Impl());
        }
    }

    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        RiftCooldownCapability capability = entity.getCapability(Midnight.riftCooldownCap, null);
        if (capability != null) {
            capability.update(entity);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            RiftSpawnHandler.update();
        }
    }
}
