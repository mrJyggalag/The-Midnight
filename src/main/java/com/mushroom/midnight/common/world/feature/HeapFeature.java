package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.feature.config.UniformCompositionConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class HeapFeature extends Feature<UniformCompositionConfig> {
    public HeapFeature(Function<Dynamic<?>, ? extends UniformCompositionConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos origin, UniformCompositionConfig config) {
        BlockState belowState = world.getBlockState(origin.down());
        Block belowBlock = belowState.getBlock();
        if (belowBlock.isLeaves(belowState, world, origin.down()) || belowBlock == MidnightBlocks.NIGHTSHROOM_HAT || belowBlock == MidnightBlocks.DEWSHROOM_HAT || belowBlock == MidnightBlocks.VIRIDSHROOM_HAT || belowBlock == MidnightBlocks.TRENCHSTONE) {
            return false;
        }

        int rangeXZ = 1, rangeY = 1;
        BlockPos min = origin.add(-rangeXZ, -rangeY, -rangeXZ);
        BlockPos max = origin.add(rangeXZ, rangeY, rangeXZ);
        BlockPos.getAllInBox(min, max).forEach(pos -> {
            if ((pos.getX() == origin.getX() && pos.getZ() == origin.getZ()) || random.nextFloat() < 0.5f) {
                setBlockAndNotifyAdequately(world, pos, state);
            }
        });

        return true;
    }
}
