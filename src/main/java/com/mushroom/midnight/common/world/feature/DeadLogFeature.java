package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.util.WorldUtil;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DeadLogFeature extends MidnightNaturalFeature {
    private final IBlockState log;

    public DeadLogFeature(IBlockState log) {
        this.log = log;
    }

    public DeadLogFeature() {
        this(ModBlocks.DEAD_WOOD_LOG.getDefaultState());
    }

    @Override
    public boolean placeFeature(World world, Random rand, BlockPos origin) {
        EnumFacing direction = EnumFacing.HORIZONTALS[rand.nextInt(EnumFacing.HORIZONTALS.length)];
        BlockLog.EnumAxis axis = direction.getAxis() == EnumFacing.Axis.X ? BlockLog.EnumAxis.X : BlockLog.EnumAxis.Z;
        BlockLog.EnumAxis perpendicular = axis == BlockLog.EnumAxis.X ? BlockLog.EnumAxis.Z : BlockLog.EnumAxis.X;

        int length = rand.nextInt(4) + 3;
        int halfLength = length / 2;

        BlockPos basePos = origin.offset(direction, -halfLength);
        BlockPos endPos = origin.offset(direction, length - halfLength);

        if (!this.checkValid(world, basePos, endPos)) {
            return false;
        }

        for (BlockPos pos : BlockPos.getAllInBoxMutable(basePos, endPos)) {
            this.placeState(world, pos, this.log.withProperty(BlockLog.LOG_AXIS, axis));
        }

        int extrusionCount = rand.nextInt(3);
        for (int i = 0; i < extrusionCount; i++) {
            BlockPos intermediate = basePos.offset(direction, rand.nextInt(length));
            EnumFacing side = rand.nextBoolean() ? direction.rotateY() : direction.rotateYCCW();
            this.placeState(world, intermediate.offset(side), this.log.withProperty(BlockLog.LOG_AXIS, perpendicular));
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
            pos.move(EnumFacing.DOWN);

            IBlockState groundState = world.getBlockState(pos);
            if (!groundState.isSideSolid(world, pos, EnumFacing.UP)) {
                return false;
            }

            pos.move(EnumFacing.UP);
        }

        return true;
    }
}
