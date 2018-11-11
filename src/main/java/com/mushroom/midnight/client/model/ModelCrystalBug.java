package com.mushroom.midnight.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrystalBug extends ModelBase {
    private final ModelRenderer Head;
    private final ModelRenderer Body;
    private final ModelRenderer Bulb;
    private final ModelRenderer Wing11;
    private final ModelRenderer Wing21;
    private final ModelRenderer Antenna1;
    private final ModelRenderer Antenna2;
    private final ModelRenderer Wing12;
    private final ModelRenderer Wing22;

    public ModelCrystalBug() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Wing11 = new ModelRenderer(this, 22, 1);
        this.Wing11.setRotationPoint(-10.2F, 4.6F, 0.5F);
        this.Wing11.addBox(2.7F, 0.0F, 0.0F, 6, 7, 1, 0.0F);
        this.setRotateAngle(this.Wing11, 0.0F, 0.0F, 0.091106186954104F);
        this.Wing21 = new ModelRenderer(this, 39, 1);
        this.Wing21.setRotationPoint(-3.1F, 5.7F, 0.5F);
        this.Wing21.addBox(2.7F, 0.0F, 0.0F, 6, 7, 1, 0.0F);
        this.setRotateAngle(this.Wing21, 0.0F, 0.0F, -0.091106186954104F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(-3.5F, 0.0F, 0.0F);
        this.Head.addBox(0.0F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
        this.Bulb = new ModelRenderer(this, 0, 24);
        this.Bulb.setRotationPoint(-2.5F, 10.7F, 1.0F);
        this.Bulb.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, 0.0F);
        this.Antenna1 = new ModelRenderer(this, 17, 26);
        this.Antenna1.setRotationPoint(-3.1F, -3.9F, 0.5F);
        this.Antenna1.addBox(0.0F, 0.0F, 0.0F, 0, 4, 1, 0.0F);
        this.Body = new ModelRenderer(this, 0, 11);
        this.Body.setRotationPoint(-3.0F, 4.8F, 0.5F);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 4, 6, 4, 0.0F);
        this.Wing12 = new ModelRenderer(this, 21, 13);
        this.Wing12.setRotationPoint(-0.7F, 5.4F, 0.5F);
        this.Wing12.addBox(2.7F, 0.0F, 0.0F, 7, 5, 1, 0.0F);
        this.setRotateAngle(this.Wing12, 0.0F, 0.0F, 0.091106186954104F);
        this.Wing22 = new ModelRenderer(this, 39, 13);
        this.Wing22.setRotationPoint(-0.3F, 6.5F, 0.5F);
        this.Wing22.addBox(2.7F, 0.0F, 0.0F, 7, 5, 1, 0.0F);
        this.setRotateAngle(this.Wing22, 0.0F, 0.0F, -0.091106186954104F);
        this.Antenna2 = new ModelRenderer(this, 22, 26);
        this.Antenna2.setRotationPoint(0.9F, -3.9F, 0.5F);
        this.Antenna2.addBox(0.0F, 0.0F, 0.0F, 0, 4, 1, 0.0F);
        this.Wing11.addChild(this.Wing12);
        this.Wing21.addChild(this.Wing22);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, age, yaw, pitch, scale, entity);

        this.Wing11.render(scale);
        this.Wing21.render(scale);
        this.Head.render(scale);
        this.Bulb.render(scale);
        this.Antenna1.render(scale);
        this.Body.render(scale);
        this.Antenna2.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale, Entity entity) {
    }

    private void setRotateAngle(ModelRenderer box, float x, float y, float z) {
        box.rotateAngleX = x;
        box.rotateAngleY = y;
        box.rotateAngleZ = z;
    }
}
