package com.mushroom.midnight.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNightStag extends ModelBase {
    public ModelRenderer RightFrontLeg;
    public ModelRenderer LeftFrontLeg;
    public ModelRenderer RightBackLeg;
    public ModelRenderer LeftBackLeg;
    public ModelRenderer Body;
    public ModelRenderer Head;
    public ModelRenderer Snout;
    public ModelRenderer RightAntler;
    public ModelRenderer LeftAntler;

    public ModelNightStag() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Body = new ModelRenderer(this, 0, 32);
        this.Body.setRotationPoint(0.0F, 8.0F, 16.0F);
        this.Body.addBox(-5.0F, -6.0F, -20.0F, 10, 10, 22, 0.0F);
        this.RightFrontLeg = new ModelRenderer(this, 0, 36);
        this.RightFrontLeg.setRotationPoint(-3.75F, 12.0F, 0.0F);
        this.RightFrontLeg.addBox(-2.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
        this.RightBackLeg = new ModelRenderer(this, 46, 31);
        this.RightBackLeg.setRotationPoint(-4.0F, 8.0F, 14.0F);
        this.RightBackLeg.addBox(-2.0F, -2.0F, -2.5F, 4, 18, 5, 0.0F);
        this.LeftAntler = new ModelRenderer(this, 0, 0);
        this.LeftAntler.mirror = true;
        this.LeftAntler.setRotationPoint(1.5F, -15.0F, -1.0F);
        this.LeftAntler.addBox(-1.0F, -17.0F, -6.0F, 1, 18, 12, 0.0F);
        this.setRotateAngle(LeftAntler, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
        this.LeftFrontLeg = new ModelRenderer(this, 0, 36);
        this.LeftFrontLeg.setRotationPoint(3.75F, 12.0F, 0.0F);
        this.LeftFrontLeg.addBox(-2.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
        this.LeftBackLeg = new ModelRenderer(this, 46, 31);
        this.LeftBackLeg.setRotationPoint(4.0F, 8.0F, 14.0F);
        this.LeftBackLeg.addBox(-2.0F, -2.0F, -2.5F, 4, 18, 5, 0.0F);
        this.Head = new ModelRenderer(this, 32, 5);
        this.Head.setRotationPoint(0.0F, 6.0F, -5.0F);
        this.Head.addBox(-4.0F, -15.0F, -4.0F, 8, 18, 8, 0.0F);
        this.setRotateAngle(Head, 0.17453292519943295F, 0.0F, 0.0F);
        this.RightAntler = new ModelRenderer(this, 0, 0);
        this.RightAntler.setRotationPoint(-1.5F, -15.0F, -1.0F);
        this.RightAntler.addBox(0.0F, -17.0F, -6.0F, 1, 18, 12, 0.0F);
        this.setRotateAngle(RightAntler, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
        this.Snout = new ModelRenderer(this, 16, 0);
        this.Snout.setRotationPoint(0.0F, -5.0F, -4.0F);
        this.Snout.addBox(-3.0F, -5.0F, -6.0F, 6, 5, 6, 0.0F);
        this.setRotateAngle(Snout, -0.17453292519943295F, 0.0F, 0.0F);
        this.Head.addChild(this.LeftAntler);
        this.Head.addChild(this.RightAntler);
        this.Head.addChild(this.Snout);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.Body.render(f5);
        this.RightFrontLeg.render(f5);
        this.RightBackLeg.render(f5);
        this.LeftFrontLeg.render(f5);
        this.LeftBackLeg.render(f5);
        this.Head.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
