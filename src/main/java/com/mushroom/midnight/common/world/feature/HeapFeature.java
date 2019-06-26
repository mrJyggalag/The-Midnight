package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class HeapFeature extends MidnightAbstractFeature {
    private final BlockState state;

    public HeapFeature(BlockState state) {
        this.state = state;
    }

    @Override
    public boolean placeFeature(World world, Random random, BlockPos origin) {
        BlockState belowState = world.getBlockState(origin.down());
        Block belowBlock = belowState.getBlock();
        if (belowBlock.isLeaves(belowState, world, origin.down()) || belowBlock == MidnightBlocks.NIGHTSHROOM_HAT || belowBlock == MidnightBlocks.DEWSHROOM_HAT || belowBlock == MidnightBlocks.VIRIDSHROOM_HAT || belowBlock == MidnightBlocks.TRENCHSTONE) {
            return false;
        }
        int rangeXZ = 1, rangeY = 1;
        for (BlockPos pos : BlockPos.getAllInBox(origin.add(-rangeXZ, -rangeY, -rangeXZ), origin.add(rangeXZ, rangeY, rangeXZ))) {
            if ((pos.getX() == origin.getX() && pos.getZ() == origin.getZ()) || random.nextFloat() < 0.5f) {
                setBlockAndNotifyAdequately(world, pos, state);
            }
        }
        return true;
    }
}
