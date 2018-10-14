package com.mushroom.midnight.common.world.generator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenMidnightPlant extends WorldGenNoisySurface {
    private final IBlockState state;
    private final SpawnPredicate predicate;

    public WorldGenMidnightPlant(IBlockState state, SpawnPredicate predicate, int spawnCount) {
        super(spawnCount);
        this.state = state;
        this.predicate = predicate;
    }

    @Override
    protected void trySpawn(World world, Random rand, BlockPos pos) {
        if (this.predicate.canSpawn(world, pos, this.state)) {
            world.setBlockState(pos, this.state, 2);
        }
    }
}
