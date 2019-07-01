package com.mushroom.midnight.client.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class SporeParticle extends MidnightParticle {
    protected SporeParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        float shade = this.rand.nextFloat() * 0.1F + 0.9F;
        this.particleRed = shade;
        this.particleGreen = shade;
        this.particleBlue = shade;
        this.particleAlpha = 1.0F;
        this.motionX = velocityX;
        this.motionY = velocityY;
        this.motionZ = velocityZ;
        this.setSize(0.2F, 0.2F);
        this.particleScale *= (this.rand.nextFloat() * 0.6F + 1.0F) * 0.7F;
        this.maxAge = 60;
        this.canCollide = true;
    }

    @Override
    ResourceLocation getTexture() {
        return MidnightParticleSprites.SPORE;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.motionX *= 0.98;
        this.motionY *= 0.98;
        this.motionZ *= 0.98;

        this.motionY -= 0.04;

        this.move(this.motionX, this.motionY, this.motionZ);

        if (this.maxAge-- <= 0 || this.onGround) {
            this.setExpired();
        }
    }

    @Override
    public int getBrightnessForRender(float p_189214_1_) {
        int skylight = 10;
        int blocklight = 5;
        return skylight << 20 | blocklight << 4;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory {
        @Nullable
        @Override
        public Particle makeParticle(IParticleData type, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SporeParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
