package com.mushroom.midnight.common.world.generator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenMidnightPlant extends WorldGenerator {
    private final IBlockState state;
    private final SpawnPredicate predicate;
    private final int spawnCount;

    public WorldGenMidnightPlant(IBlockState state, SpawnPredicate predicate, int spawnCount) {
        this.state = state;
        this.predicate = predicate;
        this.spawnCount = spawnCount;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);

        IBlockState state;
        while (mutablePos.getY() > 0) {
            state = world.getBlockState(mutablePos);
            if (!state.getBlock().isAir(state, world, mutablePos) && !state.getBlock().isLeaves(state, world, mutablePos)) {
                break;
            }
            mutablePos.move(EnumFacing.DOWN);
        }

        pos = mutablePos.toImmutable();

        for (int i = 0; i < this.spawnCount; ++i) {
            int offsetX = rand.nextInt(8) - rand.nextInt(8);
            int offsetY = rand.nextInt(4) - rand.nextInt(4);
            int offsetZ = rand.nextInt(8) - rand.nextInt(8);

            mutablePos.setPos(pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ);

            if (world.isAirBlock(mutablePos) && this.predicate.canSpawn(world, mutablePos, this.state)) {
                world.setBlockState(mutablePos, this.state, 2);
            }
        }

        return true;
    }
}
