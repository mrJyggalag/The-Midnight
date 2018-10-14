package com.mushroom.midnight.client.sound;

import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.registry.ModSounds;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;

public class IdleRiftSound extends MovingSound {
    private final EntityRift rift;

    public IdleRiftSound(EntityRift rift) {
        super(ModSounds.MIDNIGHT_RIFT_IDLE, SoundCategory.AMBIENT);
        this.rift = rift;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    @Override
    public void update() {
        if (!this.rift.isEntityAlive()) {
            this.donePlaying = true;
            return;
        }

        this.xPosF = (float) this.rift.posX;
        this.yPosF = (float) this.rift.posY;
        this.zPosF = (float) this.rift.posZ;
    }
}
