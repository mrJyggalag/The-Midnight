package com.mushroom.midnight.common;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RiftSpawnerCapability;
import com.mushroom.midnight.common.capability.RiftCooldownCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
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
    }

    @SubscribeEvent
    public static void onAttachWorldCapabilities(AttachCapabilitiesEvent<World> event) {
        World world = event.getObject();
        if (!world.isRemote && world.provider.getDimensionType() == DimensionType.OVERWORLD) {
            event.addCapability(new ResourceLocation(Midnight.MODID, "random_rift_event"), new RiftSpawnerCapability.Impl());
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
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            World world = event.world;
            if (!world.isRemote) {
                RiftSpawnerCapability riftSpawner = world.getCapability(Midnight.riftSpawnerCap, null);
                if (riftSpawner != null) {
                    riftSpawner.update(world);
                }
            }
        }
    }
}
