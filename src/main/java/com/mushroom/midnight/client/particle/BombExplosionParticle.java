package com.mushroom.midnight.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.awt.Color;

import static com.mushroom.midnight.Midnight.MODID;

@SideOnly(Side.CLIENT)
public class BombExplosionParticle extends Particle {
    private static final ResourceLocation EXPLOSION_TEXTURE = new ResourceLocation(MODID, "textures/particles/bomb_explosion.png");
    private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
    private int life;
    private final int lifeTime;

    private final TextureManager textureManager;
    private final float size;

    protected BombExplosionParticle(TextureManager textureManager, World world, double x, double y, double z, double scale, double unused1, double unused2, int colorCode) {
        super(world, x, y, z, 0d, 0d, 0d);
        this.textureManager = textureManager;
        this.lifeTime = 6 + this.rand.nextInt(4);
        //float f = this.rand.nextFloat() * 0.6f + 0.4f;
        Color color = new Color(colorCode);
        this.particleRed = color.getRed() / 255f;
        this.particleGreen = color.getGreen() / 255f;
        this.particleBlue = color.getBlue() / 255f;
        this.size = 1f - (float) scale * 0.5f;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        int i = (int) (((float) this.life + partialTicks) * 15f / (float) this.lifeTime);
        if (i <= 15) {
            this.textureManager.bindTexture(EXPLOSION_TEXTURE);
            double f = (i % 4) / 4d;
            double f1 = f + 0.24975d;
            double f2 = (float) (i / 4) / 4d;
            double f3 = f2 + 0.24975d;
            double f4 = 2d * this.size;
            float posX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
            float posY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
            float posZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);
            GlStateManager.color(1f, 1f, 1f, 1f);
            GlStateManager.disableLighting();
            RenderHelper.disableStandardItemLighting();
            buffer.begin(7, VERTEX_FORMAT);
            buffer.pos(posX - rotationX * f4 - rotationXY * f4, posY - rotationZ * f4, posZ - rotationYZ * f4 - rotationXZ * f4).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1f).lightmap(0, 240).normal(0f, 1f, 0f).endVertex();
            buffer.pos(posX - rotationX * f4 + rotationXY * f4, posY + rotationZ * f4, posZ - rotationYZ * f4 + rotationXZ * f4).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1f).lightmap(0, 240).normal(0f, 1f, 0f).endVertex();
            buffer.pos(posX + rotationX * f4 + rotationXY * f4, posY + rotationZ * f4, posZ + rotationYZ * f4 + rotationXZ * f4).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1f).lightmap(0, 240).normal(0f, 1f, 0f).endVertex();
            buffer.pos(posX + rotationX * f4 - rotationXY * f4, posY - rotationZ * f4, posZ + rotationYZ * f4 - rotationXZ * f4).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1f).lightmap(0, 240).normal(0f, 1f, 0f).endVertex();
            Tessellator.getInstance().draw();
            GlStateManager.enableLighting();
        }
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        return 61680;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.life;
        if (this.life == this.lifeTime) {
            setExpired();
        }
    }

    @Override
    public int getFXLayer() {
        return 3;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        @Nullable
        public Particle createParticle(int particleID, World world, double x, double y, double z, double scale, double unused1, double unused2, int... params) {
            return new BombExplosionParticle(Minecraft.getMinecraft().getTextureManager(), world, x, y, z, scale, 0d, 0d, params.length > 0 ? params[0] : 0xffffff);
        }
    }
}
