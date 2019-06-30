package com.mushroom.midnight.common.block;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MushroomInsideBlock extends AirBlock {
    public MushroomInsideBlock(Properties properties) {
        super(properties.lightValue(14));
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
        int neighborCount = this.countHatNeighbors(worldIn, pos);
        if (neighborCount <= 0) {
            worldIn.setBlockToAir(pos);
        }
    }

    private int countHatNeighbors(World world, BlockPos pos) {
        int neighborCount = 0;
        for (Direction facing : Direction.values()) {
            if (world.getBlockState(pos.offset(facing)).getBlock() instanceof MidnightFungiHatBlock) {
                neighborCount++;
            }
        }
        return neighborCount;
    }
}
