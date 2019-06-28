package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.client.particle.MidnightParticleSprites.SpriteTypes;
import com.mushroom.midnight.common.block.SporchBlock.SporchType;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SporchParticle extends Particle {
    private final float flameScale;
    private final List<TextureAtlasSprite> sprites = new ArrayList<>();
    private final int MAX_FRAME_ID = 2;
    private int currentFrame = 0;
    private boolean directionRight = true;
    private int lastTick = 0;

    public SporchParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int sporchType) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        this.motionX = this.motionX * 0.009999999776482582d + motionX;
        this.motionY = this.motionY * 0.009999999776482582d + motionY;
        this.motionZ = this.motionZ * 0.009999999776482582d + motionZ;
        this.posX += (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posY += (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posZ += (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.flameScale = this.particleScale = 1.3f;
        this.particleRed = 1f;
        this.particleGreen = 1f;
        this.particleBlue = 1f;
        this.particleMaxAge = (int) (8d / (Math.random() * 0.8d + 0.2d)) + 4;
        initTextures(SporchType.values()[Math.min(sporchType, SporchType.values().length)]);
        setParticleTexture(sprites.get(0));
    }

    public void move(double x, double y, double z) {
        setBoundingBox(this.getBoundingBox().offset(x, y, z));
        resetPositionToBB();
    }

    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        if (entity.ticksExisted >= this.lastTick + 5) {
            setParticleTexture(sprites.get(currentFrame));
            if (this.currentFrame == MAX_FRAME_ID) {
                this.directionRight = false;
            } else if (currentFrame == 0) {
                this.directionRight = true;
            }
            this.currentFrame = this.currentFrame + (directionRight ? 1 : -1);
            this.lastTick = entity.ticksExisted;
        }
        float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge;
        this.particleScale = this.flameScale * (1f - f * f * 0.5f);
        super.renderParticle(buffer, entity, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    public int getBrightnessForRender(float partialTick) {
        float f = ((float) this.particleAge + partialTick) / (float) this.particleMaxAge;
        f = MathHelper.clamp(f, 0f, 1f);
        int i = super.getBrightnessForRender(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j = j + (int) (f * 15f * 16f);
        if (j > 240) {
            j = 240;
        }
        return j | k << 16;
    }

    private void initTextures(SporchType sporchType) {
        switch (sporchType) {
            case BOGSHROOM:
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_BOGSHROOM_1));
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_BOGSHROOM_2));
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_BOGSHROOM_3));
                break;
            case DEWSHROOM:
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_DEWSHROOM_1));
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_DEWSHROOM_2));
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_DEWSHROOM_3));
                break;
            case NIGHTSHROOM:
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_NIGHTSHROOM_1));
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_NIGHTSHROOM_2));
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_NIGHTSHROOM_3));
                break;
            case VIRIDSHROOM: default:
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_VIRIDSHROOM_1));
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_VIRIDSHROOM_2));
                sprites.add(MidnightParticleSprites.getSprite(SpriteTypes.SPORCH_VIRIDSHROOM_3));
                break;
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int... params) {
            return new SporchParticle(world, posX, posY, posZ, motionX, motionY, motionZ, params.length > 0 ? params[0] : world.rand.nextInt(SporchType.values().length));
        }
    }
}
