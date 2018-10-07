package com.mushroom.midnight.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;

import javax.annotation.Nullable;
import javax.vecmath.Vector3d;

public class AttachmentSolver {
    private final AttachmentPoint attachmentPoint = new AttachmentPoint();
    private final Vector3d snappedAttachmentPoint = new Vector3d();

    private final EntityLivingBase origin;

    private EntityLivingBase attachedEntity;

    public AttachmentSolver(EntityLivingBase origin) {
        this.origin = origin;
    }

    public void setAttachedEntity(EntityLivingBase attachedEntity) {
        if (this.attachedEntity != null) {
            attachedEntity.motionX = attachedEntity.posX - attachedEntity.prevPosX;
            attachedEntity.motionY = attachedEntity.posY - attachedEntity.prevPosY;
            attachedEntity.motionZ = attachedEntity.posZ - attachedEntity.prevPosZ;
        }
        this.attachedEntity = attachedEntity;
    }

    public void detachEntity() {
        this.setAttachedEntity(null);
    }

    public Result updateAttachedEntity() {
        EntityLivingBase attachedEntity = this.attachedEntity;
        if (attachedEntity != null) {
            double globalX = this.attachmentPoint.getX() + this.origin.posX;
            double globalY = this.attachmentPoint.getY() + this.origin.posY;
            double globalZ = this.attachmentPoint.getZ() + this.origin.posZ;

            double deltaX = globalX - attachedEntity.posX;
            double deltaY = globalY - attachedEntity.posY;
            double deltaZ = globalZ - attachedEntity.posZ;
            attachedEntity.move(MoverType.SELF, deltaX, deltaY, deltaZ);

            attachedEntity.onGround = true;
            attachedEntity.fallDistance = 0.0F;

            this.snappedAttachmentPoint.x = attachedEntity.posX;
            this.snappedAttachmentPoint.y = attachedEntity.posY;
            this.snappedAttachmentPoint.z = attachedEntity.posZ;

            return new Result(this.snappedAttachmentPoint);
        }

        return new Result(null);
    }

    public AttachmentPoint getAttachmentPoint() {
        return this.attachmentPoint;
    }

    public static class Result {
        private final Vector3d snappedPoint;

        public Result(Vector3d snappedPoint) {
            this.snappedPoint = snappedPoint;
        }

        @Nullable
        public Vector3d getSnappedPoint() {
            return this.snappedPoint;
        }
    }
}
