package com.mushroom.midnight.common.entity.creature;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityNightStag extends AbstractHorse {
    public EntityNightStag(World world) {
        super(world);
        setSize(0.9f, 1.87f);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }
}
