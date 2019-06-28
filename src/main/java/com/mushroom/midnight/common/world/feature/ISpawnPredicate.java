package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISpawnPredicate {
    boolean canSpawn(World world, BlockPos pos, BlockState state);
}
