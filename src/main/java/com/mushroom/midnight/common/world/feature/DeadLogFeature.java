package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.util.WorldUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class DeadLogFeature extends Feature<NoFeatureConfig> {
    private static final Direction[] HORIZONTALS = new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

    private static final BlockState LOG = MidnightBlocks.DEAD_WOOD_LOG.getDefaultState();

    public DeadLogFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    private static boolean canBeReplaced(IWorld world, BlockPos pos) {
        return world.hasBlockState(pos, state -> state.canBeReplacedByLogs(world, pos));
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos origin, NoFeatureConfig config) {
        Direction direction = HORIZONTALS[rand.nextInt(HORIZONTALS.length)];
        Direction.Axis axis = direction.getAxis() == Direction.Axis.X ? Direction.Axis.X : Direction.Axis.Z;
        Direction.Axis perpendicular = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;

        int length = rand.nextInt(4) + 3;
        int halfLength = length / 2;

        BlockPos basePos = origin.offset(direction, -halfLength);
        BlockPos endPos = origin.offset(direction, length - halfLength);

        if (!this.checkValid(world, basePos, endPos)) {
            return false;
        }

        for (BlockPos pos : BlockPos.getAllInBoxMutable(basePos, endPos)) {
            this.setBlockState(world, pos, LOG.with(LogBlock.AXIS, axis));
        }

        int extrusionCount = rand.nextInt(3);
        for (int i = 0; i < extrusionCount; i++) {
            BlockPos intermediate = basePos.offset(direction, rand.nextInt(length));
            Direction side = rand.nextBoolean() ? direction.rotateY() : direction.rotateYCCW();
            this.setBlockState(world, intermediate.offset(side), LOG.with(LogBlock.AXIS, perpendicular));
        }

        return true;
    }

    private boolean checkValid(IWorld world, BlockPos basePos, BlockPos endPos) {
        BlockPos minPos = WorldUtil.min(basePos, endPos).add(-1, 0, -1);
        BlockPos maxPos = WorldUtil.max(basePos, endPos).add(1, 1, 1);

        for (BlockPos pos : BlockPos.getAllInBoxMutable(minPos, maxPos)) {
            if (!canBeReplaced(world, pos)) {
                return false;
            }
        }

        for (BlockPos pos : BlockPos.getAllInBoxMutable(basePos, endPos)) {
            BlockPos.MutableBlockPos mutablePos = (BlockPos.MutableBlockPos) pos;
            mutablePos.move(Direction.DOWN);
            BlockState groundState = world.getBlockState(mutablePos);
            if (!groundState.isSolid()) {
                return false;
            }
        }

        return true;
    }
}
