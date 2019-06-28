package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BulbFungusBlock extends MidnightPlantBlock implements IGrowable {
    private final Supplier<WorldGenerator> genSupplier;

    public BulbFungusBlock(Supplier<WorldGenerator> genSupplier) {
        super(true);
        this.genSupplier = genSupplier;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected boolean canSilkHarvest() {
        return true;
    } // in loot table

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return MidnightItems.BULB_FUNGUS_HAND;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        NonNullList<ItemStack> list = NonNullList.create();
        list.add(new ItemStack(this));
        return list;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return world.rand.nextFloat() < 0.75f;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        if (TerrainGen.saplingGrowTree(world, rand, pos)) {
            WorldGenerator generator = this.genSupplier.get();
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            if (!generator.generate(world, rand, pos)) {
                world.setBlockState(pos, state, 4);
            }
        }
    }
}
