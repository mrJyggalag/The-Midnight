package com.mushroom.midnight.client.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UnstableBushParticle extends MidnightParticle {
    private final int fruitId;

    protected UnstableBushParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int fruitId) {
        super(world, posX, posY, posZ);
        this.fruitId = fruitId;

        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.particleAlpha = 1f;
        particleScale = 1f;
        this.maxAge = 60;
        this.canCollide = false;
    }

    @Override
    public void tick() {
        this.particleAlpha = age / (float)maxAge;
        super.tick();
    }

    @Override
    public int getBrightnessForRender(float p_189214_1_) {
        int skylight = 10;
        int blocklight = 5;
        return skylight << 20 | blocklight << 4;
    }

    @Override
    ResourceLocation getTexture() {
        return fruitId == 0 ? MidnightParticleSprites.BLUE_UNSTABLE_BUSH : (fruitId == 1 ? MidnightParticleSprites.LIME_UNSTABLE_BUSH : MidnightParticleSprites.GREEN_UNSTABLE_BUSH);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World world, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ, int... params) {
            return new UnstableBushParticle(world, posX, posY, posZ, velocityX, velocityY, velocityZ, params.length > 0 ? params[0] : world.rand.nextInt(3));
        }
    }
}
