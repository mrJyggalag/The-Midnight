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
    public static final SoundEvent AMBIENT = makeSoundEvent("ambient");
    public static final SoundEvent IDLE = makeSoundEvent("idle");
    public static final SoundEvent CAVE_IDLE = makeSoundEvent("cave_idle");

    public static final SoundEvent MUSIC_GENERIC = makeSoundEvent("music_generic");
    public static final SoundEvent MUSIC_CRYSTAL = makeSoundEvent("music_crystal");
    public static final SoundEvent MUSIC_DARK_WILLOW = makeSoundEvent("music_dark_willow");

    public static final SoundEvent RIFT_IDLE = makeSoundEvent("rift_idle");
    public static final SoundEvent RIFT_UNSTABLE = makeSoundEvent("rift_unstable");

    public static final SoundEvent RIFTER_AMBIENT = makeSoundEvent("rifter_ambient");
    public static final SoundEvent RIFTER_HURT = makeSoundEvent("rifter_hurt");
    public static final SoundEvent RIFTER_DEATH = makeSoundEvent( "rifter_death");

    public static final SoundEvent BLADESHROOM_CAP_SHOOT = makeSoundEvent("bladeshroom_cap_shoot");
    public static final SoundEvent BLADESHROOM_CAP_HIT = makeSoundEvent("bladeshroom_cap_hit");

    public static final SoundEvent MUD_DIG = makeSoundEvent("mud_dig");
    public static final SoundEvent MUD_STEP = makeSoundEvent("mud_step");

    public static final SoundEvent NIGHTSTAG_AMBIENT = makeSoundEvent("nightstag_ambient");
    public static final SoundEvent NIGHTSTAG_HURT = makeSoundEvent("nightstag_hurt");
    public static final SoundEvent NIGHTSTAG_DEATH = makeSoundEvent("nightstag_death");
    public static final SoundEvent NIGHTSTAG_STEP = makeSoundEvent("nightstag_step");

    public static final SoundEvent CRYSTAL_BUG_FLYING = makeSoundEvent("crystal_bug_flying");
    public static final SoundEvent CRYSTAL_BUG_HURT = makeSoundEvent("crystal_bug_hurt");
    public static final SoundEvent CRYSTAL_BUG_DEATH = makeSoundEvent("crystal_bug_death");

    public static final SoundEvent NOVA_DEATH = makeSoundEvent("nova_death");
    public static final SoundEvent NOVA_HURT = makeSoundEvent("nova_hurt");
    public static final SoundEvent NOVA_IDLE = makeSoundEvent("nova_idle");

    public static final SoundEvent STINGER_DEATH = makeSoundEvent("stinger_death");
    public static final SoundEvent STINGER_HURT = makeSoundEvent("stinger_hurt");
    public static final SoundEvent STINGER_AMBIENT = makeSoundEvent("stinger_ambient");

    public static final SoundEvent SKULK_DEATH = makeSoundEvent("skulk_death");
    public static final SoundEvent SKULK_HURT = makeSoundEvent("skulk_hurt");

    public static final SoundEvent SNAPPER_DEATH = makeSoundEvent("snapper_death");
    public static final SoundEvent SNAPPER_HURT = makeSoundEvent("snapper_hurt");

    public static final SoundEvent HUNTER_DEATH = makeSoundEvent("hunter_death");
    public static final SoundEvent HUNTER_HURT = makeSoundEvent("hunter_hurt");
    public static final SoundEvent HUNTER_IDLE = makeSoundEvent("hunter_idle"); // unused?
    public static final SoundEvent HUNTER_FLYING = makeSoundEvent("hunter_flying");

    public static final SoundEvent EGG_CRACKED = makeSoundEvent("egg_cracked");

    @SubscribeEvent
    public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                AMBIENT,
                IDLE,
                CAVE_IDLE,
                MUSIC_GENERIC,
                MUSIC_CRYSTAL,
                MUSIC_DARK_WILLOW,
                RIFT_IDLE,
                RIFT_UNSTABLE,
                RIFTER_AMBIENT,
                RIFTER_HURT,
                RIFTER_DEATH,
                BLADESHROOM_CAP_SHOOT,
                BLADESHROOM_CAP_HIT,
                MUD_DIG,
                MUD_STEP,
                NIGHTSTAG_AMBIENT, NIGHTSTAG_HURT, NIGHTSTAG_DEATH, NIGHTSTAG_STEP,
                CRYSTAL_BUG_FLYING, CRYSTAL_BUG_HURT, CRYSTAL_BUG_DEATH,
                NOVA_DEATH, NOVA_HURT, NOVA_IDLE,
                STINGER_DEATH, STINGER_HURT, STINGER_AMBIENT,
                SKULK_DEATH, SKULK_HURT,
                SNAPPER_DEATH, SNAPPER_HURT,
                HUNTER_DEATH, HUNTER_HURT, HUNTER_IDLE, HUNTER_FLYING,
                EGG_CRACKED
        );
    }

    private static SoundEvent makeSoundEvent(String name) {
        SoundEvent sound = new SoundEvent(new ResourceLocation(MODID, "midnight." + name));
        sound.setRegistryName(new ResourceLocation(MODID, name));
        return sound;
    }
}
