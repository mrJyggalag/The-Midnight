package com.mushroom.midnight.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;

public abstract class BlockMixableFluid extends BlockFluidClassic {
    public BlockMixableFluid(Fluid fluid, Material material) {
        super(fluid, material);
    }

    @Nullable
    protected abstract IBlockState getMixState(IBlockState otherState);

    @Override
    protected void flowIntoBlock(World world, BlockPos pos, int meta) {
        if (this.tryMix(world, pos)) {
            return;
        }
        super.flowIntoBlock(world, pos, meta);
        this.tryMix(world, pos.down());
    }

    protected boolean tryMix(World world, BlockPos pos) {
        IBlockState currentState = world.getBlockState(pos);
        IBlockState mixState = this.getMixState(currentState);
        if (mixState != null) {
            world.setBlockState(pos, mixState);
            this.spawnMixEffects(world, pos, currentState);
            return true;
        }
        return false;
    }

    protected void spawnMixEffects(World world, BlockPos pos, IBlockState currentState) {
        SoundEvent mixSound = this.getMixSound(currentState);
        float pitch = 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F;
        world.playSound(null, pos, mixSound, SoundCategory.BLOCKS, 0.5F, pitch);

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        for (int i = 0; i < 8; ++i) {
            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x + Math.random(), y + 1.2, z + Math.random(), 0.0, 0.0, 0.0);
        }
    }

    protected SoundEvent getMixSound(IBlockState state) {
        return SoundEvents.BLOCK_LAVA_EXTINGUISH;
    }
}
