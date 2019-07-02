package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.common.block.SporchBlock.SporchType;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class SporchParticle extends MidnightParticle {
    private final float flameScale;
    //private final List<ResourceLocation> sprites = new ArrayList<>();
    private final int MAX_FRAME_ID = 2;
    private final SporchType sporchType;
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
        this.maxAge = (int) (8d / (Math.random() * 0.8d + 0.2d)) + 4;
        this.sporchType = SporchType.values()[sporchType];
        //initTextures(SporchType.values()[Math.min(sporchType, SporchType.values().length)]);
        //setParticleTexture(sprites.get(0));
    }

    @Override
    public void move(double x, double y, double z) {
        setBoundingBox(this.getBoundingBox().offset(x, y, z));
        resetPositionToBB();
    }

    @Override
    public void renderParticle(BufferBuilder buffer, ActiveRenderInfo activeInfo, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        Entity entity = activeInfo.getRenderViewEntity();
        if (entity.ticksExisted >= this.lastTick + 5) {
            if (this.currentFrame == MAX_FRAME_ID) {
                this.directionRight = false;
            } else if (currentFrame == 0) {
                this.directionRight = true;
            }
            this.currentFrame = this.currentFrame + (directionRight ? 1 : -1);
            this.lastTick = entity.ticksExisted;
        }
        float f = ((float) this.age + partialTicks) / (float) this.maxAge;
        this.particleScale = this.flameScale * (1f - f * f * 0.5f);
        super.renderParticle(buffer, activeInfo, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        float f = ((float) this.age + partialTick) / (float) this.maxAge;
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

    @Override
    ResourceLocation getTexture() {
        return MidnightParticleSprites.SPORCHES.get(sporchType).get(currentFrame);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory {
        @Nullable
        @Override
        public Particle makeParticle(IParticleData type, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            // TODO prams , params.length > 0 ? params[0] : world.rand.nextInt(SporchType.values().length)
            return new SporchParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, world.rand.nextInt(SporchType.values().length));
        }
    }
}
