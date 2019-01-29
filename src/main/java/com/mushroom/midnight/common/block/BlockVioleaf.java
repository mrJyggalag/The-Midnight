package com.mushroom.midnight.common.block;

import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockVioleaf extends BlockMidnightPlant implements IGrowable {
    public static final PropertyBool IS_GROWN = PropertyBool.create("is_grown");

    public BlockVioleaf() {
        super(false);
        setDefaultState(blockState.getBaseState().withProperty(IS_GROWN, false));
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (world != null && !world.isRemote && state.getValue(IS_GROWN) && entity instanceof EntityPlayer && entity.ticksExisted % 20 == 0) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.isPotionActive(MobEffects.NAUSEA)) {
                player.removePotionEffect(MobEffects.NAUSEA);
                world.setBlockState(pos, state.withProperty(IS_GROWN, false), 2);
                world.playSound(null, pos, SoundEvents.BLOCK_NOTE_HARP, SoundCategory.BLOCKS, 0.5f, 0.5f);
            }
        }
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return !state.getValue(IS_GROWN);
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return !state.getValue(IS_GROWN);
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        world.setBlockState(pos, state.withProperty(IS_GROWN, true), 2);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, IS_GROWN);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(IS_GROWN, (meta & 1) == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(IS_GROWN) ? 1 : 0;
    }
}
