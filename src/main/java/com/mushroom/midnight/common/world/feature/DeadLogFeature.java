package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.util.WorldUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DeadLogFeature extends MidnightNaturalFeature {
    private final BlockState log;

    public DeadLogFeature(BlockState log) {
        this.log = log;
    }

    public DeadLogFeature() {
        this(MidnightBlocks.DEAD_WOOD_LOG.getDefaultState());
    }

    @Override
    public boolean placeFeature(World world, Random rand, BlockPos origin) {
        Direction direction = Direction.HORIZONTALS[rand.nextInt(Direction.HORIZONTALS.length)];
        BlockLog.EnumAxis axis = direction.getAxis() == Direction.Axis.X ? BlockLog.EnumAxis.X : BlockLog.EnumAxis.Z;
        BlockLog.EnumAxis perpendicular = axis == BlockLog.EnumAxis.X ? BlockLog.EnumAxis.Z : BlockLog.EnumAxis.X;

        int length = rand.nextInt(4) + 3;
        int halfLength = length / 2;

        BlockPos basePos = origin.offset(direction, -halfLength);
        BlockPos endPos = origin.offset(direction, length - halfLength);

        if (!this.checkValid(world, basePos, endPos)) {
            return false;
        }

        for (BlockPos pos : BlockPos.getAllInBoxMutable(basePos, endPos)) {
            this.placeState(world, pos, this.log.with(BlockLog.LOG_AXIS, axis));
        }

        int extrusionCount = rand.nextInt(3);
        for (int i = 0; i < extrusionCount; i++) {
            BlockPos intermediate = basePos.offset(direction, rand.nextInt(length));
            Direction side = rand.nextBoolean() ? direction.rotateY() : direction.rotateYCCW();
            this.placeState(world, intermediate.offset(side), this.log.with(BlockLog.LOG_AXIS, perpendicular));
        }

        return true;
    }

    private boolean checkValid(World world, BlockPos basePos, BlockPos endPos) {
        BlockPos minPos = WorldUtil.min(basePos, endPos).add(-1, 0, -1);
        BlockPos maxPos = WorldUtil.max(basePos, endPos).add(1, 1, 1);

        for (BlockPos pos : BlockPos.getAllInBoxMutable(minPos, maxPos)) {
            if (!this.canReplace(world, pos)) {
                return false;
            }
        }

        for (BlockPos.MutableBlockPos pos : BlockPos.getAllInBoxMutable(basePos, endPos)) {
            pos.move(Direction.DOWN);

            BlockState groundState = world.getBlockState(pos);
            if (!groundState.isSideSolid(world, pos, Direction.UP)) {
                return false;
            }

            pos.move(Direction.UP);
        }

        return true;
    }
}
