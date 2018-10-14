package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
@GameRegistry.ObjectHolder(Midnight.MODID)
public class ModSounds {
    public static final SoundEvent AMBIENT = SoundEvents.AMBIENT_CAVE;
    public static final SoundEvent IDLE = SoundEvents.AMBIENT_CAVE;
    public static final SoundEvent CAVE_IDLE = SoundEvents.AMBIENT_CAVE;

    public static final SoundEvent MUSIC_GENERIC = SoundEvents.MUSIC_NETHER;
    public static final SoundEvent MUSIC_CRYSTAL = SoundEvents.MUSIC_NETHER;

    public static final SoundEvent RIFT_IDLE = SoundEvents.BLOCK_PORTAL_AMBIENT;
    public static final SoundEvent RIFT_UNSTABLE = SoundEvents.BLOCK_PORTAL_AMBIENT;

    public static final SoundEvent RIFTER_AMBIENT = SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    public static final SoundEvent RIFTER_HURT = SoundEvents.ENTITY_ZOMBIE_HURT;
    public static final SoundEvent RIFTER_DEATH = SoundEvents.ENTITY_ZOMBIE_DEATH;

    @SubscribeEvent
    public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "ambient"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "idle"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "cave_idle"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "music_generic"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "music_crystal"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "rift_idle"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "rift_unstable"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "rifter_ambient"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "rifter_hurt"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "rifter_death")))
        );
    }
}
