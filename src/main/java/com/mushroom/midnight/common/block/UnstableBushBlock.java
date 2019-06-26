package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class UnstableBushBlock extends MidnightPlantBlock implements IGrowable {
    public static final int MAX_STAGE = 4;
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, MAX_STAGE);
    protected static final AxisAlignedBB[] BOUNDS = new AxisAlignedBB[] {
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.125d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.4375d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.625d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.8125d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 1d),
    };

    public UnstableBushBlock() {
        super(PlantBehaviorType.FLOWER, false);
        setDefaultState(blockState.getBaseState().withProperty(STAGE, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STAGE, Math.min(meta, MAX_STAGE));
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.get(STAGE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSilkHarvest() {
        return false;
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return MidnightItems.UNSTABLE_SEEDS;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(MidnightItems.UNSTABLE_SEEDS);
    }

    @Override
    public int quantityDropped(BlockState state, int fortune, Random random) {
        return state.get(STAGE) == 0 ? 1 : 0;
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
        if (state.get(STAGE) < MAX_STAGE) {
            world.setBlockState(pos, state.with(STAGE, Math.min(state.get(STAGE) + 1, MAX_STAGE)), 2);
        } else {
            Block block = rand.nextInt(3) != 0 ? MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED : (rand.nextInt(3) != 0 ? MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED : MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED);
            world.setBlockState(pos, block.getDefaultState(), 2);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
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
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        return BOUNDS[state.get(STAGE)];
    }
}
