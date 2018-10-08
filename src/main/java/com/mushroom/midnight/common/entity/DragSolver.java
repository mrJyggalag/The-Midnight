package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.common.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import javax.vecmath.Vector3d;

public class DragSolver {
    private final EntityRifter owner;
    private final AttachmentSolver attachmentSolver;

    private EntityLivingBase dragged;

    private Vector3d attachmentPoint;
    private Vector3d prevAttachmentPoint;

    public DragSolver(EntityRifter owner) {
        this.owner = owner;
        this.attachmentSolver = new AttachmentSolver(this.owner);
    }

    public void setDragged(EntityLivingBase dragged) {
        if (this.dragged != null) {
            this.resetDragged(this.dragged);
        }
        this.dragged = dragged;
        if (dragged != null) {
            this.initDragged(dragged);
        }
    }

    private void initDragged(EntityLivingBase entity) {
        entity.noClip = true;
    }

    private void resetDragged(EntityLivingBase entity) {
        entity.noClip = false;
        entity.motionX = entity.posX - entity.prevPosX;
        entity.motionY = entity.posY - entity.prevPosY;
        entity.motionZ = entity.posZ - entity.prevPosZ;
    }

    public void solveDrag() {
        if (this.dragged == null) {
            this.attachmentPoint = null;
            return;
        }

        this.prevAttachmentPoint = this.attachmentPoint;

        float entityWidth = this.getTransformedWidth();

        float dragOffset = (this.owner.width + entityWidth) / 2.0F + 0.2F;

        float theta = (float) Math.toRadians(this.owner.renderYawOffset);
        double dragOriginX = MathHelper.sin(theta) * dragOffset;
        double dragOriginZ = -MathHelper.cos(theta) * dragOffset;

        this.attachmentSolver.getAttachmentPoint().moveTo(dragOriginX, 0.0, dragOriginZ);

        this.solveRotation(this.dragged);

        this.dragged.noClip = false;
        try {
            AttachmentSolver.Result result = this.attachmentSolver.solveAttachment(this.dragged);
            this.attachmentPoint = result.getSnappedPoint();
        } finally {
            this.dragged.noClip = true;
            this.dragged.motionX = this.dragged.motionY = this.dragged.motionZ = 0.0;
        }
    }

    private float getTransformedWidth() {
        EntityUtil.Stance stance = EntityUtil.getStance(this.dragged);
        return stance == EntityUtil.Stance.QUADRUPEDAL ? this.dragged.width : this.dragged.height;
    }

    private void solveRotation(EntityLivingBase entity) {
        entity.setRenderYawOffset(this.owner.rotationYaw);

        float deltaYaw = this.owner.rotationYaw - this.owner.prevRotationYaw;

        entity.rotationYaw += deltaYaw;
        entity.setRotationYawHead(entity.getRotationYawHead() + deltaYaw);
    }

    @Nullable
    public Vector3d getAttachmentPoint() {
        return this.attachmentPoint;
    }

    @Nullable
    public Vector3d lerpAttachmentPoint(float partialTicks) {
        if (this.attachmentPoint == null) {
            return null;
        } else if (this.prevAttachmentPoint == null) {
            this.prevAttachmentPoint = this.attachmentPoint;
        }

        Vector3d lerp = new Vector3d();
        lerp.interpolate(this.prevAttachmentPoint, this.attachmentPoint, (double) partialTicks);
        return lerp;
    }
}
