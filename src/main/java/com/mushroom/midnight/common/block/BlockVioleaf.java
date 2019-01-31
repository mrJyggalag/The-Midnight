package com.mushroom.midnight.common.block;

import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class BlockVioleaf extends BlockMidnightPlant implements IGrowable {
    public static final PropertyBool IS_GROWN = PropertyBool.create("is_grown");

    public BlockVioleaf() {
        super(false);
        setDefaultState(blockState.getBaseState().withProperty(IS_GROWN, true));
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (world != null && !world.isRemote && state.getValue(IS_GROWN) && entity instanceof EntityPlayer && entity.ticksExisted % 20 == 0) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.isPotionActive(MobEffects.NAUSEA)) {
                player.removePotionEffect(MobEffects.NAUSEA);
                world.setBlockState(pos, state.withProperty(IS_GROWN, false), 2);
                world.playSound(null, pos, SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, SoundCategory.BLOCKS, 1f, world.rand.nextFloat() * 0.4f + 0.8f);
                Vec3d offset = getOffset(state, world, pos);
                double posX = (double) pos.getX() + 0.5d + offset.x;
                double posY = (double)pos.getY() + 0.3d;
                double posZ = (double) pos.getZ() + 0.5d + offset.z;
                createCloud(world, posX, posY, posZ);
            }
        }
    }

    private static void createCloud(World world, double posX, double posY, double posZ) {
        EntityAreaEffectCloud entity = new EntityAreaEffectCloud(world, posX, posY, posZ);
        entity.setRadius(1.5f);
        entity.setDuration(5);
        entity.setRadiusPerTick(-entity.getRadius() / (float) entity.getDuration());
        entity.setColor(0xA041C3);
        entity.setPotion(PotionTypes.EMPTY);
        entity.setParticle(EnumParticleTypes.DRAGON_BREATH);
        world.spawnEntity(entity);
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
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (canGrow(world, pos, state, world.isRemote) && ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(5) == 0)) {
            grow(world, rand, pos, state);
            ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
        }
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
