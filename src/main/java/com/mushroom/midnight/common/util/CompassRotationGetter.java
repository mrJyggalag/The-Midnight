package com.mushroom.midnight.common.util;

import com.mushroom.midnight.common.entity.EntityRift;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collection;

public class CompassRotationGetter implements IItemPropertyGetter {
    private static final double RIFT_INFLUENCE_RANGE = 64.0;
    private static final long RIFT_CHECK_INTERVAL = 20 * 5;

    private double rotation;
    private double rotVelocity;
    private long lastUpdateTick;

    private EntityRift closestRift;
    private long lastRiftCheck;

    @Override
    public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase livingHolder) {
        boolean heldByLiving = livingHolder != null;
        Entity entity = (livingHolder != null ? livingHolder : stack.getItemFrame());
        if (entity == null) {
            return 0.0F;
        }

        if (world == null) {
            world = entity.world;
        }

        double angle = this.computeTargetAngle(world, entity);
        if (heldByLiving) {
            angle = this.applyWobble(world, angle);
        }

        return MathHelper.positiveModulo((float) angle, 1.0F);
    }

    private double applyWobble(World world, double targetAngle) {
        if (world.getTotalWorldTime() != this.lastUpdateTick) {
            this.lastUpdateTick = world.getTotalWorldTime();
            double deltaAngle = targetAngle - this.rotation;
            deltaAngle = MathHelper.positiveModulo(deltaAngle + 0.5, 1.0) - 0.5;
            this.rotVelocity += deltaAngle * 0.1;
            this.rotVelocity *= 0.8;
            this.rotation = MathHelper.positiveModulo(this.rotation + this.rotVelocity, 1.0);
        }
        return this.rotation;
    }

    private double computeTargetAngle(World world, Entity entity) {
        double angle;
        if (world.provider.isSurfaceWorld()) {
            angle = this.computeAngle(entity, world.getSpawnPoint());
        } else {
            angle = Math.random();
        }

        EntityRift closestRift = this.getClosestRift(entity);
        if (closestRift == null) {
            return angle;
        }

        double influence = 1.0 - Math.pow((closestRift.getDistance(entity) / RIFT_INFLUENCE_RANGE), 3.0);
        double riftAngle = this.computeAngle(entity, closestRift.getPosition());

        return angle + (riftAngle - angle) * influence;
    }

    @Nullable
    private EntityRift getClosestRift(Entity entity) {
        World world = entity.world;
        long time = world.getTotalWorldTime();

        if (time - this.lastRiftCheck > RIFT_CHECK_INTERVAL) {
            AxisAlignedBB bounds = new AxisAlignedBB(entity.getPosition()).grow(RIFT_INFLUENCE_RANGE);
            Collection<EntityRift> rifts = world.getEntitiesWithinAABB(EntityRift.class, bounds);
            this.closestRift = this.findClosestRift(entity, rifts);
            this.lastRiftCheck = time;
        }

        return this.closestRift;
    }

    @Nullable
    private EntityRift findClosestRift(Entity entity, Collection<EntityRift> rifts) {
        EntityRift closestRift = null;
        double closestRiftDistanceSq = Double.MAX_VALUE;
        for (EntityRift rift : rifts) {
            double distanceSq = rift.getDistanceSq(entity);
            if (distanceSq < closestRiftDistanceSq) {
                closestRiftDistanceSq = distanceSq;
                closestRift = rift;
            }
        }
        return closestRift;
    }

    private double computeAngle(Entity entity, BlockPos pos) {
        double sourceAngle = this.computeSourceAngle(entity);
        double targetAngle = Math.atan2(pos.getZ() - entity.posZ, pos.getX() - entity.posX) / (Math.PI * 2);
        return 0.5 - (sourceAngle - 0.25 - targetAngle);
    }

    private double computeSourceAngle(Entity entity) {
        double sourceAngle = entity.rotationYaw;
        if (entity instanceof EntityItemFrame) {
            sourceAngle = this.getFrameRotation((EntityItemFrame) entity);
        }
        return MathHelper.positiveModulo(sourceAngle / 360.0, 1.0);
    }

    private double getFrameRotation(EntityItemFrame itemFrame) {
        return MathHelper.wrapDegrees(180 + itemFrame.facingDirection.getHorizontalIndex() * 90);
    }
}
