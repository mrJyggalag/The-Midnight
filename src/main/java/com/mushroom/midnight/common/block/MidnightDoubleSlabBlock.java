package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.function.Supplier;

public class MidnightDoubleSlabBlock extends MidnightSlabBlock {
    private final Supplier<Block> singleSlabSupplier;

    public MidnightDoubleSlabBlock(Supplier<Block> singleSlabSupplier) {
        super(() -> singleSlabSupplier.get().getDefaultState());
        this.singleSlabSupplier = singleSlabSupplier;
        this.setCreativeTab(null);
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    protected BlockState buildDefaultState(BlockState baseState) {
        return baseState;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(this.singleSlabSupplier.get());
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockAccess world, BlockPos pos, Direction direction, IPlantable plantable) {
        return ((MidnightSlabBlock)singleSlabSupplier.get()).getParentstate().getBlock().canSustainPlant(state, world, pos, direction, plantable);
    }
}
