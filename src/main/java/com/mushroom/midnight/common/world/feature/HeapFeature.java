package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class HeapFeature extends MidnightAbstractFeature {
    private final IBlockState state;
    private int rangeXZ = 1, rangeY = 1;

    public HeapFeature(IBlockState state) {
        this.state = state;
    }

    @Override
    public boolean placeFeature(World world, Random random, BlockPos origin) {
        for (BlockPos pos : BlockPos.getAllInBox(origin.add(-rangeXZ, -rangeY, -rangeXZ), origin.add(rangeXZ, rangeY, rangeXZ))) {
            if (random.nextFloat() < 0.35f) {
                setBlockAndNotifyAdequately(world, pos, state);
            }
        }
        return true;
    }

    public HeapFeature withRange(int rangeXZ, int rangeY) {
        this.rangeXZ = rangeXZ;
        this.rangeY = rangeY;
        return this;
    }
}
