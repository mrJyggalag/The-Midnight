package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.block.DeceitfulMossBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class MossFeature extends Feature<NoFeatureConfig> {
    private static final BlockState BLOCK = MidnightBlocks.DECEITFUL_MOSS.getDefaultState();

    public MossFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos origin, NoFeatureConfig config) {
        Direction direction = Direction.random(rand);
        BlockState state = BLOCK.with(DeceitfulMossBlock.FACING, direction);

        if (state.isValidPosition(world, origin)) {
            this.setBlockState(world, origin, state);
            return true;
        }

        return false;
    }
}
