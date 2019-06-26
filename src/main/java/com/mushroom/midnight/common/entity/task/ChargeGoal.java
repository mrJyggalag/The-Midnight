package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.entity.creature.NightStagEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class ChargeGoal extends Goal {
    private final MobEntity owner;
    private final double speed;
    private final int duration;
    private final float chance;
    protected AnimationCapability capAnim;
    protected int tickCounter;
    protected LivingEntity target;
    protected Vec3d direction;
    protected int chargeTime;

    public ChargeGoal(MobEntity owner, double speed, int duration, float chance) {
        this.owner = owner;
        this.speed = speed;
        this.duration = duration;
        this.chance = chance;
        this.tickCounter = 0;
        this.chargeTime = duration / 10;
        setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        this.target = this.owner.getAttackTarget();
        if (!this.owner.isChild() && this.owner.onGround && this.target != null && this.target.isAlive() && this.target.posY <= this.owner.posY && this.owner.getRNG().nextFloat() < chance) {
            double distance = this.owner.getDistanceSq(this.target);
            if (distance > 50d && distance < 75d && this.owner.getEntitySenses().canSee(this.target)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.tickCounter = 0;
        this.owner.getMoveHelper().setMoveTo(this.target.posX, this.target.posY, this.target.posZ, this.speed);
        capAnim = owner.getCapability(Midnight.ANIMATION_CAP);
        if (this.capAnim != null) {
            capAnim.setAnimation(this.owner, AnimationCapability.Type.CHARGE, 200);
        }
    }

    @Override
    public void resetTask() {
        this.tickCounter = 0;
        this.target = null;
        this.direction = null;
        if (this.capAnim != null) {
            capAnim.resetAnimation(this.owner);
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.tickCounter <= this.chargeTime || (this.tickCounter < this.duration && !this.owner.collidedHorizontally);
    }

    @Override
    public void tick() {
        this.tickCounter++;
        if (this.tickCounter < this.chargeTime) {
            this.owner.getLookController().setLookPositionWithEntity(this.target, 0f, this.owner.getVerticalFaceSpeed());
        } else if (this.tickCounter == this.chargeTime) {
            this.direction = this.owner.getPositionVector().add(this.target.getPositionVector().subtract(this.owner.getPositionVector()).scale(1.5d));
        } else {
            List<LivingEntity> hitEntities = this.owner.world.getEntitiesWithinAABB(LivingEntity.class, this.owner.getBoundingBox().grow(0.2d, 0.2d, 0.2d), p -> !(p instanceof NightStagEntity));
            if (!hitEntities.isEmpty()) {
                for (LivingEntity hitEntity : hitEntities) {
                    if (this.owner.attackEntityAsMob(this.target)) {
                        hitEntity.knockBack(this.owner, 1f, (double) MathHelper.sin(this.owner.rotationYaw * 0.017453292f), (double) (-MathHelper.cos(this.owner.rotationYaw * 0.017453292f)));
                        if (hitEntity instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity) hitEntity).connection.sendPacket(new SEntityVelocityPacket(hitEntity));
                            hitEntity.velocityChanged = false;
                            Vec3d motion = owner.getMotion();
                            hitEntity.setMotion(new Vec3d(motion.x * 2, 2, motion.z * 2));
                        }
                    }
                }
                this.tickCounter = this.duration;
            } else {
                if (this.owner.getDistanceSq(this.direction.x, this.direction.y, this.direction.z) < 2d) {
                    this.tickCounter = this.duration;
                } else {
                    this.owner.getLookController().setLookPositionWithEntity(this.target, 0f, this.owner.getVerticalFaceSpeed());
                    this.owner.getMoveHelper().setMoveTo(this.direction.x, this.direction.y, this.direction.z, this.speed * 2d);
                }
            }
        }
    }
}
