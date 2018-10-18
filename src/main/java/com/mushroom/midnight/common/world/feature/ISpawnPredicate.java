package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISpawnPredicate {
    boolean canSpawn(World world, BlockPos pos, IBlockState state);
}
