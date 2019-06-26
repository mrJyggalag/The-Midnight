package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BogweedBlock extends MidnightPlantBlock {
    public BogweedBlock() {
        super(true);
    }

    @Override
    protected boolean canSustainBush(BlockState state) {
        return state.getBlock() == MidnightBlocks.DECEITFUL_PEAT;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return canSustainBush(worldIn.getBlockState(pos.down())) && super.canPlaceBlockAt(worldIn, pos);
    }

    @Override
    public boolean canGeneratePlant(World world, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.down()).getBlock() == MidnightBlocks.DECEITFUL_PEAT;
    }
}
