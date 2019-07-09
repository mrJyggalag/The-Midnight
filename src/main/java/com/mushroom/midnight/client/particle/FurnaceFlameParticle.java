package com.mushroom.midnight.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FurnaceFlameParticle extends MidnightParticle {

    public FurnaceFlameParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        this.motionX = this.motionX * 0.009999999776482582d + motionX;
        this.motionY = this.motionY * 0.009999999776482582d + motionY;
        this.motionZ = this.motionZ * 0.009999999776482582d + motionZ;
        this.posX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.maxAge = (int)(8.0d / (Math.random() * 0.8d + 0.2d)) + 4;
    }

    public void move(double x, double y, double z) {
        setBoundingBox(this.getBoundingBox().offset(x, y, z));
        resetPositionToBB();
    }

    public float func_217561_b(float partialTicks) {
        float ratio = ((float)this.age + partialTicks) / (float)this.maxAge;
        return this.particleScale * (1f - ratio * ratio * 0.5f);
    }

    public int getBrightnessForRender(float partialTicks) {
        float ratio = ((float)this.age + partialTicks) / (float)this.maxAge;
        ratio = MathHelper.clamp(ratio, 0f, 1f);
        int i = super.getBrightnessForRender(partialTicks);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(ratio * 15f * 16f);
        if (j > 240) {
            j = 240;
        }
        return j | k << 16;
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            setExpired();
        } else {
            move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9599999785423279d;
            this.motionY *= 0.9599999785423279d;
            this.motionZ *= 0.9599999785423279d;
            if (this.onGround) {
                this.motionX *= 0.699999988079071d;
                this.motionZ *= 0.699999988079071d;
            }

        }
    }

    @Override
    ResourceLocation getTexture() {
        return MidnightParticleSprites.FURNACE_FLAME;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticle {
        @Override
        public Particle makeParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... params) {
            return new FurnaceFlameParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
