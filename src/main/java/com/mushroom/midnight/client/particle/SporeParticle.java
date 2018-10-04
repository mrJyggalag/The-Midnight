package com.mushroom.midnight.client.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SporeParticle extends Particle {
    protected SporeParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        float shade = this.rand.nextFloat() * 0.1F + 0.9F;
        this.particleRed = shade;
        this.particleGreen = shade;
        this.particleBlue = shade;
        this.particleAlpha = 0.0F;
        this.setParticleTextureIndex(0);
        this.setSize(0.2F, 0.2F);
        this.particleScale *= this.rand.nextFloat() * 0.6F + 1.0F;
        this.motionX *= 0.1;
        this.motionY *= 0.1;
        this.motionZ *= 0.1;
        this.particleMaxAge = 200;
        this.canCollide = true;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.move(this.motionX, this.motionY, this.motionZ);

        this.motionX *= 0.99;
        this.motionY *= 0.99;
        this.motionZ *= 0.99;

        this.particleMaxAge--;

        if (this.particleMaxAge < 20) {
            this.particleAlpha = this.particleMaxAge / 20.0F;
        } else if (this.particleMaxAge >= 180) {
            this.particleAlpha = (200 - this.particleMaxAge) / 20.0F;
        } else {
            this.particleAlpha = 1.0F;
        }

        if (this.particleMaxAge <= 0 || this.onGround) {
            this.setExpired();
        }
    }

    @Override
    public int getBrightnessForRender(float p_189214_1_) {
        int skylight = 10;
        int blocklight = 5;
        return skylight << 20 | blocklight << 4;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
            return new SporeParticle(world, x, y, z, velocityX, velocityY, velocityZ);
        }
    }
}
