package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.entity.FlyingNavigator;
import com.mushroom.midnight.common.entity.task.HunterIdleGoal;
import com.mushroom.midnight.common.entity.task.HunterSwoopGoal;
import com.mushroom.midnight.common.entity.task.HunterTargetGoal;
import com.mushroom.midnight.common.entity.task.HunterTrackGoal;
import com.mushroom.midnight.common.entity.util.ChainSolver;
import com.mushroom.midnight.common.registry.MidnightEffects;
import com.mushroom.midnight.common.registry.MidnightLootTables;
import com.mushroom.midnight.common.util.MeanValueRecorder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Point3f;

public class HunterEntity extends FlyingEntity {
    private final AnimationCapability animCap = new AnimationCapability();

    public static final int FLIGHT_HEIGHT = 40;

    public float roll;
    public float prevRoll;

    public int swoopCooldown;

    private final MeanValueRecorder deltaYaw = new MeanValueRecorder(20);
    private final ChainSolver<HunterEntity> chainSolver = new ChainSolver<>(
            new Point3f(0.0F, 0.0F, 0.5875F),
            new Point3f[] {
                    new Point3f(0.0F, 0.0F, 0.775F),
                    new Point3f(0.0F, 0.0F, 1.65F),
                    new Point3f(0.0F, 0.0F, 2.525F)
            },
            0.5F,
            0.5F,
            (entity, matrix) -> {
                matrix.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
                matrix.rotate(entity.roll, 0.0F, 0.0F, 1.0F);
                matrix.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);
            }
    );

    public HunterEntity(EntityType<HunterEntity> entityType, World world) {
        super(entityType, world);
        this.moveController = new MoveHelper(this);
        this.lookController = new LookHelper(this);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        FlyingNavigator navigator = new FlyingNavigator(this, world);
        navigator.setCanOpenDoors(false);
        navigator.setCanSwim(false);
        navigator.setCanEnterDoors(false);
        return navigator;
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        return this.getPosition().getY() > this.world.getSeaLevel();
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.12);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new HunterSwoopGoal(this, 1.3));
        this.goalSelector.addGoal(2, new HunterTrackGoal(this, 0.7));
        this.goalSelector.addGoal(100, new HunterIdleGoal(this, 0.6));

        this.targetSelector.addGoal(1, new HunterTargetGoal<>(this, PlayerEntity.class));
        this.targetSelector.addGoal(2, new HunterTargetGoal<>(this, AnimalEntity.class));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isRemote) {
            if (this.swoopCooldown > 0) {
                this.swoopCooldown--;
            }
        } else {

            this.deltaYaw.record(this.rotationYaw - this.prevRotationYaw);
            float deltaYaw = this.deltaYaw.computeMean();

            this.prevRoll = this.roll;
            this.roll = MathHelper.clamp(-deltaYaw * 8.0F, -45.0F, 45.0F);

//            this.chainSolver.update(this);
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean grounded, BlockState state, BlockPos pos) {
    }

    @Override
    public void travel(Vec3d direction) {
        if (this.isServerWorld() || this.canPassengerSteer()) {
            double speed = this.getAIMoveSpeed() * 0.3;

            Vec3d lookVector = this.getLookVec();
            Vec3d moveVector = lookVector.normalize().scale(speed);

            this.setMotion(this.getMotion().add(moveVector).scale(0.91));
            this.move(MoverType.SELF, this.getMotion());
        }

        this.updateLimbs();
    }

    private void updateLimbs() {
        this.prevLimbSwingAmount = this.limbSwingAmount;

        double deltaX = this.posX - this.prevPosX;
        double deltaY = this.posY - this.prevPosY;
        double deltaZ = this.posZ - this.prevPosZ;

        float distance = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        float moveAmount = Math.min(distance * 4.0F, 1.0F);

        this.limbSwingAmount += (moveAmount - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;

                float theta = (float) Math.toRadians(this.rotationYaw);
                living.knockBack(this, 0.3F, MathHelper.sin(theta), -MathHelper.cos(theta));
                living.addPotionEffect(new EffectInstance(MidnightEffects.TORMENTED, 6 * 20));

                this.animCap.setAnimation(this, AnimationCapability.Type.ATTACK, 10);
            }

            return true;
        }

        return false;
    }

    public ChainSolver<HunterEntity> getChainSolver() {
        return this.chainSolver;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    private static class MoveHelper extends MovementController {
        private static final float CLOSE_TURN_SPEED = 150.0F;
        private static final float FAR_TURN_SPEED = 6.5F;

        private static final float CLOSE_TURN_DISTANCE = 2.0F;
        private static final float FAR_TURN_DISTANCE = 7.0F;

        MoveHelper(HunterEntity parent) {
            super(parent);
        }

        @Override
        public void tick() {
            if (this.action == Action.MOVE_TO) {
                this.action = Action.WAIT;

                double deltaX = this.posX - this.mob.posX;
                double deltaZ = this.posZ - this.mob.posZ;
                double deltaY = this.posY - this.mob.posY;
                double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

                if (distanceSquared < 2.5) {
                    this.mob.setMoveForward(0.0F);
                    return;
                }

                float distance = MathHelper.sqrt(distanceSquared);
                float turnSpeed = this.computeTurnSpeed(distance);

                float targetYaw = (float) (Math.toDegrees(MathHelper.atan2(deltaZ, deltaX))) - 90.0F;
                this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, targetYaw, turnSpeed);

                double deltaHorizontal = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

                float targetPitch = (float) -Math.toDegrees(Math.atan2(deltaY, deltaHorizontal));
                this.mob.rotationPitch = this.limitAngle(this.mob.rotationPitch, targetPitch, turnSpeed);

                double flySpeed = this.mob.getAttribute(SharedMonsterAttributes.FLYING_SPEED).getValue();
                double resultSpeed = this.speed * flySpeed;
                this.mob.setAIMoveSpeed((float) resultSpeed);
            } else {
                this.mob.setMoveForward(0.0F);
            }
        }

        private float computeTurnSpeed(float distance) {
            float lerpRange = FAR_TURN_DISTANCE - CLOSE_TURN_DISTANCE;
            float alpha = MathHelper.clamp((distance - CLOSE_TURN_DISTANCE) / lerpRange, 0.0F, 1.0F);
            float turnSpeed = CLOSE_TURN_SPEED + (FAR_TURN_SPEED - CLOSE_TURN_SPEED) * alpha;
            return turnSpeed * (float) this.speed;
        }
    }

    private static class LookHelper extends LookController {
        private final MobEntity parent;

        LookHelper(MobEntity parent) {
            super(parent);
            this.parent = parent;
        }

        @Override
        public void tick() {
            float deltaYaw = MathHelper.wrapDegrees(this.parent.rotationYawHead - this.parent.renderYawOffset);
            if (!this.parent.getNavigator().noPath()) {
                if (deltaYaw < -75.0F) {
                    this.parent.rotationYawHead = this.parent.renderYawOffset - 75.0F;
                }
                if (deltaYaw > 75.0F) {
                    this.parent.rotationYawHead = this.parent.renderYawOffset + 75.0F;
                }
            }
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return MidnightLootTables.LOOT_TABLE_HUNTER; }

    @Override
    public void livingTick() {
        super.livingTick();
        this.animCap.updateAnimation();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        return capability == Midnight.ANIMATION_CAP ? LazyOptional.of(() -> this.animCap).cast() : LazyOptional.empty();
    }
}
