package com.mushroom.midnight.client.model;

import com.mushroom.midnight.common.entity.creature.DeceitfulSnapperEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class DeceitfulSnapperModel extends EntityModel<DeceitfulSnapperEntity> {
    public RendererModel Body;
    public RendererModel opened_mouth;
    public RendererModel Mouth;
    public RendererModel Tail1;
    public RendererModel RightFin;
    public RendererModel LeftFin;
    public RendererModel Tail2;

    public DeceitfulSnapperModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftFin = new RendererModel(this, 0, 26);
        this.LeftFin.mirror = true;
        this.LeftFin.setRotationPoint(2.0F, 1.0F, -1.0F);
        this.LeftFin.addBox(0.0F, 0.0F, 0.0F, 0, 2, 3, 0.0F);
        this.setRotateAngle(LeftFin, 0.0F, 0.0F, -0.7853981633974483F);
        this.RightFin = new RendererModel(this, 0, 26);
        this.RightFin.setRotationPoint(-2.0F, 1.0F, -1.0F);
        this.RightFin.addBox(0.0F, 0.0F, 0.0F, 0, 2, 3, 0.0F);
        this.setRotateAngle(RightFin, 0.0F, 0.0F, 0.7853981633974483F);
        this.Body = new RendererModel(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.opened_mouth = new RendererModel(this, 46, 28);
        this.opened_mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.opened_mouth.addBox(-2.0F, -2.0F, -3F, 4, 4, 0, 0.0F);
        this.Tail1 = new RendererModel(this, 0, 10);
        this.Tail1.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Tail1.addBox(-1.0F, -1.5F, 0.0F, 2, 3, 4, 0.0F);
        this.Mouth = new RendererModel(this, 14, 0);
        this.Mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Mouth.addBox(-2.0F, 2.0F, -3.0F, 4, 2, 3, 0.0F);
        this.Tail2 = new RendererModel(this, 0, 17);
        this.Tail2.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Tail2.addBox(0.0F, -3.0F, 0.0F, 0, 6, 6, 0.0F);
        this.Body.addChild(this.opened_mouth);
        this.Body.addChild(this.LeftFin);
        this.Body.addChild(this.RightFin);
        this.Body.addChild(this.Tail1);
        this.Tail1.addChild(this.Tail2);
    }

    @Override
    public void render(DeceitfulSnapperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.opened_mouth.isHidden = entity.ticksExisted % 200 < 150;
        this.Body.render(scale);
        this.Mouth.render(scale);
    }

    public void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
}
