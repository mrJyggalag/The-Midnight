package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class EntityTaskEatGrass extends EntityAIBase {
    private final Predicate<IBlockState> eatPredicate;
    protected final EntityLiving owner;
    protected AnimationCapability capAnim;
    protected final int duration;

    public EntityTaskEatGrass(EntityLiving owner, int duration, boolean vanillaBehavior, Predicate<IBlockState> eatPredicate) {
        this.owner = owner;
        this.eatPredicate = vanillaBehavior ? eatPredicate.or(p -> p.getBlock() == Blocks.TALLGRASS && p.getValue(BlockTallGrass.TYPE) == BlockTallGrass.EnumType.GRASS) : eatPredicate;
        this.duration = duration;
        setMutexBits(7);
    }

    public boolean shouldExecute() {
        capAnim = owner.getCapability(Midnight.animationCap, null);
        if (this.capAnim == null || this.owner.getRNG().nextInt(this.owner.isChild() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos currentPos = this.owner.getPosition();
            if (this.eatPredicate.test(this.owner.world.getBlockState(currentPos))) {
                return true;
            } else {
                Block belowBlock = this.owner.world.getBlockState(currentPos.down()).getBlock();
                return belowBlock == Blocks.GRASS || belowBlock == ModBlocks.MIDNIGHT_GRASS;
            }
        }
    }

    public void startExecuting() {
        capAnim.setAnimation(this.owner, AnimationCapability.AnimationType.EAT, this.duration);
        this.owner.getNavigator().clearPath();
    }

    public void resetTask() {
        capAnim.resetAnimation(this.owner);
    }

    public boolean shouldContinueExecuting() {
        return capAnim.getProgress(1f) < 1f;
    }

    public void updateTask() {
        if (capAnim.getCurrentTick() == capAnim.getDuration() - 10) {
            BlockPos currentPos = owner.getPosition();
            if (eatPredicate.test(this.owner.world.getBlockState(currentPos))) {
                if (ForgeEventFactory.getMobGriefingEvent(this.owner.world, this.owner)) {
                    this.owner.world.destroyBlock(currentPos, false);
                }
                this.owner.eatGrassBonus();
            } else {
                BlockPos belowPos = currentPos.down();
                Block belowBlock = this.owner.world.getBlockState(belowPos).getBlock();
                if (belowBlock == Blocks.GRASS || belowBlock == ModBlocks.MIDNIGHT_GRASS) {
                    if (ForgeEventFactory.getMobGriefingEvent(this.owner.world, this.owner)) {
                        this.owner.world.playEvent(2001, belowPos, Block.getIdFromBlock(belowBlock));
                        this.owner.world.setBlockState(belowPos, (belowBlock == Blocks.GRASS ? Blocks.DIRT : ModBlocks.MIDNIGHT_DIRT).getDefaultState(), 2);
                    }
                    this.owner.eatGrassBonus();
                }
            }
        }
    }
}
