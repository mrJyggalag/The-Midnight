package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BogweedBlock extends MidnightPlantBlock {
    public BogweedBlock(Properties properties) {
        super(properties, true);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getBlock() == MidnightBlocks.DECEITFUL_PEAT;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return isValidGround(worldIn.getBlockState(pos.down()), worldIn, pos) && super.canPlaceBlockAt(worldIn, pos);
    }

    @Override
    public boolean canGeneratePlant(World world, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.down()).getBlock() == MidnightBlocks.DECEITFUL_PEAT;
    }
}
