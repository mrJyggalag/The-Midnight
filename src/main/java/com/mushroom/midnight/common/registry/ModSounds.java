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
    public static final SoundEvent MIDNIGHT_AMBIENT = SoundEvents.AMBIENT_CAVE;
    public static final SoundEvent MIDNIGHT_RUMBLE = SoundEvents.AMBIENT_CAVE;

    public static final SoundEvent MIDNIGHT_MUSIC_GENERIC = SoundEvents.MUSIC_NETHER;
    public static final SoundEvent MIDNIGHT_MUSIC_CRYSTAL = SoundEvents.MUSIC_NETHER;

    @SubscribeEvent
    public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "midnight_ambient"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "midnight_rumble"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "midnight_music_generic"))),
                RegUtil.applyName(new SoundEvent(new ResourceLocation(Midnight.MODID, "midnight_music_crystal")))
        );
    }
}
