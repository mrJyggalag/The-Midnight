package com.mushroom.midnight.client.model;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.entity.creature.NightStagEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class NightStagModel extends QuadrupedModel<NightStagEntity> {
    public RendererModel snout;
    public RendererModel rightAntler;
    public RendererModel leftAntler;

    public NightStagModel() {
        super(15, 0f);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body = new RendererModel(this, 0, 32);
        this.body.setRotationPoint(0.0F, 8.0F, 16.0F);
        this.body.addBox(-5.0F, -6.0F, -20.0F, 10, 10, 22, 0.0F);
        this.leg1 = new RendererModel(this, 0, 36);
        this.leg1.setRotationPoint(-3.75F, 12.0F, 0.0F);
        this.leg1.addBox(-2.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
        this.leg2 = new RendererModel(this, 46, 31);
        this.leg2.setRotationPoint(-4.0F, 8.0F, 14.0F);
        this.leg2.addBox(-2.0F, -2.0F, -2.5F, 4, 18, 5, 0.0F);
        this.leftAntler = new RendererModel(this, 0, 0);
        this.leftAntler.mirror = true;
        this.leftAntler.setRotationPoint(1.5F, -15.0F, -1.0F);
        this.leftAntler.addBox(-1.0F, -17.0F, -6.0F, 1, 18, 12, 0.0F);
        this.setRotateAngle(this.leftAntler, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
        this.leg3 = new RendererModel(this, 0, 36);
        this.leg3.setRotationPoint(3.75F, 12.0F, 0.0F);
        this.leg3.addBox(-2.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
        this.leg4 = new RendererModel(this, 46, 31);
        this.leg4.setRotationPoint(4.0F, 8.0F, 14.0F);
        this.leg4.addBox(-2.0F, -2.0F, -2.5F, 4, 18, 5, 0.0F);
        this.head = new RendererModel(this, 32, 5);
        this.head.setRotationPoint(0.0F, 6.0F, -5.0F);
        this.head.addBox(-4.0F, -15.0F, -4.0F, 8, 18, 8, 0.0F);
        this.setRotateAngle(this.head, 0.17453292519943295F, 0.0F, 0.0F);
        this.rightAntler = new RendererModel(this, 0, 0);
        this.rightAntler.setRotationPoint(-1.5F, -15.0F, -1.0F);
        this.rightAntler.addBox(0.0F, -17.0F, -6.0F, 1, 18, 12, 0.0F);
        this.setRotateAngle(this.rightAntler, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
        this.snout = new RendererModel(this, 16, 0);
        this.snout.setRotationPoint(0.0F, -5.0F, -4.0F);
        this.snout.addBox(-3.0F, -5.0F, -6.0F, 6, 5, 6, 0.0F);
        this.setRotateAngle(this.snout, -0.17453292519943295F, 0.0F, 0.0F);
        this.head.addChild(this.leftAntler);
        this.head.addChild(this.rightAntler);
        this.head.addChild(this.snout);
    }

    @Override
    public void render(NightStagEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(0.0F, 0.0F, -0.4F);

        this.head.render(scale);
        this.body.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg3.render(scale);
        this.leg4.render(scale);

        GlStateManager.popMatrix();
    }

    private void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(NightStagEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.head.rotateAngleX = (headPitch * 0.017453292f) + 0.17453292519943295f;
        this.head.rotateAngleY = netHeadYaw * 0.017453292f;
        this.body.rotateAngleX = 0f;
        this.leg1.rotateAngleX = this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
        this.leg2.rotateAngleX = this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount;
        this.head.rotationPointX = 0f;
        this.leg3.rotateAngleZ = 0f;

        entity.getCapability(Midnight.ANIMATION_CAP, null).ifPresent(animationCap -> {
            if (animationCap.isAnimate()) {
                float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
                float progress = animationCap.getProgress(partialTicks);
                float fctAnimation;
                switch (animationCap.getAnimationType()) {
                    case ATTACK:
                        fctAnimation = MathHelper.sin((float) (progress * Math.PI));
                        this.body.rotateAngleX = fctAnimation * 0.2f;
                        this.head.rotateAngleX = 0.17453292519943295f + (fctAnimation * 1.5f);
                        break;
                    case CURTSEY:
                        fctAnimation = MathHelper.sin((float) (progress * Math.PI));
                        this.head.rotateAngleX = 0.17453292519943295f + fctAnimation;
                        this.body.rotateAngleX = this.leg1.rotateAngleX = this.leg3.rotateAngleZ = fctAnimation * 0.2f;
                        this.leg3.rotateAngleX = -this.leg3.rotateAngleZ;
                        break;
                    case EAT:
                        this.body.rotateAngleX = MathHelper.sin((float) (progress * Math.PI)) * 0.2f;
                        this.head.rotateAngleX = 0.17453292519943295f + (progress <= 0.1f ? progress * 15f : progress >= 0.9f ? (1f - progress) * 15f : 1.5f);
                        if (progress > 0.1f && progress <0.9f) {
                            this.head.rotationPointX = -partialTicks;
                        }
                        break;
                    case CHARGE:
                        fctAnimation = MathHelper.sin((float) ((progress % 1) * 10f * Math.PI));
                        this.head.rotateAngleX = 0.17453292519943295f + (progress <= 0.05f ? progress * 28f : progress >= 0.9f ? (1f - progress) * 14f : 1.4f + fctAnimation* 0.02f);
                        this.head.rotateAngleY = 0f;
                        this.body.rotateAngleX = fctAnimation * 0.05f;
                        if (progress <= 0.1f) {
                            this.leg1.rotateAngleX = this.leg1.rotateAngleY = fctAnimation * 0.3f;
                        }
                        break;
                }
            }
        });
    }
}
