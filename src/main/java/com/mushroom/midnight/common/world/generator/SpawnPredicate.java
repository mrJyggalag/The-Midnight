package com.mushroom.midnight.common.world.generator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface SpawnPredicate {
    boolean canSpawn(World world, BlockPos pos, IBlockState state);
}
