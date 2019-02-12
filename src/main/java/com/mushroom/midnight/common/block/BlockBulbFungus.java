package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class BlockBulbFungus extends BlockMidnightPlant implements IGrowable {
    private final Supplier<WorldGenerator> genSupplier;

    public BlockBulbFungus(Supplier<WorldGenerator> genSupplier) {
        super(true);
        this.genSupplier = genSupplier;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.BULB_FUNGUS_HAND;
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
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return world.rand.nextFloat() < 0.75f;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        if (TerrainGen.saplingGrowTree(world, rand, pos)) {
            WorldGenerator generator = this.genSupplier.get();
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            if (!generator.generate(world, rand, pos)) {
                world.setBlockState(pos, state, 4);
            }
        }
    }
}
