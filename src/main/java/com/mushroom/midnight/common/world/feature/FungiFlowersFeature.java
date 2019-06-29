package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class FungiFlowersFeature extends FlowersFeature {
    private static final BlockState[] FUNGI = new BlockState[] {
            MidnightBlocks.NIGHTSHROOM.getDefaultState(),
            MidnightBlocks.DEWSHROOM.getDefaultState(),
            MidnightBlocks.VIRIDSHROOM.getDefaultState()
    };

    public FungiFlowersFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public BlockState getRandomFlower(Random random, BlockPos pos) {
        return FUNGI[random.nextInt(FUNGI.length)];
    }
}
