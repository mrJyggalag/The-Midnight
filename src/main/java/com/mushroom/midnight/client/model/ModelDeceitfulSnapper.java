package com.mushroom.midnight.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDeceitfulSnapper extends ModelBase {
    public ModelRenderer Body;
    public ModelRenderer Mouth;
    public ModelRenderer Tail1;
    public ModelRenderer RightFin;
    public ModelRenderer LeftFin;
    public ModelRenderer Tail2;

    public ModelDeceitfulSnapper() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftFin = new ModelRenderer(this, 0, 26);
        this.LeftFin.mirror = true;
        this.LeftFin.setRotationPoint(2.0F, 1.0F, -1.0F);
        this.LeftFin.addBox(0.0F, 0.0F, 0.0F, 0, 2, 3, 0.0F);
        this.setRotateAngle(LeftFin, 0.0F, 0.0F, -0.7853981633974483F);
        this.RightFin = new ModelRenderer(this, 0, 26);
        this.RightFin.setRotationPoint(-2.0F, 1.0F, -1.0F);
        this.RightFin.addBox(0.0F, 0.0F, 0.0F, 0, 2, 3, 0.0F);
        this.setRotateAngle(RightFin, 0.0F, 0.0F, 0.7853981633974483F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.Tail1 = new ModelRenderer(this, 0, 10);
        this.Tail1.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Tail1.addBox(-1.0F, -1.5F, 0.0F, 2, 3, 4, 0.0F);
        this.Mouth = new ModelRenderer(this, 14, 0);
        this.Mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Mouth.addBox(-2.0F, 2.0F, -3.0F, 4, 2, 3, 0.0F);
        this.Tail2 = new ModelRenderer(this, 0, 17);
        this.Tail2.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Tail2.addBox(0.0F, -3.0F, 0.0F, 0, 6, 6, 0.0F);
        this.Body.addChild(this.LeftFin);
        this.Body.addChild(this.RightFin);
        this.Body.addChild(this.Tail1);
        this.Tail1.addChild(this.Tail2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body.render(f5);
        this.Mouth.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
