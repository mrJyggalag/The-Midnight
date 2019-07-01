package com.mushroom.midnight.common.registry;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.mushroom.midnight.Midnight.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightSounds {
    public static final SoundEvent AMBIENT = RegUtil.injected();
    public static final SoundEvent IDLE = RegUtil.injected();
    public static final SoundEvent CAVE_IDLE = RegUtil.injected();

    public static final SoundEvent MUSIC_GENERIC = RegUtil.injected();
    public static final SoundEvent MUSIC_CRYSTAL = RegUtil.injected();

    public static final SoundEvent RIFT_IDLE = RegUtil.injected();
    public static final SoundEvent RIFT_UNSTABLE = RegUtil.injected();

    public static final SoundEvent RIFTER_AMBIENT = RegUtil.injected();
    public static final SoundEvent RIFTER_HURT = RegUtil.injected();
    public static final SoundEvent RIFTER_DEATH = RegUtil.injected();

    public static final SoundEvent BLADESHROOM_CAP_SHOOT = RegUtil.injected();
    public static final SoundEvent BLADESHROOM_CAP_HIT = RegUtil.injected();

    public static final SoundEvent MUD_DIG = RegUtil.injected();
    public static final SoundEvent MUD_STEP = RegUtil.injected();

    public static final SoundEvent NIGHTSTAG_AMBIENT = RegUtil.injected();
    public static final SoundEvent NIGHTSTAG_HURT = RegUtil.injected();
    public static final SoundEvent NIGHTSTAG_DEATH = RegUtil.injected();

    public static final SoundEvent CRYSTAL_BUG_FLYING = RegUtil.injected();
    public static final SoundEvent CRYSTAL_BUG_HURT = RegUtil.injected();
    public static final SoundEvent CRYSTAL_BUG_DEATH = RegUtil.injected();

    public static final SoundEvent NOVA_DEATH = RegUtil.injected();
    public static final SoundEvent NOVA_HURT = RegUtil.injected();
    public static final SoundEvent NOVA_IDLE = RegUtil.injected();

    public static final SoundEvent EGG_CRACKED = RegUtil.injected();

    public static SoundType MUD;
    public static SoundType PILE_OF_EGGS;

    @SubscribeEvent
    public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event) {
        SoundEvent mudDig = makeSoundEvent("mud_dig");
        SoundEvent mudStep = makeSoundEvent("mud_step");
        SoundEvent eggCracked = makeSoundEvent("egg_cracked");
        event.getRegistry().registerAll(
                makeSoundEvent("ambient"),
                makeSoundEvent("idle"),
                makeSoundEvent("cave_idle"),
                makeSoundEvent("music_generic"),
                makeSoundEvent("music_crystal"),
                makeSoundEvent("rift_idle"),
                makeSoundEvent("rift_unstable"),
                makeSoundEvent("rift_ambient"),
                makeSoundEvent("rift_hurt"),
                makeSoundEvent("rift_death"),
                makeSoundEvent("bladeshroom_cap_shoot"),
                makeSoundEvent("bladeshroom_cap_hit"),
                mudDig,
                mudStep,
                makeSoundEvent("nightstag_ambient"),
                makeSoundEvent("nightstag_hurt"),
                makeSoundEvent("nightstag_death"),
                makeSoundEvent("crystal_bug_flying"),
                makeSoundEvent("crystal_bug_hurt"),
                makeSoundEvent("crystal_bug_death"),
                makeSoundEvent("nova_death"),
                makeSoundEvent("nova_hurt"),
                makeSoundEvent("nova_idle"),
                eggCracked
        );
        MUD = new SoundType(1f, 1f, mudDig, mudStep, mudDig, mudDig, mudStep);
        PILE_OF_EGGS = new SoundType(1f, 1f, eggCracked, SoundEvents.BLOCK_STONE_STEP, SoundEvents.BLOCK_STONE_PLACE, SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_FALL);
    }

    private static SoundEvent makeSoundEvent(String name) {
        SoundEvent sound = new SoundEvent(new ResourceLocation(MODID, name));
        return sound.setRegistryName(sound.getName());
    }
}
