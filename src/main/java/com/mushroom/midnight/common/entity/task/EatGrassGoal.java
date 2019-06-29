package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EatGrassGoal extends Goal {
    private final Predicate<BlockState> eatPredicate;
    protected final MobEntity owner;
    protected final int duration;

    public EatGrassGoal(MobEntity owner, int duration, boolean vanillaBehavior, Predicate<BlockState> eatPredicate) {
        this.owner = owner;
        this.eatPredicate = vanillaBehavior ? eatPredicate.or(p -> p.getBlock() == Blocks.GRASS) : eatPredicate;
        this.duration = duration;
        setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean shouldExecute() {
        if (this.owner.getCapability(Midnight.ANIMATION_CAP, null).map(cap -> false).orElse(true) || this.owner.getRNG().nextInt(this.owner.isChild() ? 50 : 500) != 0) {
            return false;
        } else {
            BlockPos currentPos = new BlockPos(this.owner);
            if (this.eatPredicate.test(this.owner.world.getBlockState(currentPos))) {
                return true;
            } else {
                Block belowBlock = this.owner.world.getBlockState(currentPos.down()).getBlock();
                return belowBlock == Blocks.GRASS_BLOCK || belowBlock == MidnightBlocks.GRASS_BLOCK;
            }
        }
    }

    @Override
    public void startExecuting() {
        this.owner.getCapability(Midnight.ANIMATION_CAP, null).ifPresent(capAnim -> capAnim.setAnimation(this.owner, AnimationCapability.Type.EAT, this.duration));
        this.owner.getNavigator().clearPath();
    }

    @Override
    public void resetTask() {
        this.owner.getCapability(Midnight.ANIMATION_CAP, null).ifPresent(capAnim -> capAnim.resetAnimation(this.owner));
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.owner.getCapability(Midnight.ANIMATION_CAP, null).map(capAnim -> capAnim.getProgress(1f) < 1f).orElse(false);
    }

    @Override
    public void tick() {
        if (this.owner.getCapability(Midnight.ANIMATION_CAP, null).map(capAnim -> capAnim.getCurrentTick() == capAnim.getDuration() - 10).orElse(false)) {
            BlockPos currentPos = this.owner.getPosition();
            BlockState currentState = this.owner.world.getBlockState(currentPos);
            if (this.eatPredicate.test(currentState)) {
                if (ForgeEventFactory.getMobGriefingEvent(this.owner.world, this.owner)) {
                    eatPlant(currentState, currentPos);
                }
                this.owner.eatGrassBonus();
            } else {
                BlockPos belowPos = currentPos.down();
                BlockState belowState = this.owner.world.getBlockState(belowPos);
                Block belowBlock = belowState.getBlock();
                if (belowBlock == Blocks.GRASS || belowBlock == MidnightBlocks.GRASS_BLOCK) {
                    if (ForgeEventFactory.getMobGriefingEvent(this.owner.world, this.owner)) {
                        this.owner.world.playEvent(2001, belowPos, Block.getStateId(belowState));
                        this.owner.world.setBlockState(belowPos, (belowBlock == Blocks.GRASS ? Blocks.DIRT : MidnightBlocks.DIRT).getDefaultState(), 2);
                    }
                    this.owner.eatGrassBonus();
                }
            }
        }
    }

    protected void eatPlant(BlockState state, BlockPos pos) {
        this.owner.world.destroyBlock(pos, false);
    }
}
