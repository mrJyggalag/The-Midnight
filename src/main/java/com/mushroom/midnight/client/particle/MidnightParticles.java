package com.mushroom.midnight.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public enum MidnightParticles {
    AMBIENT_SPORE(new AmbientSporeParticle.Factory()),
    SPORE(new SporeParticle.Factory()),
    DRIP(new DripParticle.Factory()),
    UNSTABLE_BUSH(new UnstableBushParticle.Factory()),
    SPORCH(new SporchParticle.Factory()),
    FURNACE_FLAME(new FurnaceFlameParticle.Factory());

    IParticleFactory factory;

    MidnightParticles(IParticleFactory factory) {
        this.factory = factory;
    }

    public Particle create(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        return this.factory.createParticle(-1, world, x, y, z, velocityX, velocityY, velocityZ, parameters);
    }

    public void spawn(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        if (!world.isRemote) {
            return;
        }
        Particle particle = this.create(world, x, y, z, velocityX, velocityY, velocityZ, parameters);
        this.spawn(particle);
    }

    private void spawn(Particle particle) {
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static MidnightParticles fromId(int id) {
        return values()[MathHelper.clamp(id, 0, values().length - 1)];
    }
}
