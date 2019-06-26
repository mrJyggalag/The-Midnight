package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MushroomInsideBlock extends BlockAir {
    public MushroomInsideBlock() {
        this.setLightLevel(1.0F);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        int neighborCount = this.countHatNeighbors(world, pos);
        if (neighborCount <= 0) {
            world.setBlockToAir(pos);
        }
    }

    private int countHatNeighbors(World world, BlockPos pos) {
        int neighborCount = 0;
        for (Direction facing : Direction.VALUES) {
            if (world.getBlockState(pos.offset(facing)).getBlock() instanceof MidnightFungiHatBlock) {
                neighborCount++;
            }
        }
        return neighborCount;
    }
}
