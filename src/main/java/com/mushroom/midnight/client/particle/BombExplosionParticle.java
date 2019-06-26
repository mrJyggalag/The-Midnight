package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.common.helper.Helper;
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
import net.minecraftforge.fml.relauncher.OnlyIn;

import javax.annotation.Nullable;

import static com.mushroom.midnight.Midnight.MODID;

@OnlyIn(Dist.CLIENT)
public class BombExplosionParticle extends Particle {
    private static final ResourceLocation EXPLOSION_TEXTURE = new ResourceLocation(MODID, "textures/particles/bomb_explosion.png");
    private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
    private int life;
    private final int lifeTime;

    private final TextureManager textureManager;
    private final float size;

    protected BombExplosionParticle(TextureManager textureManager, World world, double x, double y, double z, double scale, double unused1, double unused2, int color) {
        super(world, x, y, z, 0d, 0d, 0d);
        this.textureManager = textureManager;
        this.lifeTime = 6 + this.rand.nextInt(4);
        //float f = this.rand.nextFloat() * 0.6f + 0.4f;
        float[] rgbF = Helper.getRGBColorF(color);
        setRBGColorF(rgbF[0], rgbF[1], rgbF[2]);
        this.size = 1f - (float) scale * 0.5f;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        int i = (int) (((float) this.life + partialTicks) * 15f / (float) this.lifeTime);
        if (i <= 15) {
            this.textureManager.bindTexture(EXPLOSION_TEXTURE);
            double minU = (i % 4) / 4d;
            double maxU = minU + 0.24975d;
            double minV = (float)(i / 4) / 4d;
            double maxV = minV + 0.24975d;
            double scale = 2d * this.size;
            int skyLight = 0;
            int blockLight = 240;
            double x = this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX;
            double y = this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY;
            double z = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ;
            GlStateManager.color(1f, 1f, 1f, 1f);
            GlStateManager.disableLighting();
            RenderHelper.disableStandardItemLighting();
            buffer.begin(7, VERTEX_FORMAT);
            buffer.pos(x - rotationX * scale - rotationXY * scale, y - rotationZ * scale, z - rotationYZ * scale - rotationXZ * scale)
                    .tex(maxU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, 1f).lightmap(skyLight, blockLight).normal(0f, 1f, 0f).endVertex();
            buffer.pos(x - rotationX * scale + rotationXY * scale, y + rotationZ * scale, z - rotationYZ * scale + rotationXZ * scale)
                    .tex(maxU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, 1f).lightmap(skyLight, blockLight).normal(0f, 1f, 0f).endVertex();
            buffer.pos(x + rotationX * scale + rotationXY * scale, y + rotationZ * scale, z + rotationYZ * scale + rotationXZ * scale)
                    .tex(minU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, 1f).lightmap(skyLight, blockLight).normal(0f, 1f, 0f).endVertex();
            buffer.pos(x + rotationX * scale - rotationXY * scale, y - rotationZ * scale, z + rotationYZ * scale - rotationXZ * scale)
                    .tex(minU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, 1f).lightmap(skyLight, blockLight).normal(0f, 1f, 0f).endVertex();
            Tessellator.getInstance().draw();
            GlStateManager.enableLighting();
        }
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        return 61680;
    }

    @Override
    public void tick() {
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

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        @Nullable
        public Particle createParticle(int particleID, World world, double x, double y, double z, double scale, double unused1, double unused2, int... params) {
            return new BombExplosionParticle(Minecraft.getInstance().getTextureManager(), world, x, y, z, scale, 0d, 0d, params.length > 0 ? params[0] : 0xffffff);
        }
    }
}
