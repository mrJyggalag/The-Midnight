package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.navigation.CustomPathNavigateGround;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.mushroom.midnight.common.registry.ModLootTables.LOOT_TABLE_NIGHTSTAG;

public class EntityNightStag extends EntityAnimal {
    public EntityNightStag(World world) {
        super(world);
        setSize(0.9f, 1.87f);
        experienceValue = 4;
    }

    @Override
    @Nullable
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        return new CustomPathNavigateGround(this, world);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.2d));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 24f, 0.8d, 1d));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1d));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7d, 0.005f));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 12f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25d);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15d);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT_TABLE_NIGHTSTAG;
    }
}
