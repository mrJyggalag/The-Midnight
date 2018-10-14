package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenSmallFungi extends WorldGenNoisySurface {
    private static final IBlockState[] FUNGI_STATES = new IBlockState[] {
            ModBlocks.NIGHTSHROOM.getDefaultState(),
            ModBlocks.DEWSHROOM.getDefaultState(),
            ModBlocks.VIRIDSHROOM.getDefaultState()
    };

    public WorldGenSmallFungi(int spawnCount) {
        super(spawnCount);
    }

    @Override
    protected void trySpawn(World world, Random rand, BlockPos pos) {
        IBlockState state = FUNGI_STATES[rand.nextInt(FUNGI_STATES.length)];
        if (((BlockBush) ModBlocks.NIGHTSHROOM).canBlockStay(world, pos, state)) {
            world.setBlockState(pos, state, 2);
        }
    }
}
