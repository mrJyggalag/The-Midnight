package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FungiInsideBlock extends AirBlock {
    public FungiInsideBlock(Properties properties) {
        super(properties.lightValue(14));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        int neighborCount = this.countHatNeighbors(world, pos);
        if (neighborCount <= 0) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    private int countHatNeighbors(World world, BlockPos pos) {
        int neighborCount = 0;
        for (Direction facing : Direction.values()) {
            if (world.getBlockState(pos.offset(facing)).isIn(MidnightTags.Blocks.FUNGI_HATS)) {
                neighborCount++;
            }
        }
        return neighborCount;
    }
}
