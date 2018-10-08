package com.mushroom.midnight.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;

import javax.vecmath.Vector3d;

public class AttachmentSolver {
    private final AttachmentPoint attachmentPoint = new AttachmentPoint();
    private final Vector3d snappedAttachmentPoint = new Vector3d();

    private final EntityLivingBase origin;

    public AttachmentSolver(EntityLivingBase origin) {
        this.origin = origin;
    }

    public Result solveAttachment(EntityLivingBase entity) {
        double globalX = this.attachmentPoint.getX() + this.origin.posX;
        double globalY = this.attachmentPoint.getY() + this.origin.posY;
        double globalZ = this.attachmentPoint.getZ() + this.origin.posZ;

        double deltaX = globalX - entity.posX;
        double deltaY = globalY - entity.posY;
        double deltaZ = globalZ - entity.posZ;
        entity.move(MoverType.SELF, deltaX, deltaY, deltaZ);

        entity.onGround = true;
        entity.fallDistance = 0.0F;

        this.snappedAttachmentPoint.x = entity.posX;
        this.snappedAttachmentPoint.y = entity.posY;
        this.snappedAttachmentPoint.z = entity.posZ;

        return new Result(this.snappedAttachmentPoint);
    }

    public AttachmentPoint getAttachmentPoint() {
        return this.attachmentPoint;
    }

    public static class Result {
        private final Vector3d snappedPoint;

        public Result(Vector3d snappedPoint) {
            this.snappedPoint = snappedPoint;
        }

        public Vector3d getSnappedPoint() {
            return this.snappedPoint;
        }
    }
}
