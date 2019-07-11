package com.mushroom.midnight.common.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.mushroom.midnight.Midnight.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightSounds {
    public static final SoundEvent AMBIENT = null;
    public static final SoundEvent IDLE = null;
    public static final SoundEvent CAVE_IDLE = null;

    public static final SoundEvent MUSIC_GENERIC = null;
    public static final SoundEvent MUSIC_CRYSTAL = null;
    public static final SoundEvent MUSIC_DARK_WILLOW = null;

    public static final SoundEvent RIFT_IDLE = null;
    public static final SoundEvent RIFT_UNSTABLE = null;

    public static final SoundEvent RIFTER_AMBIENT = null;
    public static final SoundEvent RIFTER_HURT = null;
    public static final SoundEvent RIFTER_DEATH = null;

    public static final SoundEvent BLADESHROOM_CAP_SHOOT = null;
    public static final SoundEvent BLADESHROOM_CAP_HIT = null;

    public static final SoundEvent MUD_DIG = null;
    public static final SoundEvent MUD_STEP = null;

    public static final SoundEvent NIGHTSTAG_AMBIENT = null;
    public static final SoundEvent NIGHTSTAG_HURT = null;
    public static final SoundEvent NIGHTSTAG_DEATH = null;
    public static final SoundEvent NIGHTSTAG_STEP = null;

    public static final SoundEvent CRYSTAL_BUG_FLYING = null;
    public static final SoundEvent CRYSTAL_BUG_HURT = null;
    public static final SoundEvent CRYSTAL_BUG_DEATH = null;

    public static final SoundEvent NOVA_DEATH = null;
    public static final SoundEvent NOVA_HURT = null;
    public static final SoundEvent NOVA_IDLE = null;

    public static final SoundEvent STINGER_DEATH = null;
    public static final SoundEvent STINGER_HURT = null;
    public static final SoundEvent STINGER_AMBIENT = null;

    public static final SoundEvent SKULK_DEATH = null;
    public static final SoundEvent SKULK_HURT = null;

    public static final SoundEvent SNAPPER_DEATH = null;
    public static final SoundEvent SNAPPER_HURT = null;

    public static final SoundEvent HUNTER_DEATH = null;
    public static final SoundEvent HUNTER_HURT = null;
    public static final SoundEvent HUNTER_FLYING = null;

    public static final SoundEvent EGG_CRACKED = null;

    @SubscribeEvent
    public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                makeSoundEvent("ambient"),
                makeSoundEvent("idle"),
                makeSoundEvent("cave_idle"),
                makeSoundEvent("music_generic"),
                makeSoundEvent("music_crystal"),
                makeSoundEvent("music_dark_willow"),
                makeSoundEvent("rift_idle"),
                makeSoundEvent("rift_unstable"),
                makeSoundEvent("rifter_ambient"),
                makeSoundEvent("rifter_hurt"),
                makeSoundEvent( "rifter_death"),
                makeSoundEvent("bladeshroom_cap_shoot"),
                makeSoundEvent("bladeshroom_cap_hit"),
                makeSoundEvent("mud_dig"),
                makeSoundEvent("mud_step"),
                makeSoundEvent("nightstag_ambient"),
                makeSoundEvent("nightstag_hurt"),
                makeSoundEvent("nightstag_death"),
                makeSoundEvent("nightstag_step"),
                makeSoundEvent("crystal_bug_flying"),
                makeSoundEvent("crystal_bug_hurt"),
                makeSoundEvent("crystal_bug_death"),
                makeSoundEvent("nova_death"),
                makeSoundEvent("nova_hurt"),
                makeSoundEvent("nova_idle"),
                makeSoundEvent("stinger_death"),
                makeSoundEvent("stinger_hurt"),
                makeSoundEvent("stinger_ambient"),
                makeSoundEvent("skulk_death"),
                makeSoundEvent("skulk_hurt"),
                makeSoundEvent("snapper_death"),
                makeSoundEvent("snapper_hurt"),
                makeSoundEvent("hunter_death"),
                makeSoundEvent("hunter_hurt"),
                makeSoundEvent("hunter_flying"),
                makeSoundEvent("egg_cracked")
        );
    }

    private static SoundEvent makeSoundEvent(String name) {
        SoundEvent sound = new SoundEvent(new ResourceLocation(MODID, name));
        sound.setRegistryName(new ResourceLocation(MODID, name));
        return sound;
    }
}
