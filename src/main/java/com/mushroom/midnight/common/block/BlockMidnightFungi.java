package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.function.Supplier;

public class BlockMidnightFungi extends BlockMidnightPlant implements IGrowable {
    private final Supplier<Block> parentSupplier;

    public BlockMidnightFungi(Supplier<Block> parentSupplier) {
        super(true);
        this.parentSupplier = parentSupplier;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return true;
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return state.isSideSolid(world, pos, EnumFacing.UP);
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return true;
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
        if (parentSupplier.get().canPlaceBlockAt(world, pos)) {
            ((BlockMidnightDoubleFungi)parentSupplier.get()).placeAt(world, pos, 2);
        }
    }
}
