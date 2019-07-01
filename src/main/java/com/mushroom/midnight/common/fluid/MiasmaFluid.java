package com.mushroom.midnight.common.fluid;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightFluids;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.world.IWorldReader;

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

    @Override
    public boolean isEquivalentTo(Fluid fluid) {
        return fluid.isIn(MidnightTags.Fluids.MIASMA);
    }

    @Override
    public int getTickRate(IWorldReader world) {
        return 20;
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
