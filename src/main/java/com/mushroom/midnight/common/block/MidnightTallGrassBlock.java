package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MidnightTallGrassBlock extends MidnightPlantBlock implements IGrowable {
    public MidnightTallGrassBlock() {
        super(PlantBehaviorType.BUSH, false);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        if (MidnightBlocks.DOUBLE_MIDNIGHT_GRASS.canPlaceBlockAt(world, pos)) {
            world.setBlockState(pos, MidnightBlocks.DOUBLE_MIDNIGHT_GRASS.getDefaultState().withProperty(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.LOWER), 2);
            world.setBlockState(pos.up(), MidnightBlocks.DOUBLE_MIDNIGHT_GRASS.getDefaultState().withProperty(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.UPPER), 2);
        }
    }
}
