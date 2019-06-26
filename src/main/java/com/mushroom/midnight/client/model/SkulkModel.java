package com.mushroom.midnight.client.model;

import com.mushroom.midnight.common.entity.creature.SkulkEntity;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class SkulkModel extends QuadrupedModel<SkulkEntity> {
    private RendererModel Tail;
    private RendererModel RightEar;
    private RendererModel LeftEar;
    private RendererModel Snout;

    public SkulkModel() {
        super(5, 0f);
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.head = new RendererModel(this, 0, 12);
        this.head.setRotationPoint(0.0F, 20.0F, -4.0F);
        this.head.addBox(-3.5F, -2.0F, -3.0F, 7, 4, 4, 0.0F);
        this.body = new RendererModel(this, 0, 2);
        this.body.setRotationPoint(0.0F, 20.0F, 4.0F);
        this.body.addBox(-2.5F, -1.5F, -7.0F, 5, 3, 7, 0.0F);
        this.leg1 = new RendererModel(this, 0, 26);
        this.leg1.setRotationPoint(-1.4F, 22.0F, -2.1F);
        this.leg1.addBox(-1.0F, -2.0F, -1.0F, 2, 4, 2, 0.0F);
        this.leg2 = new RendererModel(this, 0, 26);
        this.leg2.setRotationPoint(-1.6F, 22.0F, 2.5F);
        this.leg2.addBox(-1.0F, -2.0F, -1.0F, 2, 4, 2, 0.0F);
        this.leg3 = new RendererModel(this, 0, 26);
        this.leg3.setRotationPoint(1.4F, 22.0F, -2.1F);
        this.leg3.addBox(-1.0F, -2.0F, -1.0F, 2, 4, 2, 0.0F);
        this.leg4 = new RendererModel(this, 0, 26);
        this.leg4.setRotationPoint(1.6F, 22.0F, 2.5F);
        this.leg4.addBox(-1.0F, -2.0F, -1.0F, 2, 4, 2, 0.0F);
        this.LeftEar = new RendererModel(this, 1, 0);
        this.LeftEar.setRotationPoint(2.5F, -1.0F, -0.5F);
        this.LeftEar.addBox(-1.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
        this.setRotateAngle(LeftEar, 0.17453292519943295F, -0.5235987755982988F, 0.5235987755982988F);
        this.RightEar = new RendererModel(this, 1, 0);
        this.RightEar.setRotationPoint(-2.5F, -1.0F, -0.5F);
        this.RightEar.addBox(-1.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
        this.setRotateAngle(RightEar, 0.17453292519943295F, 0.5235987755982988F, -0.5235987755982988F);
        this.Snout = new RendererModel(this, 0, 22);
        this.Snout.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Snout.addBox(-1.5F, 0.0F, -4.0F, 3, 2, 1, 0.0F);
        this.Tail = new RendererModel(this, 8, 22);
        this.Tail.setRotationPoint(0.0F, 19.5F, 3.5F);
        this.Tail.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
        this.setRotateAngle(Tail, -0.4363323129985824F, 0.0F, 0.0F);
        this.head.addChild(this.LeftEar);
        this.head.addChild(this.RightEar);
        this.head.addChild(this.Snout);
    }

    @Override
    public void render(SkulkEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.Tail.render(scale);
    }

    @Override
    public void setRotationAngles(SkulkEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        this.body.rotateAngleX = 0f;
        this.Tail.rotateAngleY = MathHelper.sin((float) (ageInTicks * Math.PI * 0.25f)) * 0.1f;
        this.LeftEar.rotateAngleX = this.RightEar.rotateAngleX = MathHelper.sin((float) (ageInTicks * Math.PI * 0.2f)) * 0.1f;
    }

    private void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
}
