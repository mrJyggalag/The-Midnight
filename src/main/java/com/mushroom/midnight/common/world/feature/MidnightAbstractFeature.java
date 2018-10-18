package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class MidnightAbstractFeature extends WorldGenerator implements IMidnightFeature {
    public MidnightAbstractFeature() {
        super(false);
    }

    @Override
    public abstract boolean generate(World world, Random rand, BlockPos origin);

    @Override
    protected void setBlockAndNotifyAdequately(World world, BlockPos pos, IBlockState state) {
        world.setBlockState(pos, state, 2 | 16);
    }
}
