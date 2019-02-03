package com.mushroom.midnight.client.model;

import com.mushroom.midnight.common.entity.creature.EntityNightStag;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelNightStag extends ModelQuadruped {
    public ModelRenderer snout;
    public ModelRenderer rightAntler;
    public ModelRenderer leftAntler;

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
        this.leftAntler = new ModelRenderer(this, 0, 0);
        this.leftAntler.mirror = true;
        this.leftAntler.setRotationPoint(1.5F, -15.0F, -1.0F);
        this.leftAntler.addBox(-1.0F, -17.0F, -6.0F, 1, 18, 12, 0.0F);
        this.setRotateAngle(this.leftAntler, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
        this.leg3 = new ModelRenderer(this, 0, 36);
        this.leg3.setRotationPoint(3.75F, 12.0F, 0.0F);
        this.leg3.addBox(-2.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
        this.leg4 = new ModelRenderer(this, 46, 31);
        this.leg4.setRotationPoint(4.0F, 8.0F, 14.0F);
        this.leg4.addBox(-2.0F, -2.0F, -2.5F, 4, 18, 5, 0.0F);
        this.head = new ModelRenderer(this, 32, 5);
        this.head.setRotationPoint(0.0F, 6.0F, -5.0F);
        this.head.addBox(-4.0F, -15.0F, -4.0F, 8, 18, 8, 0.0F);
        this.setRotateAngle(this.head, 0.17453292519943295F, 0.0F, 0.0F);
        this.rightAntler = new ModelRenderer(this, 0, 0);
        this.rightAntler.setRotationPoint(-1.5F, -15.0F, -1.0F);
        this.rightAntler.addBox(0.0F, -17.0F, -6.0F, 1, 18, 12, 0.0F);
        this.setRotateAngle(this.rightAntler, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
        this.snout = new ModelRenderer(this, 16, 0);
        this.snout.setRotationPoint(0.0F, -5.0F, -4.0F);
        this.snout.addBox(-3.0F, -5.0F, -6.0F, 6, 5, 6, 0.0F);
        this.setRotateAngle(this.snout, -0.17453292519943295F, 0.0F, 0.0F);
        this.head.addChild(this.leftAntler);
        this.head.addChild(this.rightAntler);
        this.head.addChild(this.snout);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, -0.4F);

        this.head.render(scale);
        this.body.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg3.render(scale);
        this.leg4.render(scale);

        GlStateManager.popMatrix();
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

        EntityNightStag nightstag = (EntityNightStag) entity;
        if (nightstag.isAttacking()) {
            float attackProgress = nightstag.getAttackProgress();
            this.body.rotateAngleX = (attackProgress < 0.5f ? attackProgress : 0f) * 0.2f;
            this.head.rotateAngleX = (attackProgress < 0.9f ? attackProgress : (1f - attackProgress)) * 3f;
        } else {
            this.body.rotateAngleX = 0f;
        }
    }
}
