package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.common.entities.EntityRift;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.MathHelper;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

public class RiftParticle extends Particle {
    private static final float ORBITAL_DISTANCE = 2.5F;

    private final EntityRift rift;
    private final float orbitalOffset;
    private final float orbitalInclination;

    private final Matrix4f matrix = new Matrix4f();

    public RiftParticle(EntityRift rift, double x, double y, double z, float orbitalOffset, float orbitalInclination) {
        super(rift.world, x, y, z);
        this.setSize(0.2F, 0.2F);
        this.setParticleTextureIndex(7);

        this.rift = rift;
        this.orbitalOffset = orbitalOffset;
        this.orbitalInclination = orbitalInclination;

        float shade = this.rand.nextFloat() * 0.1F + 0.4F;
        this.particleRed = shade;
        this.particleGreen = shade;
        this.particleBlue = shade;

        this.particleScale *= this.rand.nextFloat() * 0.6F + 1.0F;

        this.canCollide = false;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.rift.isDead) {
            this.setExpired();
            return;
        }

        Point3d targetDelta = this.computeTarget();
        targetDelta.sub(new Point3d(this.posX, this.posY, this.posZ));

        this.posX += targetDelta.x * 0.1F;
        this.posY += targetDelta.y * 0.1F;
        this.posZ += targetDelta.z * 0.1F;

        this.particleAge++;
    }

    private Point3d computeTarget() {
        this.matrix.setIdentity();
        this.matrix.rotZ(this.orbitalInclination);
        this.matrix.rotY(this.particleAge * 3.0F + this.orbitalOffset);

        Point3f point = new Point3f(ORBITAL_DISTANCE, 0.0F, 0.0F);
        this.matrix.transform(point);

        double targetX = this.rift.posX + displacementX;
        double targetZ = this.rift.posZ + displacementZ;
        return new Point3d(targetX, this.rift.posY + this.rift.height / 2.0F, targetZ);
    }
}
