package com.mushroom.midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class MidnightFluidBlock extends FlowingFluidBlock {
    private final boolean isBurning;

    public MidnightFluidBlock(FlowingFluid fluid, boolean isBurning, Properties properties) {
        super(fluid, properties);
        this.isBurning = isBurning;
    }

    @Override
    public boolean isBurning(BlockState state, IBlockReader world, BlockPos pos) {
        return isBurning;
    }
}
