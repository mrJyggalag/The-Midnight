package com.mushroom.midnight.common.world.feature.config;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import com.mushroom.midnight.common.world.generator.WorldGenMoltenCrater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Consumer;

public class DragonNestPlacementConfig implements IPlacementConfig {
    private static final int CRATER_RANGE_CHUNKS = 3;

    private final int count;
    private final int scatterCount;

    public DragonNestPlacementConfig(int count, int scatterCount) {
        this.count = count;
        this.scatterCount = scatterCount;
    }

    @Override
    public void apply(World world, SurfacePlacementLevel placementLevel, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator) {
        if (!isNearCrater(world, random, chunkOrigin)) {
            return;
        }

        for (int i = 0; i < this.count; i++) {
            int offsetX = random.nextInt(16) + 8;
            int offsetZ = random.nextInt(16) + 8;
            BlockPos pos = chunkOrigin.add(offsetX, 0, offsetZ);
            int surfaceY = placementLevel.getSurfacePos(world, pos).getY();
            int offsetY = surfaceY + random.nextInt(16);
            this.applyScatter(world, random, pos.up(offsetY), generator);
        }
    }

    private void applyScatter(World world, Random random, BlockPos pos, Consumer<BlockPos> generator) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);

        for (int i = 0; i < this.scatterCount; i++) {
            int offsetX = random.nextInt(8) - random.nextInt(8);
            int offsetY = random.nextInt(8) - random.nextInt(8);
            int offsetZ = random.nextInt(8) - random.nextInt(8);

            mutablePos.setPos(pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ);

            IBlockState existingState = world.getBlockState(mutablePos);
            if (existingState.getBlock().isAir(existingState, world, mutablePos) && existingState.getBlock() != ModBlocks.MUSHROOM_INSIDE) {
                generator.accept(mutablePos.toImmutable());
            }
        }
    }

    private static boolean isNearCrater(World world, Random random, BlockPos chunkOrigin) {
        if (random.nextInt(10) != 0) {
            return false;
        }

        int chunkX = chunkOrigin.getX() >> 4;
        int chunkZ = chunkOrigin.getZ() >> 4;

        for (int deltaZ = -CRATER_RANGE_CHUNKS; deltaZ <= CRATER_RANGE_CHUNKS; deltaZ++) {
            for (int deltaX = -CRATER_RANGE_CHUNKS; deltaX <= CRATER_RANGE_CHUNKS; deltaX++) {
                if (WorldGenMoltenCrater.isCraterSource(world, chunkX + deltaX, chunkZ + deltaZ)) {
                    return true;
                }
            }
        }

        return false;
    }
}
