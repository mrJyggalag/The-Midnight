package com.mushroom.midnight.client.sound;

import com.mushroom.midnight.client.IdleSoundController;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MidnightCaveSound extends TickableSound implements ITickableSound {
    private static final Minecraft MC = Minecraft.getInstance();

    public MidnightCaveSound() {
        super(MidnightSounds.CAVE_IDLE, SoundCategory.AMBIENT);
        this.attenuationType = AttenuationType.NONE;
        this.repeat = true;
    }

    @Override
    public boolean isDonePlaying() {
        return MC.player == null || !Helper.isMidnightDimension(MC.player.world);
    }

    @Override
    public void tick() {
        this.volume = Math.max(IdleSoundController.CAVE_ANIMATION.getScale(), 0.01F);
    }
}
