package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class CrystalFlowersFeature extends FlowersFeature {
    public CrystalFlowersFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public BlockState getRandomFlower(Random random, BlockPos pos) {
        return MidnightBlocks.CRYSTAL_FLOWER.getDefaultState();
    }
}
