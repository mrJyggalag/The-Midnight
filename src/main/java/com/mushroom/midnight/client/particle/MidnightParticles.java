package com.mushroom.midnight.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public enum MidnightParticles {
    AMBIENT_SPORE, SPORE, DRIP, UNSTABLE_BUSH, SPORCH, FURNACE_FLAME, BOMB_EXPLOSION, FADING_SPORE;

    MidnightParticles() {
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    public Particle create(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        IParticleFactory factory = getFactory();
        return factory == null ? null : factory.createParticle(-1, world, x, y, z, velocityX, velocityY, velocityZ, parameters);
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    public IParticleFactory getFactory() {
        switch (this) {
            case AMBIENT_SPORE:
                return new AmbientSporeParticle.Factory();
            case SPORE:
                return new SporeParticle.Factory();
            case DRIP:
                return new DripParticle.Factory();
            case UNSTABLE_BUSH:
                return new UnstableBushParticle.Factory();
            case SPORCH:
                return new SporchParticle.Factory();
            case FURNACE_FLAME:
                return new FurnaceFlameParticle.Factory();
            case BOMB_EXPLOSION:
                return new BombExplosionParticle.Factory();
            case FADING_SPORE:
                return new FadingSporeParticle.Factory();
        }
        return null;
    }

    public void spawn(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        if (world.isRemote) {
            Particle particle = create(world, x, y, z, velocityX, velocityY, velocityZ, parameters);
            if (particle != null) {
                spawn(particle);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawn(Particle particle) {
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static MidnightParticles fromId(int id) {
        return values()[MathHelper.clamp(id, 0, values().length - 1)];
    }
}
