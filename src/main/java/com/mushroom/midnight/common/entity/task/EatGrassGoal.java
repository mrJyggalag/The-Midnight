package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class EatGrassGoal extends Goal {
    private final Predicate<BlockState> eatPredicate;
    protected final MobEntity owner;
    protected AnimationCapability capAnim;
    protected final int duration;

    public EatGrassGoal(MobEntity owner, int duration, boolean vanillaBehavior, Predicate<BlockState> eatPredicate) {
        this.owner = owner;
        this.eatPredicate = vanillaBehavior ? eatPredicate.or(p -> p.getBlock() == Blocks.TALL_GRASS && p.getValue(TallGrassBlock.TYPE) == TallGrassBlock.EnumType.GRASS) : eatPredicate;
        this.duration = duration;
        setMutexBits(7);
    }

    @Override
    public boolean shouldExecute() {
        this.capAnim = this.owner.getCapability(Midnight.ANIMATION_CAP, null);
        if (this.capAnim == null || this.owner.getRNG().nextInt(this.owner.isChild() ? 50 : 500) != 0) {
            return false;
        } else {
            BlockPos currentPos = this.owner.getPosition();
            if (this.eatPredicate.test(this.owner.world.getBlockState(currentPos))) {
                return true;
            } else {
                Block belowBlock = this.owner.world.getBlockState(currentPos.down()).getBlock();
                return belowBlock == Blocks.GRASS || belowBlock == MidnightBlocks.MIDNIGHT_GRASS;
            }
        }
    }

    @Override
    public void startExecuting() {
        this.capAnim.setAnimation(this.owner, AnimationCapability.Type.EAT, this.duration);
        this.owner.getNavigator().clearPath();
    }

    @Override
    public void resetTask() {
        this.capAnim.resetAnimation(this.owner);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.capAnim.getProgress(1f) < 1f;
    }

    @Override
    public void tick() {
        if (this.capAnim.getCurrentTick() == this.capAnim.getDuration() - 10) {
            BlockPos currentPos = this.owner.getPosition();
            BlockState currentState = this.owner.world.getBlockState(currentPos);
            if (this.eatPredicate.test(currentState)) {
                if (ForgeEventFactory.getMobGriefingEvent(this.owner.world, this.owner)) {
                    eatPlant(currentState, currentPos);
                }
                this.owner.eatGrassBonus();
            } else {
                BlockPos belowPos = currentPos.down();
                Block belowBlock = this.owner.world.getBlockState(belowPos).getBlock();
                if (belowBlock == Blocks.GRASS || belowBlock == MidnightBlocks.MIDNIGHT_GRASS) {
                    if (ForgeEventFactory.getMobGriefingEvent(this.owner.world, this.owner)) {
                        this.owner.world.playEvent(2001, belowPos, Block.getIdFromBlock(belowBlock));
                        this.owner.world.setBlockState(belowPos, (belowBlock == Blocks.GRASS ? Blocks.DIRT : MidnightBlocks.MIDNIGHT_DIRT).getDefaultState(), 2);
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
