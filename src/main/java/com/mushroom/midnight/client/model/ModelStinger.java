package com.mushroom.midnight.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelStinger extends ModelBase {
    private ModelRenderer stingerLeg5;
    private ModelRenderer stingerLeg6;
    private ModelRenderer stingerLeg7;
    private ModelRenderer stingerLeg8;
    private ModelRenderer stingerLeg1;
    private ModelRenderer stingerLeg2;
    private ModelRenderer stingerLeg3;
    private ModelRenderer stingerLeg4;
    private ModelRenderer stingerHead;
    private ModelRenderer stingerBody;
    private ModelRenderer bodyEnd;
    private ModelRenderer stingerBody2;
    private ModelRenderer bodyTail1;
    private ModelRenderer bodyTail2;
    private ModelRenderer bodyTail3;
    private ModelRenderer bodyTail4;
    private ModelRenderer armRight;
    private ModelRenderer armLeft;
    private ModelRenderer PincerRightBack;
    private ModelRenderer PincerRightInt;
    private ModelRenderer PincerRightExt;
    private ModelRenderer PincerRightInt2;
    private ModelRenderer PincerExt2;
    private ModelRenderer PincerLeftBack;
    private ModelRenderer PincerRightInt_1;
    private ModelRenderer PincerRightExt_1;
    private ModelRenderer PincerRightInt2_1;
    private ModelRenderer PincerExt2_1;

    public ModelStinger() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.stingerLeg7 = new ModelRenderer(this, 18, 0);
        this.stingerLeg7.setRotationPoint(-4.0F, 15.0F, -1.0F);
        this.stingerLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(stingerLeg7, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
        this.armRight = new ModelRenderer(this, 7, 17);
        this.armRight.setRotationPoint(-3.3F, 1.0F, -5.0F);
        this.armRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
        this.setRotateAngle(armRight, 0.2617993877991494F, 0.4363323129985824F, 0.6981317007977318F);
        this.bodyTail3 = new ModelRenderer(this, 9, 17);
        this.bodyTail3.setRotationPoint(0.0F, 0.5F, 3.5F);
        this.bodyTail3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(bodyTail3, 0.6981317007977318F, 0.0F, 0.0F);
        this.stingerLeg6 = new ModelRenderer(this, 18, 0);
        this.stingerLeg6.setRotationPoint(4.0F, 15.0F, 0.0F);
        this.stingerLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(stingerLeg6, 0.0F, 0.39269908169872414F, 0.5811946409141118F);
        this.PincerRightInt2_1 = new ModelRenderer(this, 47, 29);
        this.PincerRightInt2_1.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.PincerRightInt2_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.PincerRightInt = new ModelRenderer(this, 23, 17);
        this.PincerRightInt.setRotationPoint(4.0F, 0.0F, -9.0F);
        this.PincerRightInt.addBox(0.0F, 0.0F, 0.0F, 2, 1, 10, 0.0F);
        this.stingerLeg4 = new ModelRenderer(this, 18, 0);
        this.stingerLeg4.setRotationPoint(4.0F, 15.0F, 1.0F);
        this.stingerLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(stingerLeg4, 0.0F, -0.39269908169872414F, 0.5811946409141118F);
        this.PincerRightExt = new ModelRenderer(this, 25, 19);
        this.PincerRightExt.setRotationPoint(-1.0F, 0.0F, -8.0F);
        this.PincerRightExt.addBox(0.0F, 0.0F, 0.0F, 2, 1, 8, 0.0F);
        this.PincerRightInt2 = new ModelRenderer(this, 47, 29);
        this.PincerRightInt2.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.PincerRightInt2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.PincerRightBack = new ModelRenderer(this, 9, 23);
        this.PincerRightBack.setRotationPoint(-5.0F, 0.0F, -1.0F);
        this.PincerRightBack.addBox(0.0F, 0.0F, 0.0F, 5, 1, 2, 0.0F);
        this.stingerLeg8 = new ModelRenderer(this, 18, 0);
        this.stingerLeg8.setRotationPoint(4.0F, 15.0F, -1.0F);
        this.stingerLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(stingerLeg8, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
        this.PincerLeftBack = new ModelRenderer(this, 9, 23);
        this.PincerLeftBack.setRotationPoint(-5.0F, 0.0F, -1.0F);
        this.PincerLeftBack.addBox(0.0F, 0.0F, 0.0F, 5, 1, 2, 0.0F);
        this.PincerRightExt_1 = new ModelRenderer(this, 25, 19);
        this.PincerRightExt_1.setRotationPoint(-1.0F, 0.0F, -8.0F);
        this.PincerRightExt_1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 8, 0.0F);
        this.stingerBody = new ModelRenderer(this, 0, 12);
        this.stingerBody.setRotationPoint(2.0F, 15.0F, 9.0F);
        this.stingerBody.addBox(-5.0F, -1.0F, -6.0F, 6, 4, 10, 0.0F);
        this.PincerExt2_1 = new ModelRenderer(this, 56, 30);
        this.PincerExt2_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.PincerExt2_1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.setRotateAngle(PincerExt2_1, 0.0F, 0.8726646259971648F, 0.0F);
        this.stingerLeg5 = new ModelRenderer(this, 18, 0);
        this.stingerLeg5.setRotationPoint(-4.0F, 15.0F, 0.0F);
        this.stingerLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(stingerLeg5, 0.0F, -0.39269908169872414F, -0.5811946409141118F);
        this.bodyEnd = new ModelRenderer(this, 4, 15);
        this.bodyEnd.setRotationPoint(-4.5F, -0.2F, 2.6F);
        this.bodyEnd.addBox(0.0F, 0.0F, 0.0F, 5, 3, 7, 0.0F);
        this.setRotateAngle(bodyEnd, 0.5235987755982988F, 0.0F, 0.0F);
        this.stingerLeg1 = new ModelRenderer(this, 18, 0);
        this.stingerLeg1.setRotationPoint(-4.0F, 15.0F, 2.0F);
        this.stingerLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(stingerLeg1, 0.0F, 0.7853981633974483F, -0.7853981633974483F);
        this.bodyTail1 = new ModelRenderer(this, 7, 17);
        this.bodyTail1.setRotationPoint(0.5F, 0.6F, 4.9F);
        this.bodyTail1.addBox(0.0F, 0.0F, 0.0F, 4, 3, 5, 0.0F);
        this.setRotateAngle(bodyTail1, 0.6981317007977318F, 0.0F, 0.0F);
        this.armLeft = new ModelRenderer(this, 7, 17);
        this.armLeft.setRotationPoint(10.0F, 1.6F, -5.3F);
        this.armLeft.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
        this.setRotateAngle(armLeft, -0.2617993877991494F, 0.4363323129985824F, 2.443460952792061F);
        this.PincerExt2 = new ModelRenderer(this, 47, 30);
        this.PincerExt2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.PincerExt2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.setRotateAngle(PincerExt2, 0.0F, 0.8726646259971648F, 0.0F);
        this.stingerHead = new ModelRenderer(this, 0, 0);
        this.stingerHead.setRotationPoint(0.0F, 15.0F, -10.0F);
        this.stingerHead.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.bodyTail4 = new ModelRenderer(this, 47, 24);
        this.bodyTail4.setRotationPoint(0.5F, 0.6F, 4.8F);
        this.bodyTail4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(bodyTail4, 0.2617993877991494F, 0.0F, 0.0F);
        this.PincerRightInt_1 = new ModelRenderer(this, 23, 17);
        this.PincerRightInt_1.setRotationPoint(4.0F, 0.0F, -9.0F);
        this.PincerRightInt_1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 10, 0.0F);
        this.stingerLeg3 = new ModelRenderer(this, 18, 0);
        this.stingerLeg3.setRotationPoint(-4.0F, 15.0F, 1.0F);
        this.stingerLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(stingerLeg3, 0.0F, 0.39269908169872414F, -0.5811946409141118F);
        this.stingerBody2 = new ModelRenderer(this, 0, 12);
        this.stingerBody2.setRotationPoint(-5.0F, -1.0F, -16.0F);
        this.stingerBody2.addBox(0.0F, 0.0F, 0.0F, 6, 4, 10, 0.0F);
        this.stingerLeg2 = new ModelRenderer(this, 18, 0);
        this.stingerLeg2.setRotationPoint(4.0F, 15.0F, 2.0F);
        this.stingerLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(stingerLeg2, 0.0F, -0.7853981633974483F, 0.7853981633974483F);
        this.bodyTail2 = new ModelRenderer(this, 9, 17);
        this.bodyTail2.setRotationPoint(1.0F, 1.0F, 3.1F);
        this.bodyTail2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(bodyTail2, 0.8726646259971648F, 0.0F, 0.0F);
        this.stingerBody2.addChild(this.armRight);
        this.bodyTail2.addChild(this.bodyTail3);
        this.PincerRightInt_1.addChild(this.PincerRightInt2_1);
        this.PincerRightBack.addChild(this.PincerRightInt);
        this.PincerRightBack.addChild(this.PincerRightExt);
        this.PincerRightInt.addChild(this.PincerRightInt2);
        this.armRight.addChild(this.PincerRightBack);
        this.armLeft.addChild(this.PincerLeftBack);
        this.PincerLeftBack.addChild(this.PincerRightExt_1);
        this.PincerRightExt_1.addChild(this.PincerExt2_1);
        this.stingerBody.addChild(this.bodyEnd);
        this.bodyEnd.addChild(this.bodyTail1);
        this.stingerBody2.addChild(this.armLeft);
        this.PincerRightExt.addChild(this.PincerExt2);
        this.bodyTail3.addChild(this.bodyTail4);
        this.PincerLeftBack.addChild(this.PincerRightInt_1);
        this.stingerBody.addChild(this.stingerBody2);
        this.bodyTail1.addChild(this.bodyTail2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.stingerLeg7.render(f5);
        this.stingerLeg6.render(f5);
        this.stingerLeg4.render(f5);
        this.stingerLeg8.render(f5);
        this.stingerBody.render(f5);
        this.stingerLeg5.render(f5);
        this.stingerLeg1.render(f5);
        this.stingerHead.render(f5);
        this.stingerLeg3.render(f5);
        this.stingerLeg2.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.stingerHead.rotateAngleY = netHeadYaw * 0.017453292F;
        this.stingerHead.rotateAngleX = headPitch * 0.017453292F;
        float piOn4 = (float)(Math.PI / 4f);

        this.stingerLeg1.rotateAngleZ = this.stingerLeg7.rotateAngleZ = this.stingerLeg2.rotateAngleY = this.stingerLeg7.rotateAngleY = -piOn4;
        this.stingerLeg2.rotateAngleZ = this.stingerLeg8.rotateAngleZ = this.stingerLeg1.rotateAngleY = this.stingerLeg8.rotateAngleY = piOn4;
        this.stingerLeg3.rotateAngleZ = this.stingerLeg5.rotateAngleZ = -0.58119464F;
        this.stingerLeg4.rotateAngleZ = this.stingerLeg6.rotateAngleZ = 0.58119464F;
        this.stingerLeg3.rotateAngleY = this.stingerLeg6.rotateAngleY = 0.3926991F;
        this.stingerLeg4.rotateAngleY = this.stingerLeg5.rotateAngleY = -0.3926991F;

        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f6 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        this.stingerLeg1.rotateAngleY += f3;
        this.stingerLeg2.rotateAngleY += -f3;
        this.stingerLeg3.rotateAngleY += f4;
        this.stingerLeg4.rotateAngleY += -f4;
        this.stingerLeg5.rotateAngleY += f5;
        this.stingerLeg6.rotateAngleY += -f5;
        this.stingerLeg7.rotateAngleY += f6;
        this.stingerLeg8.rotateAngleY += -f6;
        this.stingerLeg1.rotateAngleZ += f7;
        this.stingerLeg2.rotateAngleZ += -f7;
        this.stingerLeg3.rotateAngleZ += f8;
        this.stingerLeg4.rotateAngleZ += -f8;
        this.stingerLeg5.rotateAngleZ += f9;
        this.stingerLeg6.rotateAngleZ += -f9;
        this.stingerLeg7.rotateAngleZ += f10;
        this.stingerLeg8.rotateAngleZ += -f10;
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
