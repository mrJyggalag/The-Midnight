package com.mushroom.midnight.common.entity.task;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.EnumDifficulty;

public class EntityTaskNeutral extends EntityAIBase {
    private final EntityCreature creature;
    private final EntityAIBase task;
    private final boolean isTaskForPeaceful;

    public EntityTaskNeutral(EntityCreature creature, EntityAIBase task, boolean isTaskForPeaceful) {
        this.creature = creature;
        this.task = task;
        this.isTaskForPeaceful = isTaskForPeaceful;
    }

    @Override
    public void startExecuting() {
        task.startExecuting();
    }

    @Override
    public boolean shouldExecute() {
        return canProceed() && task.shouldExecute();
    }

    @Override
    public void updateTask() {
        task.updateTask();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return canProceed() && task.shouldContinueExecuting();
    }

    @Override
    public boolean isInterruptible() {
        return task.isInterruptible();
    }

    @Override
    public void resetTask() {
        task.resetTask();
    }

    @Override
    public void setMutexBits(int mutexBits) {
        task.setMutexBits(mutexBits);
    }

    @Override
    public int getMutexBits() {
        return task.getMutexBits();
    }

    private boolean canProceed() {
        return creature != null && (creature.world.getWorldInfo().getDifficulty() == EnumDifficulty.PEACEFUL) == isTaskForPeaceful;
    }
}
