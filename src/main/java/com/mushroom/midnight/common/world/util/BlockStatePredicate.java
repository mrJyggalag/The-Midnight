package com.mushroom.midnight.common.world.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public interface BlockStatePredicate {
    boolean test(IWorld world, BlockPos pos);
}
