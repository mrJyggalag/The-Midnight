package com.mushroom.midnight.common.world.feature.config;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Consumer;

public interface IPlacementConfig {
    void apply(World world, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator);
}
