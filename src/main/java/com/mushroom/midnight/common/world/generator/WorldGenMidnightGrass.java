package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenMidnightGrass extends WorldGenerator {
    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);

        IBlockState state;
        while (mutablePos.getY() > 0) {
            state = world.getBlockState(mutablePos);
            if (state.getBlock().isAir(state, world, mutablePos) || state.getBlock().isLeaves(state, world, mutablePos)) {
                break;
            }
            mutablePos.move(EnumFacing.DOWN);
        }

        pos = mutablePos.toImmutable();

        IBlockState grassState = ModBlocks.TALL_MIDNIGHT_GRASS.getDefaultState();
        BlockBush grassBlock = (BlockBush) grassState.getBlock();

        for (int i = 0; i < 128; ++i) {
            int offsetX = rand.nextInt(8) - rand.nextInt(8);
            int offsetY = rand.nextInt(4) - rand.nextInt(4);
            int offsetZ = rand.nextInt(8) - rand.nextInt(8);

            mutablePos.setPos(pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ);

            if (world.isAirBlock(mutablePos) && grassBlock.canBlockStay(world, mutablePos, grassState)) {
                world.setBlockState(mutablePos, grassState, 2);
            }
        }

        return true;
    }
}
