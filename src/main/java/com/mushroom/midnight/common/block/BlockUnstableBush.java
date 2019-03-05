package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class BlockUnstableBush extends BlockMidnightPlant implements IGrowable {
    public static final int MAX_STAGE = 4;
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, MAX_STAGE);
    protected static final AxisAlignedBB[] BOUNDS = new AxisAlignedBB[] {
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.125d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.4375d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.625d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.8125d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 1d),
    };

    public BlockUnstableBush() {
        super(PlantBehaviorType.FLOWER, false);
        setDefaultState(blockState.getBaseState().withProperty(STAGE, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STAGE, Math.min(meta, MAX_STAGE));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSilkHarvest() {
        return false;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ModItems.UNSTABLE_SEEDS);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return state.getValue(STAGE) == 0 ? 1 : 0;
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
        if (state.getValue(STAGE) < MAX_STAGE) {
            world.setBlockState(pos, state.withProperty(STAGE, Math.min(state.getValue(STAGE) + 1, MAX_STAGE)), 2);
        } else {
            Block block = rand.nextInt(3) != 0 ? ModBlocks.UNSTABLE_BUSH_BLUE_BLOOMED : (rand.nextInt(3) != 0 ? ModBlocks.UNSTABLE_BUSH_LIME_BLOOMED : ModBlocks.UNSTABLE_BUSH_GREEN_BLOOMED);
            world.setBlockState(pos, block.getDefaultState(), 2);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!canBlockStay(world, pos, state)) {
            dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        } else {
            if (ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(10) == 0)) {
                grow(world, rand, pos, state);
                ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return BOUNDS[state.getValue(STAGE)];
    }
}
