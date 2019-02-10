package com.mushroom.midnight.common.world.feature.config;

import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Consumer;

public interface IPlacementConfig {
    void apply(World world, SurfacePlacementLevel placementLevel, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator);
}
