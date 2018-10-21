package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.creature.EntityRifter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class EntityTaskRifterTeleport extends EntityAIBase {
    private static final double MIN_EXECUTE_DISTANCE = 12;
    private static final int TELEPORT_DISTANCE = 8;

    private final EntityRifter owner;

    public EntityTaskRifterTeleport(EntityRifter owner) {
        this.owner = owner;
    }

    @Override
    public boolean shouldExecute() {
        if (this.owner.getRNG().nextInt(40) != 0) {
            return false;
        }
        EntityLivingBase target = this.owner.getAttackTarget();
        return target != null && target.getDistanceSq(this.owner) > MIN_EXECUTE_DISTANCE * MIN_EXECUTE_DISTANCE && !this.canBeSeen();
    }

    private boolean canBeSeen() {
        List<EntityPlayer> players = this.owner.world.getEntitiesWithinAABB(EntityPlayer.class, this.owner.getEntityBoundingBox().grow(24.0));
        for (EntityPlayer player : players) {
            Vec3d playerLook = player.getLook(1.0F);

            Vec3d deltaPos = player.getPositionVector().subtract(this.owner.getPositionVector());
            deltaPos = deltaPos.normalize();

            if (playerLook.dotProduct(deltaPos) < 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void startExecuting() {
        EntityLivingBase attackTarget = this.owner.getAttackTarget();
        if (attackTarget == null) {
            return;
        }

        Vec3d target = this.computeTeleportTarget(attackTarget);
        if (target != null) {
            this.owner.setPositionAndUpdate(target.x, target.y, target.z);
            this.owner.getNavigator().clearPath();
        }
    }

    private Vec3d computeTeleportTarget(EntityLivingBase target) {
        Vec3d targetPoint = new Vec3d(target.posX, target.posY, target.posZ);
        for (int i = 0; i < 64; i++) {
            Vec3d teleportTarget = RandomPositionGenerator.findRandomTargetBlockTowards(this.owner, TELEPORT_DISTANCE, 3, targetPoint);
            if (teleportTarget == null) {
                continue;
            }
            if (!this.owner.world.collidesWithAnyBlock(this.getEntityBoundAt(this.owner, teleportTarget))) {
                return teleportTarget;
            }
        }
        return null;
    }

    private AxisAlignedBB getEntityBoundAt(Entity entity, Vec3d pos) {
        float halfWidth = entity.width / 2.0F;
        return new AxisAlignedBB(
                pos.x - halfWidth, pos.y, pos.z - halfWidth,
                pos.x + halfWidth, pos.y + entity.height, pos.z + halfWidth
        );
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }
}
