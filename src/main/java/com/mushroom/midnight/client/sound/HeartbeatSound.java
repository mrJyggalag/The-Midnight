package com.mushroom.midnight.client.sound;

import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.registry.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HeartbeatSound extends PositionedSound implements ITickableSound {
    private static final Minecraft MC = Minecraft.getMinecraft();

    public HeartbeatSound() {
        super(ModSounds.MIDNIGHT_HEARTBEAT, SoundCategory.AMBIENT);
        this.attenuationType = AttenuationType.NONE;
        this.volume = 0.75F;
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
