package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.entity.navigation.CustomPathNavigateGround;
import com.mushroom.midnight.common.entity.task.EntityTaskNeutral;
import com.mushroom.midnight.common.registry.ModLootTables;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityStinger extends EntityAgeable implements IMob {
    private static final AttributeModifier CHILD_ATTACK_MALUS = new AttributeModifier(UUID.fromString("c02aca06-ecb7-447f-bcc7-1fef82fecdbd"), "stinger_child_attack_malus", -1d, 0);
    private static final int GROWING_TIME = -1200;
    private final AnimationCapability animCap = new AnimationCapability();

    public EntityStinger(World worldIn) {
        super(worldIn);
        setSize(0.2f, 0.2f);
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        return new CustomPathNavigateGround(this, world);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (getPosition().getY() > 50) {
            return false;
        }
        IBlockState belowState = world.getBlockState(new BlockPos(this).down());
        return belowState.isFullCube() && belowState.canEntitySpawn(this);
    }

    @Override
    protected boolean canDespawn() {
        return !isChild();
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    @Override
    public int getTalkInterval() {
        return 100;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block block) {
        playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15f, 1f);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityTaskNeutral(this, new EntityAIPanic(this, 1.2d), true));
        this.tasks.addTask(2, new EntityTaskNeutral(this, new EntityAIAttackMelee(this, 1d, false), false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1d));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7d, 0.005f));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8f, 0.02f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityTaskNeutral(this, new EntityAIHurtByTarget(this, true), false));
        this.targetTasks.addTask(2, new EntityTaskNeutral(this, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true), false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2d);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12d);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2d);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        super.attackEntityAsMob(entity);
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        if (flag) {
            if (!isChild() && entity instanceof EntityPlayer) {
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0, false, true));
            }
            applyEnchantments(this, entity);
            animCap.setAnimation(this, AnimationCapability.AnimationType.ATTACK, 10);
        }
        return flag;
    }

    @Override
    public void swingArm(EnumHand hand) {
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return ModLootTables.LOOT_TABLE_STINGER;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        if (this.rand.nextInt(5) == 0) {
            setGrowingAge(GROWING_TIME);
        }
        return livingdata;
    }

    @Override
    protected void onGrowingAdult() {
        IAttributeInstance attackAttrib = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        if (attackAttrib.hasModifier(CHILD_ATTACK_MALUS)) {
            attackAttrib.removeModifier(CHILD_ATTACK_MALUS);
        }
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12d);
        setHealth(12f);
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

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        animCap.updateAnimation();
        if (!world.isRemote && isChild()) {
            IAttributeInstance attackAttrib = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
            if (!attackAttrib.hasModifier(CHILD_ATTACK_MALUS)) {
                attackAttrib.applyModifier(CHILD_ATTACK_MALUS);
                getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6d);
                setHealth(6f);
            }
        }
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == Midnight.animationCap) {
            return Midnight.animationCap.cast(animCap);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == Midnight.animationCap || super.hasCapability(capability, facing);
    }
}
