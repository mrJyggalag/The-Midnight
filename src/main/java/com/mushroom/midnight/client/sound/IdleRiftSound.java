package com.mushroom.midnight.client.sound;

import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;

public class IdleRiftSound extends TickableSound {
    private final RiftEntity rift;

    public IdleRiftSound(RiftEntity rift) {
        super(MidnightSounds.RIFT_IDLE, SoundCategory.AMBIENT);
        this.rift = rift;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    @Override
    public void tick() {
        if (!this.rift.isAlive()) {
            this.donePlaying = true;
            return;
        }

        this.x = (float) this.rift.posX;
        this.y = (float) this.rift.posY;
        this.z = (float) this.rift.posZ;
    }
}
