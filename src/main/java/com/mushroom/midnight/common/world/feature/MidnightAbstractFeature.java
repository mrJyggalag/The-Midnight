package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class MidnightAbstractFeature extends WorldGenerator implements IMidnightFeature {
    public MidnightAbstractFeature() {
        super(false);
    }

    @Override
    public final boolean generate(World world, Random random, BlockPos origin) {
        return this.placeFeature(world, random, origin);
    }

    @Override
    protected void setBlockAndNotifyAdequately(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, 2 | 16);
    }
}
