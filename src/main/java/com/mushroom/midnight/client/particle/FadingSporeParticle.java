package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.common.helper.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.mushroom.midnight.Midnight.MODID;

@SideOnly(Side.CLIENT)
public class FadingSporeParticle extends Particle {
    private static final ResourceLocation FADING_SPORE_TEXTURE = new ResourceLocation(MODID, "textures/particles/fading_spore.png");
    private final float scaleMax;

    public FadingSporeParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ, int color) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        float[] rgbF = Helper.getRGBColorF(color);
        setRBGColorF(rgbF[0] * (1f - this.rand.nextFloat() * 0.25f), rgbF[1] * (1f - this.rand.nextFloat() * 0.25f), rgbF[2] * (1f - this.rand.nextFloat() * 0.25f));
        setSize(0.2f, 0.2f);
        this.particleAlpha = 1f;
        this.particleScale = 0f;
        this.scaleMax = world.rand.nextFloat() + 0.5f;
        this.particleMaxAge = (int) (8d / (Math.random() * 0.8d + 0.2d)) + 4;
        this.canCollide = true;
        this.particleGravity = 0f;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionX *= 0.9d;
        this.motionZ *= 0.9d;
        if (++this.particleAge >= this.particleMaxAge) {
            setExpired();
        } else {
            move(this.motionX, this.motionY, this.motionZ);
            float ratio = this.particleAge / (float) this.particleMaxAge;
            if (ratio <= 0.25f) {
                this.particleScale = this.scaleMax * ratio * 4f;
            } else {
                float ratio2 = ratio / 0.75f;
                this.particleAlpha = ratio2;
                this.particleScale = this.scaleMax * (1f - ratio2);
            }
        }
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float scale = 0.1f * this.particleScale;
        int skyLight = 0;
        int blockLight = 240;
        double x = this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX;
        double y = this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY;
        double z = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ;
        Minecraft.getMinecraft().getTextureManager().bindTexture(FADING_SPORE_TEXTURE);
        buffer.pos(x + (-rotationX * scale - rotationXY * scale), y + -rotationZ * scale, z + (-rotationYZ * scale - rotationXZ * scale))
                .tex(0d, 1d).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
                .lightmap(skyLight, blockLight).endVertex();
        buffer.pos(x + (-rotationX * scale + rotationXY * scale), y + (rotationZ * scale), z + (-rotationYZ * scale + rotationXZ * scale))
                .tex(1d, 1d).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
                .lightmap(skyLight, blockLight).endVertex();
        buffer.pos(x + (rotationX * scale + rotationXY * scale), y + (rotationZ * scale), z + (rotationYZ * scale + rotationXZ * scale))
                .tex(1d, 0d).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
                .lightmap(skyLight, blockLight).endVertex();
        buffer.pos(x + (rotationX * scale - rotationXY * scale), y + (-rotationZ * scale), z + (rotationYZ * scale - rotationXZ * scale))
                .tex(0d, 0d).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
                .lightmap(skyLight, blockLight).endVertex();
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        @Nullable
        public Particle createParticle(int particleID, World world, double x, double y, double z, double motionX, double motionY, double motionZ, int... params) {
            return new FadingSporeParticle(world, x, y, z, motionX, motionY, motionZ, params.length > 0 ? params[0] : 0xffffff);
        }
    }
}
