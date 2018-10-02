package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModDimensions;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Side.CLIENT)
public class SoundEchoHandler {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final ThreadLocal<Boolean> ECHO_ACTIVE = ThreadLocal.withInitial(() -> false);

    private static final List<Echo> ECHOES = new ArrayList<>();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START && !MC.isGamePaused()) {
            ECHO_ACTIVE.set(true);
            try {
                ECHOES.removeIf(echo -> echo.update(MC.world));
            } finally {
                ECHO_ACTIVE.set(false);
            }
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if (!shouldEcho(MC.world) || ECHO_ACTIVE.get()) {
            return;
        }
        ISound sound = event.getSound();
        if (sound instanceof PositionedSoundRecord) {
            PositionedSoundRecord positionedSound = (PositionedSoundRecord) sound;
            if (!positionedSound.canRepeat()) {
                ECHOES.add(Echo.of(MC.world, positionedSound));
            }
        }
    }

    private static boolean shouldEcho(World world) {
        return world != null && world.provider.getDimensionType() == ModDimensions.MIDNIGHT;
    }

    private static class Echo {
        private static final Random RANDOM = new Random();

        private static final int MIN_ECHO_COUNT = 5;
        private static final int MAX_ECHO_COUNT = 12;

        private static final int MAX_ECHO_DURATION = 20;

        private final SoundData soundData;
        private final LongSet echoTimes;

        private float volume;

        private Echo(long startTime, SoundData soundData) {
            this.soundData = soundData;
            this.volume = soundData.volume * 0.8F;
            this.echoTimes = this.prepareEcho(startTime);
        }

        static Echo of(World world, PositionedSoundRecord sound) {
            long startTime = world.getTotalWorldTime();
            return new Echo(startTime, new SoundData(sound));
        }

        private LongSet prepareEcho(long startTime) {
            int count = RANDOM.nextInt(MAX_ECHO_COUNT - MIN_ECHO_COUNT) + MIN_ECHO_COUNT;
            LongSet times = new LongOpenHashSet(count);

            while (times.size() < count) {
                times.add(startTime + 2 + RANDOM.nextInt(MAX_ECHO_DURATION - 2));
            }

            return times;
        }

        boolean update(World world) {
            if (!shouldEcho(world)) {
                return true;
            }

            long worldTime = world.getTotalWorldTime();
            this.echoTimes.removeIf(time -> {
                if (worldTime > time) {
                    this.playEcho();
                    return true;
                }
                return false;
            });

            return false;
        }

        private void playEcho() {
            float volume = this.volume * (RANDOM.nextFloat() * 0.5F + 0.5F);
            float pitch = this.soundData.pitch * (RANDOM.nextFloat() * 0.5F + 0.9F);

            PositionedSoundRecord sound = new PositionedSoundRecord(
                    this.soundData.soundId, this.soundData.category,
                    volume, pitch,
                    false, 0,
                    this.soundData.attenuationType,
                    this.soundData.x, this.soundData.y, this.soundData.z
            );
            MC.getSoundHandler().playSound(sound);

            this.volume *= 0.7F;
        }
    }

    private static class SoundData {
        private final ResourceLocation soundId;
        private final SoundCategory category;
        private final ISound.AttenuationType attenuationType;

        private final float volume;
        private final float pitch;

        private final float x;
        private final float y;
        private final float z;

        private SoundData(PositionedSoundRecord sound) {
            this.soundId = sound.getSoundLocation();
            this.category = sound.getCategory();
            this.attenuationType = sound.getAttenuationType();
            this.x = sound.getXPosF();
            this.y = sound.getYPosF();
            this.z = sound.getZPosF();
            this.volume = sound.volume;
            this.pitch = sound.pitch;
        }
    }
}
