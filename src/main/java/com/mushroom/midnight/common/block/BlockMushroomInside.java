package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMushroomInside extends BlockAir {
    public BlockMushroomInside() {
        this.setLightLevel(1.0F);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        int neighborCount = this.countHatNeighbors(world, pos);
        if (neighborCount <= 0) {
            world.setBlockToAir(pos);
        }
    }

    private int countHatNeighbors(World world, BlockPos pos) {
        int neighborCount = 0;
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (world.getBlockState(pos.offset(facing)).getBlock() instanceof BlockMidnightFungiHat) {
                neighborCount++;
            }
        }
        return neighborCount;
    }
}
