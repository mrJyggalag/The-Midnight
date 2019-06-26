package com.mushroom.midnight.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class ModelPartAnimator {
    public void bob(RendererModel box, float speed, float degree, boolean invert, float offset, float weight, float limbSwing, float limbSwingAmount) {
        box.rotationPointY = this.computeAnimation(speed, degree, invert, offset, weight, limbSwing, limbSwingAmount);
    }

    public void walk(RendererModel box, float speed, float degree, boolean invert, float offset, float weight, float limbSwing, float limbSwingAmount) {
        box.rotateAngleX = this.computeAnimation(speed, degree, invert, offset, weight, limbSwing, limbSwingAmount);
    }

    public void swing(RendererModel box, float speed, float degree, boolean invert, float offset, float weight, float limbSwing, float limbSwingAmount) {
        box.rotateAngleY = this.computeAnimation(speed, degree, invert, offset, weight, limbSwing, limbSwingAmount);
    }

    public void flap(RendererModel box, float speed, float degree, boolean invert, float offset, float weight, float limbSwing, float limbSwingAmount) {
        box.rotateAngleZ = this.computeAnimation(speed, degree, invert, offset, weight, limbSwing, limbSwingAmount);
    }

    public void chainSwing(RendererModel[] boxes, float speed, float degree, boolean invert, double rootOffset, float swing, float swingAmount) {
        float offset = this.computeChainOffset(boxes, rootOffset);
        for (int index = 0; index < boxes.length; index++) {
            boxes[index].rotateAngleY = this.computeChainRotation(speed, degree, invert, offset, index, swing, swingAmount);
        }
    }

    public void chainWave(RendererModel[] boxes, float speed, float degree, boolean invert, double rootOffset, float swing, float swingAmount) {
        float offset = this.computeChainOffset(boxes, rootOffset);
        for (int index = 0; index < boxes.length; index++) {
            boxes[index].rotateAngleX = this.computeChainRotation(speed, degree, invert, offset, index, swing, swingAmount);
        }
    }

    public void chainFlap(RendererModel[] boxes, float speed, float degree, boolean invert, double rootOffset, float swing, float swingAmount) {
        float offset = this.computeChainOffset(boxes, rootOffset);
        for (int index = 0; index < boxes.length; index++) {
            boxes[index].rotateAngleZ = this.computeChainRotation(speed, degree, invert, offset, index, swing, swingAmount);
        }
    }

    public float computeChainRotation(float speed, float degree, boolean invert, float offset, int boxIndex, float swing, float swingAmount) {
        float theta = swing * speed + offset * boxIndex;
        float rotation = MathHelper.cos(theta) * swingAmount * degree;
        return invert ? -rotation : rotation;
    }

    public float computeChainOffset(RendererModel[] boxes, double rootOffset) {
        return (float) (rootOffset * Math.PI / (2 * boxes.length));
    }

    public float computeAnimation(float speed, float degree, boolean invert, float offset, float weight, float limbSwing, float limbSwingAmount) {
        float theta = limbSwing * speed + offset;
        float scaledWeight = weight * limbSwingAmount;
        float rotation = (MathHelper.cos(theta) * degree * limbSwingAmount) + scaledWeight;
        return invert ? -rotation : rotation;
    }
}
