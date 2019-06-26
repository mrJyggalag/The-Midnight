package com.mushroom.midnight.common.entity.task;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class SearchForBlockGoal extends Goal {
    private final CreatureEntity owner;
    protected final Predicate<BlockState> blockPredicate;
    private final double speed;
    @Nullable private BlockPos destinationBlock = null;
    private final int searchDist;
    private final int searchDelay;
    private int runDelay;

    private int timeoutCounter;
    private int maxStayTicks;

    public SearchForBlockGoal(CreatureEntity owner, Predicate<BlockState> blockPredicate, double speed, int searchDist, int searchDelay) {
        this.owner = owner;
        this.blockPredicate = blockPredicate;
        this.searchDist = searchDist;
        this.searchDelay = searchDelay / 2 + (int)(searchDelay * owner.getRNG().nextFloat());
        this.speed = speed;
        setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        if (this.owner.getAttackTarget() != null) {
            return false;
        } else if (this.runDelay > 0) {
            --this.runDelay;
            return false;
        } else {
            this.runDelay = this.searchDelay + this.owner.getRNG().nextInt(this.searchDelay);
            return searchForDestination();
        }
    }

    private boolean searchForDestination() {
        BlockPos currentPos = this.owner.getPosition();
        Iterable<BlockPos> positions = BlockPos.getAllInBox(currentPos.add(-this.searchDist, -1, -this.searchDist), currentPos.add(this.searchDist, 1, this.searchDist));
        for (BlockPos pos : positions) {
            if (this.owner.world.isBlockLoaded(pos) && this.blockPredicate.test(this.owner.world.getBlockState(pos))) {
                this.destinationBlock = pos;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.destinationBlock != null && this.owner.getAttackTarget() == null && this.owner.hasPath() && this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 1200 && shouldMoveTo(this.destinationBlock);
    }

    private boolean shouldMoveTo(BlockPos pos) {
        return this.blockPredicate.test(this.owner.world.getBlockState(pos));
    }

    @Override
    public void startExecuting() {
        assert this.destinationBlock != null;
        this.owner.getNavigator().tryMoveToXYZ(this.destinationBlock.getX() + 0.5d, this.destinationBlock.getY(), this.destinationBlock.getZ() + 0.5d, this.speed);
        this.timeoutCounter = 0;
        this.maxStayTicks = this.owner.getRNG().nextInt(this.owner.getRNG().nextInt(1200) + 1200) + 1200;
    }

    @Override
    public void tick() {
        assert this.destinationBlock != null;
        boolean isNearDestination = this.owner.getDistanceSq(this.destinationBlock.getX() + 0.5d, this.destinationBlock.getY(), this.destinationBlock.getZ() + 0.5d) <= 1d;
        if (isNearDestination) {
            --this.timeoutCounter;
        } else {
            ++this.timeoutCounter;
        }
        if (isNearDestination) {
            this.owner.setHomePosAndDistance(this.destinationBlock, 20);
            this.destinationBlock = null;
        }
    }
}
