package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.common.entities.EntityRift;
import com.mushroom.midnight.common.util.MatrixStack;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.MathHelper;

import javax.vecmath.Point3d;

public class RiftParticle extends Particle {
    private static final int TRANSITION_TIME = 20;
    private static final float MIN_RETURN_DISTANCE = 0.2F;

    private static final int MIN_RETURN_CHANCE = 400;
    private static final int RETURN_DECAY_TIME = EntityRift.UNSTABLE_TIME + EntityRift.CLOSE_TIME - TRANSITION_TIME - 2;

    private final EntityRift rift;
    private final EntityRift.Ring ring;

    private final Point3d origin;

    private final float radius;
    private final float angleOffset;
    private final float verticalOffset;
    private final float rotateSpeed;

    private final MatrixStack matrix = new MatrixStack(4);

    private int transitionTime;
    private boolean returning;

    public RiftParticle(EntityRift rift, double x, double y, double z, EntityRift.Ring ring, float radius, float angleOffset, float verticalOffset, float rotateSpeed) {
        super(rift.world, x, y, z);
        this.setSize(0.2F, 0.2F);
        this.setParticleTextureIndex(0);

        this.rift = rift;
        this.radius = radius;
        this.angleOffset = angleOffset;
        this.verticalOffset = verticalOffset;
        this.rotateSpeed = rotateSpeed;
        this.ring = ring;

        this.origin = new Point3d(x, y, z);

        float shade = this.rand.nextFloat() * 0.1F + 0.2F;
        this.particleRed = shade;
        this.particleGreen = shade;
        this.particleBlue = shade;

        this.particleScale *= this.rand.nextFloat() * 0.6F + 2.0F;

        this.canCollide = false;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.shouldExpire()) {
            this.setExpired();
            this.rift.returnParticle();
            return;
        }

        // When stable, we have a return chance of MIN_RETURN_CHANCE, and by RETURN_DECAY_TIME, and chance of 1
        int gradient = (MIN_RETURN_CHANCE / RETURN_DECAY_TIME);
        int returnChance = MathHelper.clamp(gradient * (RETURN_DECAY_TIME - this.rift.unstableTime), 1, MIN_RETURN_CHANCE);
        if (this.rand.nextInt(returnChance) == 0) {
            this.returning = true;
        }

        this.updateTransitionTimer();

        float transitionAnimation = (float) this.transitionTime / TRANSITION_TIME;
        transitionAnimation = (float) (1.0F - Math.pow(1.0F - transitionAnimation, 3.0F));

        Point3d target = this.computeTarget();
        this.posX = this.origin.x + (target.x - this.origin.x) * transitionAnimation;
        this.posY = this.origin.y + (target.y - this.origin.y) * transitionAnimation;
        this.posZ = this.origin.z + (target.z - this.origin.z) * transitionAnimation;

        this.particleAge++;
    }

    private void updateTransitionTimer() {
        if (this.returning && this.transitionTime > 0) {
            this.transitionTime--;
        } else if (this.transitionTime < TRANSITION_TIME) {
            this.transitionTime++;
        }
    }

    private boolean shouldExpire() {
        if (this.rift.isDead) {
            return true;
        }
        if (this.returning) {
            double deltaX = this.posX - this.origin.x;
            double deltaY = this.posY - this.origin.y;
            double deltaZ = this.posZ - this.origin.z;
            double distanceSq = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
            return distanceSq < MIN_RETURN_DISTANCE * MIN_RETURN_DISTANCE;
        }
        return false;
    }

    private Point3d computeTarget() {
        this.matrix.push();
        this.matrix.rotate(this.ring.getTiltX(), 1.0F, 0.0F, 0.0F);
        this.matrix.rotate(this.ring.getTiltZ(), 0.0F, 0.0F, 1.0F);
        this.matrix.rotate(this.particleAge * this.rotateSpeed + this.angleOffset, 0.0F, 1.0F, 0.0F);

        Point3d point = new Point3d(this.radius, this.verticalOffset, 0.0);
        this.matrix.transform(point);

        this.matrix.pop();

        double targetX = this.rift.posX + point.x;
        double targetY = this.rift.posY + this.rift.height / 2.0F + point.y;
        double targetZ = this.rift.posZ + point.z;
        return new Point3d(targetX, targetY, targetZ);
    }
}
