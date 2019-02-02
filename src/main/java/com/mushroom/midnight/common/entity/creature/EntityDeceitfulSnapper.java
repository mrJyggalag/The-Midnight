package com.mushroom.midnight.common.entity.creature;

import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.world.World;

public class EntityDeceitfulSnapper extends EntityWaterMob {
    public EntityDeceitfulSnapper(World world) {
        super(world);
        setSize(0.6f, 0.6f);
        experienceValue = 3;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }
}
