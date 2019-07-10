package com.mushroom.midnight.common.fluid;

import com.mushroom.midnight.client.particle.FurnaceFlameParticle;
import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightFluids;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightTags;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class MiasmaFluid extends LavaFluid {
    @Override
    public Fluid getFlowingFluid() {
        return MidnightFluids.FLOWING_MIASMA;
    }

    @Override
    public Fluid getStillFluid() {
        return MidnightFluids.MIASMA;
    }

    @Override
    public Item getFilledBucket() {
        return MidnightItems.MIASMA_BUCKET;
    }

    @Override
    public BlockState getBlockState(IFluidState state) {
        return MidnightBlocks.MIASMA.getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public void animateTick(World world, BlockPos pos, IFluidState state, Random random) {
        BlockPos blockpos = pos.up();
        if (world.getBlockState(blockpos).isAir() && !world.getBlockState(blockpos).isOpaqueCube(world, blockpos)) {
            if (random.nextInt(100) == 0) {
                double d0 = (double) ((float) pos.getX() + random.nextFloat());
                double d1 = (double) (pos.getY() + 1);
                double d2 = (double) ((float) pos.getZ() + random.nextFloat());
                FurnaceFlameParticle particle = new FurnaceFlameParticle(world, d0, d1, d2, 0d, 0d, 0d);
                particle.multipleParticleScaleBy(0.6f);
                Minecraft.getInstance().particles.addEffect(particle);
                world.playSound(d0, d1, d2, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
            }
            if (random.nextInt(200) == 0) {
                world.playSound((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
            }
        }
    }

    @Override
    public boolean isEquivalentTo(Fluid fluid) {
        return fluid.isIn(MidnightTags.Fluids.MIASMA);
    }

    @Override
    public int getTickRate(IWorldReader world) {
        return 20;
    }

    @Override
    protected void flowInto(IWorld world, BlockPos intoPos, BlockState intoBlock, Direction direction, IFluidState fluidState) {
        if (direction == Direction.DOWN) {
            IFluidState intoFluid = world.getFluidState(intoPos);
            if (intoFluid.isTagged(MidnightTags.Fluids.DARK_WATER) || intoFluid.isTagged(FluidTags.WATER)) {
                this.mixInto(world, intoPos, MidnightBlocks.NIGHTSTONE.getDefaultState());
                return;
            } else if (intoFluid.isTagged(FluidTags.LAVA)) {
                this.mixInto(world, intoPos, MidnightBlocks.MIASMA_SURFACE.getDefaultState());
                return;
            }
        }
        if (intoBlock.getBlock() instanceof ILiquidContainer) {
            ((ILiquidContainer)intoBlock.getBlock()).receiveFluid(world, intoPos, intoBlock, fluidState);
        } else {
            if (!intoBlock.isAir()) {
                this.beforeReplacingBlock(world, intoPos, intoBlock);
            }
            world.setBlockState(intoPos, fluidState.getBlockState(), 3);
        }
    }

    private void mixInto(IWorld world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, Constants.BlockFlags.NOTIFY_NEIGHBORS | Constants.BlockFlags.NOTIFY_LISTENERS);
        world.playEvent(1501, pos, 0);
    }

    public static class Flowing extends MiasmaFluid {
        public Flowing() {
        }

        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> container) {
            super.fillStateContainer(container);
            container.add(LEVEL_1_8);
        }

        @Override
        public int getLevel(IFluidState state) {
            return state.get(LEVEL_1_8);
        }

        @Override
        public boolean isSource(IFluidState state) {
            return false;
        }
    }

    public static class Source extends MiasmaFluid {
        public Source() {
        }

        @Override
        public int getLevel(IFluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(IFluidState state) {
            return true;
        }
    }
}
