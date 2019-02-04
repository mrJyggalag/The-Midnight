package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.navigation.CustomPathNavigateGround;
import com.mushroom.midnight.common.entity.task.EntityTaskNeutral;
import com.mushroom.midnight.common.network.MessageNightstagAttack;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.mushroom.midnight.common.registry.ModLootTables.LOOT_TABLE_NIGHTSTAG;

public class EntityNightStag extends EntityAnimal {
    private static final int ATTACK_ANIMATION_TICKS = 10;
    private int attackAnimation = 0;
    private int prevAttackAnimation;
    private boolean attacking = false;

    public EntityNightStag(World world) {
        super(world);
        setSize(0.9f, 1.87f);
    }

    @Override
    @Nullable
    public EntityAgeable createChild(EntityAgeable entity) {
        return null;
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        setGrowingAge(this.rand.nextInt(5) == 0 ? -1 : -24000);
        return livingdata;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_LLAMA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_LLAMA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_LLAMA_DEATH;
    }

    @Override
    public int getTalkInterval() {
        return 200;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15f, 1f);
    }

    @Override
    public float getBlockPathWeight(BlockPos pos) {
        Block belowBlock = world.getBlockState(pos.down()).getBlock();
        return belowBlock == ModBlocks.MIDNIGHT_GRASS || belowBlock == ModBlocks.NIGHTSTONE ? 10f : 9f - (world.getLightBrightness(pos) * 10f);
    }

    @Override
    public boolean getCanSpawnHere() {
        IBlockState belowState = world.getBlockState(new BlockPos(this).down());
        return belowState.isFullCube() && belowState.canEntitySpawn(this);
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        return new CustomPathNavigateGround(this, world);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityTaskNeutral(this, new EntityAIPanic(this, 1.2d), true));
        this.tasks.addTask(2, new EntityTaskNeutral(this, new EntityAIAttackMelee(this, 1d, false), false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1d));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7d, 0.005f));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 12f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityTaskNeutral(this, new EntityAIHurtByTarget(this, true), false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25d);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15d);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2d);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        super.attackEntityAsMob(entity);
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        if (flag) {
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(ModEffects.DARKNESS, 200, 0, false, true));
            }
            applyEnchantments(this, entity);
            setAttacking(true);
            Midnight.NETWORK.sendToAllTracking(new MessageNightstagAttack(this), this);
        }
        return flag;
    }

    @Override
    public void swingArm(EnumHand hand) {
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        updateAttackAnimation();
    }

    private void updateAttackAnimation() {
        this.prevAttackAnimation = this.attackAnimation;
        if (isAttacking()) {
            if (this.attackAnimation >= ATTACK_ANIMATION_TICKS) {
                this.attackAnimation = 0;
                setAttacking(false);
            } else {
                this.attackAnimation++;
            }
        }
    }

    public float getAttackAnimation(float partialTicks) {
        float animationTick = this.prevAttackAnimation + (this.attackAnimation - this.prevAttackAnimation) * partialTicks;
        return animationTick / ATTACK_ANIMATION_TICKS;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isAttacking() {
        return this.attacking;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return 5;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return isChild() ? null : LOOT_TABLE_NIGHTSTAG;
    }
}
