package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLumenBud extends BlockMidnightPlant implements IGrowable {
    public BlockLumenBud() {
        super(true);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        if (world.isAirBlock(pos.up())) {
            world.setBlockState(pos, ModBlocks.DOUBLE_LUMEN_BUD.getDefaultState().withProperty(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.LOWER), 2);
            world.setBlockState(pos.up(), ModBlocks.DOUBLE_LUMEN_BUD.getDefaultState().withProperty(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.UPPER), 2);
        }
    }
}
