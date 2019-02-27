package com.mushroom.midnight.client.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.mushroom.midnight.client.particle.MidnightParticleSprites.*;

@SideOnly(Side.CLIENT)
public class UnstableBushParticle extends Particle {

    protected UnstableBushParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int fruitId) {
        super(world, posX, posY, posZ);
        setParticleTexture(getSprite(fruitId == 0 ? SpriteTypes.BLUE_UNSTABLE_BUSH : (fruitId == 1 ? SpriteTypes.LIME_UNSTABLE_BUSH : SpriteTypes.GREEN_UNSTABLE_BUSH)));
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.particleAlpha = 1f;
        particleScale = 1f;
        this.particleMaxAge = 60;
        this.canCollide = false;
        onUpdate();
    }

    @Override
    public void onUpdate() {
        this.particleAlpha = particleAge / (float)particleMaxAge;
        super.onUpdate();
    }

    @Override
    public int getBrightnessForRender(float p_189214_1_) {
        int skylight = 10;
        int blocklight = 5;
        return skylight << 20 | blocklight << 4;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World world, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ, int... params) {
            return new UnstableBushParticle(world, posX, posY, posZ, velocityX, velocityY, velocityZ, params.length > 0 ? params[0] : world.rand.nextInt(3));
        }
    }
}
