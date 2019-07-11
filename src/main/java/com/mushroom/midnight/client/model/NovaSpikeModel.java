package com.mushroom.midnight.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class NovaSpikeModel<T extends Entity> extends EntityModel<T> {
    public RendererModel spike;
    public RendererModel spike2;

    public NovaSpikeModel() {
        this.textureWidth = 32;
        this.textureHeight = 16;
        this.spike2 = new RendererModel(this, 14, 0);
        this.spike2.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.spike2.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F);
        this.spike = new RendererModel(this, 0, 0);
        this.spike.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.spike.addBox(-1.0F, -1.0F, -1.5F, 2, 2, 5, 0.0F);
        this.setRotateAngle(spike, 1.5707963267948966F, 0.0F, 0.0F);
        this.spike.addChild(this.spike2);
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.spike.render(scale);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        this.spike.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.spike.rotateAngleX = headPitch * ((float) Math.PI / 180F);
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
