package com.mushroom.midnight.common.entity.creature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Predicate;

public class ShadeSquirreEntity extends TameableEntity {
    private static final DataParameter<BlockPos> HOME_POS = EntityDataManager.createKey(ShadeSquirreEntity.class, DataSerializers.BLOCK_POS);
    public static final EntitySize normal_size = EntitySize.flexible(0.6f, 0.75f);

    private static final Ingredient BREEDING_ITEMS = Ingredient.fromItems(MidnightItems.GLOB_FUNGUS_HAND);

    private static final Map<Pose, EntitySize> pose_map = ImmutableMap.<Pose, EntitySize>builder().put(Pose.STANDING, normal_size).put(Pose.SLEEPING, EntitySize.flexible(0.6f, 0.55f)).put(Pose.FALL_FLYING, EntitySize.flexible(0.6f, 0.75f)).put(Pose.SWIMMING, EntitySize.flexible(0.6f, 0.6f)).put(Pose.SNEAKING, EntitySize.flexible(0.65f, 0.6f)).put(Pose.DYING, EntitySize.fixed(0.2F, 0.2F)).build();

    public static final Predicate<LivingEntity> attackSmallTarget = (p_213440_0_) -> {
        EntityType<?> entitytype = p_213440_0_.getType();
        return entitytype != EntityType.CAT && entitytype != MidnightEntities.SHADESQUIRRE && entitytype.getWidth() <= 0.8F && entitytype.getHeight() <= 0.8F;
    };

    public ShadeSquirreEntity(EntityType<? extends ShadeSquirreEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTamed(false);
    }

    @Override
    protected void registerGoals() {
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.7D, BREEDING_ITEMS, true) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && !isTamed();
            }
        });
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.4D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.35D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.3D, 0.0015F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, attackSmallTarget));
    }

    public void setHome(BlockPos position) {
        this.dataManager.set(HOME_POS, position);
    }

    private BlockPos getHome() {
        return this.dataManager.get(HOME_POS);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(HOME_POS, BlockPos.ZERO);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.3F);
        if (this.isTamed()) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("HomePosX", this.getHome().getX());
        compound.putInt("HomePosY", this.getHome().getY());
        compound.putInt("HomePosZ", this.getHome().getZ());
    }

    public void readAdditional(CompoundNBT compound) {
        int i = compound.getInt("HomePosX");
        int j = compound.getInt("HomePosY");
        int k = compound.getInt("HomePosZ");
        if (!new BlockPos(i, j, k).equals(BlockPos.ZERO)) {
            this.setHome(new BlockPos(i, j, k));
        }
        super.readAdditional(compound);
    }


    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {


        return this.isChild() ? sizeIn.height * 0.45F : sizeIn.height * 0.85F;
    }

    public EntitySize getSize(Pose p_213305_1_) {
        return pose_map.getOrDefault(p_213305_1_, normal_size);
    }

    @Override
    public void tick() {
        super.tick();

        this.updatePose();
    }

    @Override
    public BlockPos getHomePosition() {
        return super.getHomePosition();
    }

    protected void updatePose() {
        if (this.isPoseClear(Pose.SWIMMING)) {
            Pose pose;
            if (this.isSleeping()) {
                pose = Pose.SLEEPING;
            } else if (this.isSwimming()) {
                pose = Pose.SWIMMING;
            } else if (this.isSpinAttacking()) {
                pose = Pose.SPIN_ATTACK;
            } else if (!this.isSitting()) {
                pose = Pose.SNEAKING;
            } else {
                pose = Pose.STANDING;
            }

            Pose pose1;
            if (!this.isSpectator() && !this.isPassenger() && !this.isPoseClear(pose)) {
                if (this.isPoseClear(Pose.SNEAKING)) {
                    pose1 = Pose.SNEAKING;
                } else {
                    pose1 = Pose.SWIMMING;
                }
            } else {
                pose1 = pose;
            }

            this.setPose(pose1);
        }
    }

    @Override
    protected Brain<?> createBrain(Dynamic<?> p_213364_1_) {
        return super.createBrain(p_213364_1_);
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (this.isTamed()) {
            if (!itemstack.isEmpty()) {
                if (item.isFood()) {
                    if (this.isBreedingItem(itemstack) && this.getHealth() < this.getMaxHealth()) {
                        ItemStack itemstack1 = itemstack.onItemUseFinish(this.world, this);

                        if (!player.abilities.isCreativeMode) {
                            itemstack1.shrink(1);
                        }

                        this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.8F, 1.2F + this.rand.nextFloat() * 0.3F);

                        this.heal((float) item.getFood().getHealing());
                        return true;
                    }
                }
            }

            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack)) {
                this.sitGoal.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget((LivingEntity) null);
            }
        } else if (this.isBreedingItem(itemstack)) {
            this.consumeItemFromStack(player, itemstack);

            if (!this.world.isRemote) {
                if (this.rand.nextInt(4) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity) null);
                    this.sitGoal.setSitting(true);
                    this.setHealth(20.0F);
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte) 6);
                }
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_ITEMS.test(stack);
    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof WolfEntity) {
                WolfEntity wolfentity = (WolfEntity) target;
                if (wolfentity.isTamed() && wolfentity.getOwner() == owner) {
                    return false;
                }
            }

            if (target instanceof ShadeSquirreEntity) {
                ShadeSquirreEntity squirreEntity = (ShadeSquirreEntity) target;
                if (squirreEntity.isTamed() && squirreEntity.getOwner() == owner) {
                    return false;
                }
            }

            if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity) target).isTame()) {
                return false;
            } else {
                return !(target instanceof CatEntity) || !((CatEntity) target).isTamed();
            }
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return MidnightEntities.SHADESQUIRRE.create(this.world);
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }
}
