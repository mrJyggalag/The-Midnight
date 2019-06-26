package com.mushroom.midnight.client.sound;

import com.mushroom.midnight.client.IdleSoundController;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MidnightIdleSound extends PositionedSound implements ITickableSound {
    private static final Minecraft MC = Minecraft.getInstance();

    public MidnightIdleSound() {
        super(MidnightSounds.IDLE, SoundCategory.AMBIENT);
        this.attenuationType = AttenuationType.NONE;
        this.repeat = true;
    }

    @Override
    public boolean isDonePlaying() {
        return MC.player == null || !Helper.isMidnightDimension(MC.player.world);
    }

    @Override
    public void update() {
        this.volume = Math.max(1.0F - IdleSoundController.CAVE_ANIMATION.getScale(), 0.01F);
    }
}
