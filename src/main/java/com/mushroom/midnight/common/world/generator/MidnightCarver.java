package com.mushroom.midnight.common.world.generator;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public abstract class MidnightCarver<C extends ICarverConfig> extends WorldCarver<C> {
    protected MidnightCarver(Function<Dynamic<?>, ? extends C> deserialize, int maxHeight) {
        super(deserialize, maxHeight);
    }

    @Override
    protected boolean carveBlock(IChunk chunk, BitSet carvingMask, Random random, BlockPos.MutableBlockPos posHere, BlockPos.MutableBlockPos posAbove, BlockPos.MutableBlockPos posBelow, int p_222703_7_, int p_222703_8_, int p_222703_9_, int globalX, int globalZ, int x, int y, int z, AtomicBoolean foundSurface) {
        int index = x | z << 4 | y << 8;
        if (carvingMask.get(index)) {
            return false;
        }

        carvingMask.set(index);
        posHere.setPos(globalX, y, globalZ);

        BlockState state = chunk.getBlockState(posHere);
        BlockState stateAbove = chunk.getBlockState(posAbove.setPos(posHere).move(Direction.UP));
        if (!this.canCarveBlock(state, stateAbove)) {
            return false;
        }

        if (y > 10) {
            chunk.setBlockState(posHere, CAVE_AIR, false);
        } else {
            chunk.setBlockState(posHere, MidnightBlocks.MIASMA.getDefaultState(), false);
        }

        return true;
    }
}
