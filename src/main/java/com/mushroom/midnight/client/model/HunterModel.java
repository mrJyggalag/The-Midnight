package com.mushroom.midnight.client.model;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.entity.creature.HunterEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class HunterModel extends EntityModel<HunterEntity> {
    private final RendererModel Body;
    private final RendererModel Head;
    private final RendererModel TailBase;
    private final RendererModel Wing11;
    private final RendererModel Wing21;
    private final RendererModel Nose;
    private final RendererModel Segment1;
    private final RendererModel TailJoint2;
    private final RendererModel Segment3;
    private final RendererModel StingerBody;
    private final RendererModel StingerHead;
    private final RendererModel Wing12;
    private final RendererModel Wing22;

    private final RendererModel[] leftWing;
    private final RendererModel[] rightWing;
    private final RendererModel[] tail;

    private final ModelPartAnimator animator = new ModelPartAnimator();

    public HunterModel() {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.Body = new RendererModel(this, 74, 87);
        this.Body.setRotationPoint(0.0F, 19.5F, 0.0F);
        this.Body.addBox(-3.5F, -3.5F, -6.0F, 7, 7, 19);
        this.Head = new RendererModel(this, 95, 1);
        this.Head.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.Head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8);
        this.Head.mirror = true;
        this.Body.addChild(this.Head);
        this.Nose = new RendererModel(this, 0, 16);
        this.Nose.setRotationPoint(0.0F, 2.0F, -10.7F);
        this.Nose.addBox(-3.0F, -2.0F, 0.0F, 6, 4, 3);
        this.Head.addChild(this.Nose);
        this.TailBase = new RendererModel(this, 69, 1);
        this.TailBase.setRotationPoint(0.0F, 0.0F, 15.4F);
        this.TailBase.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
        this.Body.addChild(this.TailBase);
        this.Segment1 = new RendererModel(this, 56, 14);
        this.Segment1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Segment1.addBox(-2.5F, -2.5F, 1.4F, 5, 5, 12);
        this.TailBase.addChild(this.Segment1);
        this.TailJoint2 = new RendererModel(this, 13, 1);
        this.TailJoint2.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.TailJoint2.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
        this.Segment1.addChild(this.TailJoint2);
        this.Segment3 = new RendererModel(this, 1, 31);
        this.Segment3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Segment3.addBox(-2.5F, -2.5F, 1.0F, 5, 5, 12);
        this.TailJoint2.addChild(this.Segment3);
        this.StingerBody = new RendererModel(this, 1, 50);
        this.StingerBody.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.StingerBody.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
        this.Segment3.addChild(this.StingerBody);
        this.StingerHead = new RendererModel(this, 0, 0);
        this.StingerHead.setRotationPoint(0.6F, 0.0F, 2.8F);
        this.StingerHead.addBox(-1.5F, -1.6F, -5.0F, 3, 3, 5);
        this.setRotateAngle(this.StingerHead, 3.141592653589793F, 0.0F, 0.0F);
        this.StingerBody.addChild(this.StingerHead);
        this.Wing11 = new RendererModel(this, 36, 36);
        this.Wing11.setRotationPoint(2.8F, -1.4F, 2.0F);
        this.Wing11.addBox(0.0F, -1.0F, -6.0F, 12, 2, 12);
        this.setRotateAngle(this.Wing11, 0.0F, -0.0911061832922575F, -0.12217304763960307F);
        this.Body.addChild(this.Wing11);
        this.Wing12 = new RendererModel(this, 1, 104);
        this.Wing12.setRotationPoint(11.3F, 0.0F, -0.5F);
        this.Wing12.addBox(0.0F, -1.0F, -5.5F, 15, 2, 12);
        this.setRotateAngle(this.Wing12, 0.0F, 0.0911061832922575F, 0.13718288153702027F);
        this.Wing11.addChild(this.Wing12);
        this.Wing21 = new RendererModel(this, 38, 59);
        this.Wing21.setRotationPoint(-2.8F, -1.4F, 2.0F);
        this.Wing21.addBox(-12.0F, -1.0F, -6.0F, 12, 2, 12);
        this.setRotateAngle(this.Wing21, 0.0F, 0.0911061832922575F, 0.12217304763960307F);
        this.Body.addChild(this.Wing21);
        this.Wing22 = new RendererModel(this, 36, 85);
        this.Wing22.setRotationPoint(-11.3F, 0.0F, -0.7F);
        this.Wing22.addBox(-15.0F, -1.0F, -5.5F, 15, 2, 12);
        this.setRotateAngle(this.Wing22, 0.0F, -0.0911061832922575F, -0.13718288153702027F);
        this.Wing21.addChild(this.Wing22);

        this.leftWing = new RendererModel[] { this.Wing11, this.Wing12 };
        this.rightWing = new RendererModel[] { this.Wing21, this.Wing22 };
        this.tail = new RendererModel[] { this.TailBase, this.TailJoint2, this.StingerBody };
    }

    @Override
    public void render(HunterEntity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, age, yaw, pitch, scale);

        this.Body.render(scale);
    }

    @Override
    public void setRotationAngles(HunterEntity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        float globalSpeed = 1.0F;
        float globalDegree = 1.0F;

        this.animator.bob(this.Body, globalSpeed * 0.5F, globalDegree * 1.5F, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
        this.Body.rotationPointY += 13.0F;

        this.animator.chainFlap(this.leftWing, globalSpeed * 0.5F, globalDegree * 0.6F, true, -2, limbSwing, limbSwingAmount);
        this.animator.chainFlap(this.rightWing, globalSpeed * 0.5F, globalDegree * 0.6F, false, -2, limbSwing, limbSwingAmount);

        this.animator.walk(this.Wing11, globalSpeed * 0.5F, globalDegree * 0.25F, true, 0.0F, -0.25F, limbSwing, limbSwingAmount);
        this.animator.walk(this.Wing21, globalSpeed * 0.5F, globalDegree * 0.25F, true, 0.0F, -0.25F, limbSwing, limbSwingAmount);

        entity.getCapability(Midnight.ANIMATION_CAP).ifPresent(animation -> {
            if (animation.getAnimationType() != AnimationCapability.Type.ATTACK) {
                return;
            }

            float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
            float attackProgress = animation.getProgress(partialTicks);
            float attackAnimation = (float) (Math.sin((2.0F * attackProgress - 0.5F) * Math.PI) + 1.0F) / 2.0F;

            float partAttackAngle = -180.0F / this.tail.length;

            for (RendererModel tailPart : this.tail) {
                tailPart.rotateAngleX = (float) Math.toRadians(attackAnimation * partAttackAngle);
            }

//            ChainSolver<HunterEntity> chainSolver = hunter.getChainSolver();
//            chainSolver.apply(this.tail, partialTicks);
        });
    }

    private void setRotateAngle(RendererModel box, float x, float y, float z) {
        box.rotateAngleX = x;
        box.rotateAngleY = y;
        box.rotateAngleZ = z;
    }
}
