package com.mushroom.midnight.common.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class MidnightTeleporter implements ITeleporter {
    public static final MidnightTeleporter INSTANCE = new MidnightTeleporter();

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
    }
}
