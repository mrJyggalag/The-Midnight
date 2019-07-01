package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightCriterion;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

@SuppressWarnings("deprecation")
public class VioleafBlock extends MidnightPlantBlock implements IGrowable {
    private static final BooleanProperty IS_GROWN = BooleanProperty.create("is_grown");

    public VioleafBlock(Properties properties) {
        super(properties, false);
        setDefaultState(getStateContainer().getBaseState().with(IS_GROWN, true));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world != null && !world.isRemote && state.get(IS_GROWN) && entity instanceof PlayerEntity && entity.ticksExisted % 20 == 0) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.isPotionActive(Effects.NAUSEA)) {
                player.removePotionEffect(Effects.NAUSEA);
                MidnightCriterion.VIOLEAF.trigger((ServerPlayerEntity) player);
                world.setBlockState(pos, state.with(IS_GROWN, false), 2);
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
        AreaEffectCloudEntity entity = new AreaEffectCloudEntity(world, posX, posY, posZ);
        entity.setRadius(1.5f);
        entity.setDuration(5);
        entity.setRadiusPerTick(-entity.getRadius() / (float) entity.getDuration());
        entity.setColor(0xA041C3);
        entity.setPotion(Potions.EMPTY);
        entity.setParticleData(ParticleTypes.DRAGON_BREATH);
        world.addEntity(entity);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !state.get(IS_GROWN);
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return !state.get(IS_GROWN);
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(IS_GROWN, true), 2);
    }

    @Override
    public void randomTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (canGrow(world, pos, state, world.isRemote) && ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(5) == 0)) {
            grow(world, rand, pos, state);
            ForgeHooks.onCropsGrowPost(world, pos, state);
        }
    }
}
