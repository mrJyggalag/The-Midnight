package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.entity.task.NeutralGoal;
import com.mushroom.midnight.common.registry.MidnightLootTables;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class StingerEntity extends GrowableEntity implements IMob {
    private final AnimationCapability animCap = new AnimationCapability();

    public StingerEntity(EntityType<? extends StingerEntity> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    @Override
    public int getMaxGrowingAge() {
        return 5;
    }

    @Override
    public int getGrowingTimeByAge() {
        return 1200;
    }

    @Override
    protected void onGrowingToAge(int age) {
        float maxHealth = 10f + age * 4f;
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHealth);
        setHealth(maxHealth);
        getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2d + age);
        if (age > 0) {
            getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(age);
        }
        if (age == getMaxGrowingAge()) {
            this.targetSelector.addGoal(1, new NeutralGoal(this, new HurtByTargetGoal(this), false));
            this.targetSelector.addGoal(2, new NeutralGoal(this, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true, false), false));
        }
    }

    @Nullable
    @Override
    public GrowableEntity createChild(GrowableEntity growable) {
        return null;
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new GroundPathNavigator(this, world);
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        if (getPosition().getY() > 50) {
            return false;
        }
        return super.canSpawn(worldIn, spawnReasonIn);
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
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
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15f, 1f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new NeutralGoal(this, new PanicGoal(this, 1.2d), true));
        this.goalSelector.addGoal(2, new NeutralGoal(this, new MeleeAttackGoal(this, 1d, false), false));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1d));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7d, 0.005f));
        this.goalSelector.addGoal(7, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 8f, 0.02f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NeutralGoal(this, new HurtByTargetGoal(this), false));
        this.targetSelector.addGoal(2, new NeutralGoal(this, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true, true), false));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2d);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10d);
        getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2d);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        super.attackEntityAsMob(entity);
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue());
        if (flag) {
            if (!isChild()) {
                int age = getGrowingAge();
                ((PlayerEntity) entity).addPotionEffect(new EffectInstance(Effects.WEAKNESS, 100, age == getMaxGrowingAge() ? 2 : age > 2 ? 1 : 0, false, true));
            }
            applyEnchantments(this, entity);
            animCap.setAnimation(this, AnimationCapability.Type.ATTACK, 10);
        }
        return flag;
    }

    @Override
    public void swingArm(Hand hand) {
    }

    @Override
    protected ResourceLocation getLootTable() {
        return MidnightLootTables.LOOT_TABLE_STINGER;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT dataTag) {
        if (this.rand.nextInt(5) == 0) {
            setGrowingAge(-1);
        }
        return livingdata;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 5 + getGrowingAge() * 2;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        animCap.updateAnimation();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        return capability == Midnight.ANIMATION_CAP ? LazyOptional.of(() -> this.animCap).cast() : LazyOptional.empty();
    }
}
