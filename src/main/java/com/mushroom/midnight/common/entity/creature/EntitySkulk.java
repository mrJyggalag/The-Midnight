package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.navigation.CustomPathNavigateGround;
import com.mushroom.midnight.common.registry.ModLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntitySkulk extends EntityAnimal {

    public EntitySkulk(World world) {
        super(world);
        setSize(0.6f, 0.6f);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        return new CustomPathNavigateGround(this, world);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (getPosition().getY() <= world.getSeaLevel()) {
            return false;
        }
        IBlockState belowState = world.getBlockState(new BlockPos(this).down());
        return belowState.isFullCube() && belowState.canEntitySpawn(this);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1d));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7d, 0.005f));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8f, 0.02f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2d);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4d);
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.5f;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    public int getTalkInterval() {
        return 100;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return ModLootTables.LOOT_TABLE_SKULK;
    }
}
