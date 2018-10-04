package com.mushroom.midnight.client.sound;

import com.mushroom.midnight.common.registry.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LoopingMidnightSound extends PositionedSound implements ITickableSound {
    private static final Minecraft MC = Minecraft.getMinecraft();

    public LoopingMidnightSound(SoundEvent event, float volume) {
        super(event, SoundCategory.AMBIENT);
        this.attenuationType = AttenuationType.NONE;
        this.volume = volume;
        this.repeat = true;
    }

    @Override
    public boolean isDonePlaying() {
        return MC.player == null || MC.player.world.provider.getDimensionType() != ModDimensions.MIDNIGHT;
    }

    @Override
    public void update() {
    }
}
