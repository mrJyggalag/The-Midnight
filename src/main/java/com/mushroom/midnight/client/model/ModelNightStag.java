package com.mushroom.midnight.client.model;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNightStag extends ModelQuadruped {
    public ModelRenderer Snout;
    public ModelRenderer RightAntler;
    public ModelRenderer LeftAntler;

    public ModelNightStag() {
        super(15, 0f);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 0, 32);
        this.body.setRotationPoint(0.0F, 8.0F, 16.0F);
        this.body.addBox(-5.0F, -6.0F, -20.0F, 10, 10, 22, 0.0F);
        this.leg1 = new ModelRenderer(this, 0, 36);
        this.leg1.setRotationPoint(-3.75F, 12.0F, 0.0F);
        this.leg1.addBox(-2.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
        this.leg2 = new ModelRenderer(this, 46, 31);
        this.leg2.setRotationPoint(-4.0F, 8.0F, 14.0F);
        this.leg2.addBox(-2.0F, -2.0F, -2.5F, 4, 18, 5, 0.0F);
        this.LeftAntler = new ModelRenderer(this, 0, 0);
        this.LeftAntler.mirror = true;
        this.LeftAntler.setRotationPoint(1.5F, -15.0F, -1.0F);
        this.LeftAntler.addBox(-1.0F, -17.0F, -6.0F, 1, 18, 12, 0.0F);
        this.setRotateAngle(LeftAntler, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
        this.leg3 = new ModelRenderer(this, 0, 36);
        this.leg3.setRotationPoint(3.75F, 12.0F, 0.0F);
        this.leg3.addBox(-2.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
        this.leg4 = new ModelRenderer(this, 46, 31);
        this.leg4.setRotationPoint(4.0F, 8.0F, 14.0F);
        this.leg4.addBox(-2.0F, -2.0F, -2.5F, 4, 18, 5, 0.0F);
        this.head = new ModelRenderer(this, 32, 5);
        this.head.setRotationPoint(0.0F, 6.0F, -5.0F);
        this.head.addBox(-4.0F, -15.0F, -4.0F, 8, 18, 8, 0.0F);
        this.setRotateAngle(head, 0.17453292519943295F, 0.0F, 0.0F);
        this.RightAntler = new ModelRenderer(this, 0, 0);
        this.RightAntler.setRotationPoint(-1.5F, -15.0F, -1.0F);
        this.RightAntler.addBox(0.0F, -17.0F, -6.0F, 1, 18, 12, 0.0F);
        this.setRotateAngle(RightAntler, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
        this.Snout = new ModelRenderer(this, 16, 0);
        this.Snout.setRotationPoint(0.0F, -5.0F, -4.0F);
        this.Snout.addBox(-3.0F, -5.0F, -6.0F, 6, 5, 6, 0.0F);
        this.setRotateAngle(Snout, -0.17453292519943295F, 0.0F, 0.0F);
        this.head.addChild(this.LeftAntler);
        this.head.addChild(this.RightAntler);
        this.head.addChild(this.Snout);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        this.head.render(scale);
        this.body.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg3.render(scale);
        this.leg4.render(scale);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
        this.body.rotateAngleX = 0f;
    }
}
