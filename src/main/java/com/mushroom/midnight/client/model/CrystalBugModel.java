package com.mushroom.midnight.client.model;

import com.mushroom.midnight.common.entity.creature.CrystalBugEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class CrystalBugModel extends EntityModel<CrystalBugEntity> {
    private final RendererModel body;
    private final RendererModel wingL;
    private final RendererModel head;
    private final RendererModel bulb;
    private final RendererModel wingR;
    private final RendererModel wingL2;
    private final RendererModel antennaL;
    private final RendererModel antennaR;
    private final RendererModel wingR2;

    public CrystalBugModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.wingL = new RendererModel(this, 0, 11);
        this.wingL.setRotationPoint(2.0F, 1.1F, 3.1F);
        this.wingL.addBox(0.0F, 0.0F, 0.0F, 6, 7, 1, 0.0F);
        this.setRotateAngle(wingL, 0.0F, 0.0F, -0.08726646259971647F);
        this.antennaR = new RendererModel(this, 0, -1);
        this.antennaR.setRotationPoint(0.5F, -4.0F, 3.0F);
        this.antennaR.addBox(0.0F, 0.0F, 0.0F, 0, 4, 1, 0.0F);
        this.antennaL = new RendererModel(this, 0, -1);
        this.antennaL.setRotationPoint(4.5F, -4.0F, 3.0F);
        this.antennaL.addBox(0.0F, 0.0F, 0.0F, 0, 4, 1, 0.0F);
        this.head = new RendererModel(this, 17, 0);
        this.head.setRotationPoint(-0.5F, -5.0F, -0.5F);
        this.head.addBox(0.0F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
        this.wingR = new RendererModel(this, 15, 11);
        this.wingR.setRotationPoint(2.0F, 1.1F, 3.1F);
        this.wingR.addBox(-6.0F, 0.0F, 0.0F, 6, 7, 1, 0.0F);
        this.setRotateAngle(wingR, 0.0F, 0.0F, 0.08726646259971647F);
        this.wingL2 = new RendererModel(this, 0, 20);
        this.wingL2.setRotationPoint(0.0F, 7.0F, 0.1F);
        this.wingL2.addBox(0.0F, 0.0F, 0.0F, 7, 5, 1, 0.0F);
        this.setRotateAngle(wingL2, 0.0F, 0.0F, -0.08726646259971647F);
        this.bulb = new RendererModel(this, 38, 0);
        this.bulb.setRotationPoint(0.5F, 6.0F, 0.5F);
        this.bulb.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, 0.0F);
        this.wingR2 = new RendererModel(this, 0, 26);
        this.wingR2.setRotationPoint(-1.0F, 7.0F, 0.1F);
        this.wingR2.addBox(-6.0F, 0.0F, 0.0F, 7, 5, 1, 0.0F);
        this.setRotateAngle(wingR2, 0.0F, 0.0F, 0.08726646259971647F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(-2.0F, -3.0F, -2.0F);
        this.body.addBox(0.0F, 0.0F, 0.0F, 4, 6, 4, 0.0F);

        this.head.addChild(this.antennaR);
        this.head.addChild(this.antennaL);
        this.body.addChild(this.head);
        this.wingL.addChild(this.wingL2);
        this.body.addChild(this.wingL);
        this.wingR.addChild(this.wingR2);
        this.body.addChild(this.wingR);
        this.body.addChild(this.bulb);
    }

    @Override
    public void render(CrystalBugEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        body.render(scale);
    }

    @Override
    public void setRotationAngles(CrystalBugEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        if (entity.isStanding()) {
            body.rotateAngleX = 0f;
            wingR.rotateAngleY = wingL.rotateAngleY = 0f;
        } else {
            body.rotateAngleX = (float) Math.PI / 4f + MathHelper.cos(ageInTicks * 0.1f) * 0.15f;
            wingR.rotateAngleY = Math.abs(MathHelper.cos(ageInTicks)) * (float) Math.PI * 0.35f;
            wingL.rotateAngleY = -wingR.rotateAngleY;
        }
    }

    private void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
}
