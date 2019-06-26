package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.navigation.CustomPathNavigateGround;
import com.mushroom.midnight.common.entity.task.NeutralGoal;
import com.mushroom.midnight.common.registry.MidnightLootTables;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IMobEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class SkulkEntity extends AnimalEntity {
    private static final DataParameter<Boolean> STEALTH = EntityDataManager.createKey(SkulkEntity.class, DataSerializers.BOOLEAN);
    private int stealthCooldown = 0;

    public SkulkEntity(World world) {
        super(world);
        setSize(0.6f, 0.6f);
    }

    @Override
    public void registerData() {
        super.registerData();
        dataManager.register(STEALTH, Boolean.FALSE);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new CustomPathNavigateGround(this, world);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (getPosition().getY() <= world.getSeaLevel()) {
            return false;
        }
        BlockState belowState = world.getBlockState(new BlockPos(this).down());
        return belowState.isFullCube() && belowState.canEntitySpawn(this);
    }

    @Override
    @Nullable
    public IMobEntityData onInitialSpawn(DifficultyInstance difficulty, @Nullable IMobEntityData livingdata) {
        setStealth(true);
        return livingdata;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new NeutralGoal(this, new PanicGoal(this, 1d), true));
        this.goalSelector.addGoal(2, new NeutralGoal(this, new MeleeAttackGoal(this, 1d, false), false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1d, 0.005f) {
            @Override
            public boolean shouldExecute() {
                boolean valid = super.shouldExecute();
                if (valid && canStealth()) { setStealth(true); }
                return valid;
            }
        });
        this.goalSelector.addGoal(7, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 8f, 0.02f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this) {
            @Override
            public boolean shouldExecute() {
                boolean valid = super.shouldExecute();
                if (valid && canStealth()) { setStealth(true); }
                return valid;
            }
        });
        this.targetSelector.addGoal(1, new NeutralGoal(this, new HurtByTargetGoal(this, true), false));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1d);
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25d);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6d);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        super.attackEntityAsMob(entity);
        float damage = (float) getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
        if (flag) {
            applyEnchantments(this, entity);
        }
        setStealth(false);
        return flag;
    }

    @Override
    public void swingArm(Hand hand) {
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (stealthCooldown > 0) {
            stealthCooldown--;
        }
    }

    public boolean isStealth() {
        return dataManager.get(STEALTH);
    }

    public boolean canStealth() {
        return stealthCooldown <= 0;
    }

    public void setStealth(boolean flag) {
        dataManager.set(STEALTH, flag);
        stealthCooldown = 60;
    }

    @Override
    public float getAIMoveSpeed() {
        return super.getAIMoveSpeed() * (isStealth() ? 0.3f : 1f);
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        super.damageEntity(damageSrc, damageAmount);
        setStealth(false);
    }

    @Override
    public void writeEntityToNBT(CompoundNBT compound) {
        super.writeEntityToNBT(compound);
        compound.putBoolean("stealth", isStealth());
    }

    @Override
    public void readEntityFromNBT(CompoundNBT compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("stealth", Constants.NBT.TAG_BYTE)) {
            setStealth(compound.getBoolean("stealth"));
        }
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
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return MidnightLootTables.LOOT_TABLE_SKULK;
    }
}
