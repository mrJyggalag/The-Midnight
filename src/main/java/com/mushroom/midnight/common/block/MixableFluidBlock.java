package com.mushroom.midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;

public abstract class MixableFluidBlock extends BlockFluidClassic {
    public MixableFluidBlock(Fluid fluid, Material material) {
        super(fluid, material);
    }

    @Nullable
    protected abstract BlockState getMixState(BlockState otherState);

    @Override
    protected void flowIntoBlock(World world, BlockPos pos, int meta) {
        if (this.tryMix(world, pos)) {
            return;
        }
        super.flowIntoBlock(world, pos, meta);
        this.tryMix(world, pos.down());
    }

    protected boolean tryMix(World world, BlockPos pos) {
        BlockState currentState = world.getBlockState(pos);
        BlockState mixState = this.getMixState(currentState);
        if (mixState != null) {
            world.setBlockState(pos, mixState);
            this.spawnMixEffects(world, pos, currentState);
            return true;
        }
        return false;
    }

    protected void spawnMixEffects(World world, BlockPos pos, BlockState currentState) {
        SoundEvent mixSound = this.getMixSound(currentState);
        float pitch = 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F;
        world.playSound(null, pos, mixSound, SoundCategory.BLOCKS, 0.5F, pitch);

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        for (int i = 0; i < 8; ++i) {
            world.addParticle(EnumParticleTypes.SMOKE_LARGE, x + Math.random(), y + 1.2, z + Math.random(), 0.0, 0.0, 0.0);
        }
    }

    protected SoundEvent getMixSound(BlockState state) {
        return SoundEvents.BLOCK_LAVA_EXTINGUISH;
    }
}
