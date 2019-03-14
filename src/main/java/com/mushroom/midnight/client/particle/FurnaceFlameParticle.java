package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.client.particle.MidnightParticleSprites.SpriteTypes;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFlame;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FurnaceFlameParticle extends ParticleFlame {
    private int layer = 0;

    protected FurnaceFlameParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        layer = 1;
        setParticleTexture(MidnightParticleSprites.getSprite(SpriteTypes.FURNACE_FLAME));
    }

    @Override
    public int getFXLayer() {
        return layer;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int... params) {
            return new FurnaceFlameParticle(world, posX, posY, posZ, motionX, motionY, motionZ);
        }
    }
}
