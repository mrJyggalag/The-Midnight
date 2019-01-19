package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.navigation.CustomPathNavigateGround;
import com.mushroom.midnight.common.registry.ModLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityStinger extends EntityAgeable implements IAnimals {

    public EntityStinger(World worldIn) {
        super(worldIn);
        setSize(0.2f, 0.2f);
        setGrowingAge(-1200);
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        return new CustomPathNavigateGround(this, world);
    }

    @Override
    public boolean getCanSpawnHere() {
        return false;
    }

    @Override
    protected boolean canDespawn() {
        return !isChild();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1d, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6d));
        this.tasks.addTask(7, new EntityAIWander(this, 0.6d, 60));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8f, 0.02f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 10, true, true, p -> p.world.getDifficulty() != EnumDifficulty.PEACEFUL && !p.isCreative() && !p.isSpectator()));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2d);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4d);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1d);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        super.attackEntityAsMob(entity);
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return isChild() ? null : ModLootTables.LOOT_TABLE_STINGER;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    protected void onGrowingAdult() {
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2d);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6d);
        setHealth(6f);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return isChild() ? 3 : 5;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public void setScaleForAge(boolean child) {
        setScale(child ? 1f : 1.5f);
    }
}
