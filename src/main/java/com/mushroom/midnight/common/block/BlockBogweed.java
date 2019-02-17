package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBogweed extends BlockMidnightPlant {
    public BlockBogweed() {
        super(true);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == ModBlocks.DECEITFUL_PEAT;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return canSustainBush(worldIn.getBlockState(pos.down())) && super.canPlaceBlockAt(worldIn, pos);
    }
}
