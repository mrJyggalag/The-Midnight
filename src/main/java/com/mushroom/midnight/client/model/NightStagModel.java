package com.mushroom.midnight.client.model;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.creature.NightStagEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NightStagModel extends QuadrupedModel<NightStagEntity> {
    public RendererModel snout;
    public RendererModel rightAntler;
    public RendererModel leftAntler;

    public NightStagModel() {
        super(15, 0f);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.field_78148_b = new RendererModel(this, 0, 32); // body
        this.field_78148_b.setRotationPoint(0.0F, 8.0F, 16.0F);
        this.field_78148_b.addBox(-5.0F, -6.0F, -20.0F, 10, 10, 22, 0.0F);
        this.field_78149_c = new RendererModel(this, 0, 36); // leg1
        this.field_78149_c.setRotationPoint(-3.75F, 12.0F, 0.0F);
        this.field_78149_c.addBox(-2.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
        this.field_78146_d = new RendererModel(this, 46, 31); // leg2
        this.field_78146_d.setRotationPoint(-4.0F, 8.0F, 14.0F);
        this.field_78146_d.addBox(-2.0F, -2.0F, -2.5F, 4, 18, 5, 0.0F);
        this.leftAntler = new RendererModel(this, 0, 0);
        this.leftAntler.mirror = true;
        this.leftAntler.setRotationPoint(1.5F, -15.0F, -1.0F);
        this.leftAntler.addBox(-1.0F, -17.0F, -6.0F, 1, 18, 12, 0.0F);
        this.setRotateAngle(this.leftAntler, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
        this.field_78147_e = new RendererModel(this, 0, 36); // leg3
        this.field_78147_e.setRotationPoint(3.75F, 12.0F, 0.0F);
        this.field_78147_e.addBox(-2.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
        this.field_78144_f = new RendererModel(this, 46, 31); // leg4
        this.field_78144_f.setRotationPoint(4.0F, 8.0F, 14.0F);
        this.field_78144_f.addBox(-2.0F, -2.0F, -2.5F, 4, 18, 5, 0.0F);
        this.headModel = new RendererModel(this, 32, 5);
        this.headModel.setRotationPoint(0.0F, 6.0F, -5.0F);
        this.headModel.addBox(-4.0F, -15.0F, -4.0F, 8, 18, 8, 0.0F);
        this.setRotateAngle(this.headModel, 0.17453292519943295F, 0.0F, 0.0F);
        this.rightAntler = new RendererModel(this, 0, 0);
        this.rightAntler.setRotationPoint(-1.5F, -15.0F, -1.0F);
        this.rightAntler.addBox(0.0F, -17.0F, -6.0F, 1, 18, 12, 0.0F);
        this.setRotateAngle(this.rightAntler, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
        this.snout = new RendererModel(this, 16, 0);
        this.snout.setRotationPoint(0.0F, -5.0F, -4.0F);
        this.snout.addBox(-3.0F, -5.0F, -6.0F, 6, 5, 6, 0.0F);
        this.setRotateAngle(this.snout, -0.17453292519943295F, 0.0F, 0.0F);
        this.headModel.addChild(this.leftAntler);
        this.headModel.addChild(this.rightAntler);
        this.headModel.addChild(this.snout);
    }

    @Override
    public void render(NightStagEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(0.0F, 0.0F, -0.4F);

        this.headModel.render(scale);
        this.field_78148_b.render(scale);
        this.field_78149_c.render(scale);
        this.field_78146_d.render(scale);
        this.field_78147_e.render(scale);
        this.field_78144_f.render(scale);

        GlStateManager.popMatrix();
    }

    private void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(NightStagEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.headModel.rotateAngleX = (headPitch * 0.017453292f) + 0.17453292519943295f;
        this.headModel.rotateAngleY = netHeadYaw * 0.017453292f;
        this.field_78148_b.rotateAngleX = 0f;
        this.field_78149_c.rotateAngleX = this.field_78144_f.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
        this.field_78146_d.rotateAngleX = this.field_78147_e.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount;
        this.headModel.rotationPointX = 0f;
        this.field_78147_e.rotateAngleZ = 0f;

        entity.getCapability(Midnight.ANIMATION_CAP, null).ifPresent(animationCap -> {
            if (animationCap.isAnimate()) {
                float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
                float progress = animationCap.getProgress(partialTicks);
                float fctAnimation;
                switch (animationCap.getAnimationType()) {
                    case ATTACK:
                        fctAnimation = MathHelper.sin((float) (progress * Math.PI));
                        this.field_78148_b.rotateAngleX = fctAnimation * 0.2f;
                        this.headModel.rotateAngleX = 0.17453292519943295f + (fctAnimation * 1.5f);
                        break;
                    case CURTSEY:
                        fctAnimation = MathHelper.sin((float) (progress * Math.PI));
                        this.headModel.rotateAngleX = 0.17453292519943295f + fctAnimation;
                        this.field_78148_b.rotateAngleX = this.field_78149_c.rotateAngleX = this.field_78147_e.rotateAngleZ = fctAnimation * 0.2f;
                        this.field_78147_e.rotateAngleX = -this.field_78147_e.rotateAngleZ;
                        break;
                    case EAT:
                        this.field_78148_b.rotateAngleX = MathHelper.sin((float) (progress * Math.PI)) * 0.2f;
                        this.headModel.rotateAngleX = 0.17453292519943295f + (progress <= 0.1f ? progress * 15f : progress >= 0.9f ? (1f - progress) * 15f : 1.5f);
                        if (progress > 0.1f && progress <0.9f) {
                            this.headModel.rotationPointX = -partialTicks;
                        }
                        break;
                    case CHARGE:
                        fctAnimation = MathHelper.sin((float) ((progress % 1) * 10f * Math.PI));
                        this.headModel.rotateAngleX = 0.17453292519943295f + (progress <= 0.05f ? progress * 28f : progress >= 0.9f ? (1f - progress) * 14f : 1.4f + fctAnimation* 0.02f);
                        this.headModel.rotateAngleY = 0f;
                        this.field_78148_b.rotateAngleX = fctAnimation * 0.05f;
                        if (progress <= 0.1f) {
                            this.field_78149_c.rotateAngleX = this.field_78149_c.rotateAngleY = fctAnimation * 0.3f;
                        }
                        break;
                }
            }
        });
    }
}
