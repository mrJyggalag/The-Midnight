package com.mushroom.midnight.common.world.feature.config;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Consumer;

public class ScatterPlacementConfig implements IPlacementConfig {
    private final int count;
    private final int scatterCount;

    public ScatterPlacementConfig(int count, int scatterCount) {
        this.count = count;
        this.scatterCount = scatterCount;
    }

    @Override
    public void apply(World world, SurfacePlacementLevel placementLevel, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator) {
        for (int i = 0; i < this.count; i++) {
            int offsetX = random.nextInt(16) + 8;
            int offsetZ = random.nextInt(16) + 8;
            BlockPos pos = chunkOrigin.add(offsetX, 0, offsetZ);
            int maxY = placementLevel.getSurfacePos(pos).getY() + 32;
            if (maxY > 0) {
                this.applyScatter(world, random, pos.up(placementLevel.generateUpTo(random, maxY)), generator);
            }
        }
    }

    private void applyScatter(World world, Random random, BlockPos pos, Consumer<BlockPos> generator) {
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

        for (int i = 0; i < this.scatterCount; i++) {
            int offsetX = random.nextInt(8) - random.nextInt(8);
            int offsetY = random.nextInt(4) - random.nextInt(4);
            int offsetZ = random.nextInt(8) - random.nextInt(8);

            mutablePos.setPos(pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ);

            IBlockState existingState = world.getBlockState(mutablePos);
            if (existingState.getBlock().isAir(existingState, world, mutablePos) && existingState.getBlock() != ModBlocks.MUSHROOM_INSIDE) {
                generator.accept(mutablePos.toImmutable());
            }
        }
    }
}
