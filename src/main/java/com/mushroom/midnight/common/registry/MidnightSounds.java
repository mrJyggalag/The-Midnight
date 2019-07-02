package com.mushroom.midnight.common.registry;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.mushroom.midnight.Midnight.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightSounds {
    public static final SoundEvent AMBIENT = makeSoundEvent("ambient");
    public static final SoundEvent IDLE = makeSoundEvent("idle");
    public static final SoundEvent CAVE_IDLE = makeSoundEvent("cave_idle");

    public static final SoundEvent MUSIC_GENERIC = makeSoundEvent("music_generic");
    public static final SoundEvent MUSIC_CRYSTAL = makeSoundEvent("music_crystal");

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

    public static final SoundEvent CRYSTAL_BUG_FLYING = makeSoundEvent("crystal_bug_flying");
    public static final SoundEvent CRYSTAL_BUG_HURT = makeSoundEvent("crystal_bug_hurt");
    public static final SoundEvent CRYSTAL_BUG_DEATH = makeSoundEvent("crystal_bug_death");

    public static final SoundEvent NOVA_DEATH = makeSoundEvent("nova_death");
    public static final SoundEvent NOVA_HURT = makeSoundEvent("nova_hurt");
    public static final SoundEvent NOVA_IDLE = makeSoundEvent("nova_idle");

    public static final SoundEvent EGG_CRACKED = makeSoundEvent("egg_cracked");

    public static final SoundType MUD = new SoundType(1.0F, 1.0F, MidnightSounds.MUD_DIG, MidnightSounds.MUD_STEP, MidnightSounds.MUD_DIG, MidnightSounds.MUD_DIG, MidnightSounds.MUD_STEP);
    public static final SoundType PILE_OF_EGGS = new SoundType(1.0F, 1.0F, MidnightSounds.EGG_CRACKED, SoundEvents.BLOCK_STONE_STEP, SoundEvents.BLOCK_STONE_PLACE, SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_FALL);

    @SubscribeEvent
    public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                AMBIENT,
                IDLE,
                CAVE_IDLE,
                MUSIC_GENERIC,
                MUSIC_CRYSTAL,
                RIFT_IDLE,
                RIFT_UNSTABLE,
                RIFTER_AMBIENT,
                RIFTER_HURT,
                RIFTER_DEATH,
                BLADESHROOM_CAP_SHOOT,
                BLADESHROOM_CAP_HIT,
                MUD_DIG,
                MUD_STEP,
                NIGHTSTAG_AMBIENT, NIGHTSTAG_HURT, NIGHTSTAG_DEATH,
                CRYSTAL_BUG_FLYING, CRYSTAL_BUG_HURT, CRYSTAL_BUG_DEATH,
                NOVA_DEATH, NOVA_HURT, NOVA_IDLE,
                EGG_CRACKED
        );
    }

    private static SoundEvent makeSoundEvent(String name) {
        SoundEvent sound = new SoundEvent(new ResourceLocation(MODID, name));
        sound.setRegistryName(new ResourceLocation(MODID, name));
        return sound;
    }
}
