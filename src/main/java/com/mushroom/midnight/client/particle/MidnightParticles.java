package com.mushroom.midnight.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum MidnightParticles {
    AMBIENT_SPORE, SPORE, DRIP, UNSTABLE_BUSH, SPORCH, FURNACE_FLAME, BOMB_EXPLOSION, FADING_SPORE;

    MidnightParticles() {
    }

    @OnlyIn(Dist.CLIENT)
    public Particle create(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... params) {
        return getFactory().makeParticle(world, x, y, z, velocityX, velocityY, velocityZ, params);
    }

    @OnlyIn(Dist.CLIENT)
    public IParticle getFactory() {
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
        return new AmbientSporeParticle.Factory();
    }

    public static MidnightParticles getDefaultParticle() {
        return AMBIENT_SPORE;
    }

    public void spawn(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        if (world.isRemote) {
            spawn(create(world, x, y, z, velocityX, velocityY, velocityZ, parameters));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void spawn(Particle particle) {
        Minecraft.getInstance().particles.addEffect(particle);
    }

    public static MidnightParticles fromId(int id) {
        return values()[MathHelper.clamp(id, 0, values().length - 1)];
    }
}
