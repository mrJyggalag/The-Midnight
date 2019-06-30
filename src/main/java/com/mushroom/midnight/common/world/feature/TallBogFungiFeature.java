package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class TallBogFungiFeature extends TallFungiFeature {
    private static final BlockState[] FUNGI = new BlockState[] {
            MidnightBlocks.DOUBLE_NIGHTSHROOM.getDefaultState(),
            MidnightBlocks.DOUBLE_DEWSHROOM.getDefaultState(),
            MidnightBlocks.DOUBLE_VIRIDSHROOM.getDefaultState(),
            MidnightBlocks.DOUBLE_BOGSHROOM.getDefaultState()
    };

    public TallBogFungiFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    protected BlockState getRandomFungi(Random random) {
        return FUNGI[random.nextInt(FUNGI.length)];
    }
}
