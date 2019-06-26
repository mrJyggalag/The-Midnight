package com.mushroom.midnight.common.entity.util;

import com.mushroom.midnight.common.util.MatrixStack;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class ChainSolver<E extends LivingEntity> {
    private final Point3f rootNode;

    private final Point3f[] restingPoints;
    private final Point3f[] transformedRestingPoints;

    private final Point3f[] solvedNodes;
    private final Point3f[] previousNodes;

    private final Vector3f[] nodeVelocity;

    private final float tightness;
    private final float dampening;

    private final TransformationMethod<E> transformationMethod;

    private final MatrixStack matrix = new MatrixStack(4);

    public ChainSolver(Point3f rootNode, Point3f[] restingPoints, float tightness, float dampening, TransformationMethod<E> transformationMethod) {
        this.rootNode = rootNode;
        this.restingPoints = this.relativize(restingPoints);
        this.tightness = tightness;
        this.dampening = dampening;

        this.transformedRestingPoints = new Point3f[restingPoints.length];
        this.solvedNodes = new Point3f[restingPoints.length];
        this.previousNodes = new Point3f[restingPoints.length];
        this.nodeVelocity = new Vector3f[restingPoints.length];

        for (int i = 0; i < this.restingPoints.length; i++) {
            this.transformedRestingPoints[i] = new Point3f(this.restingPoints[i]);
            this.solvedNodes[i] = new Point3f();
            this.previousNodes[i] = new Point3f();
            this.nodeVelocity[i] = new Vector3f();
        }

        this.transformationMethod = transformationMethod;
    }

    private Point3f[] relativize(Point3f[] restingPoints) {
        for (int i = restingPoints.length - 1; i >= 1; i--) {
            Point3f point = restingPoints[i];
            Point3f previousPoint = restingPoints[i - 1];
            point.x -= previousPoint.x;
            point.y -= previousPoint.y;
            point.z -= previousPoint.z;
        }
        return restingPoints;
    }

    public void update(E entity) {
        this.computeTransforms(entity);

        this.storePreviousState();

        for (int i = 0; i < this.restingPoints.length; i++) {
            Point3f restingPoint = this.transformedRestingPoints[i];
            Point3f previousPoint = i > 0 ? this.transformedRestingPoints[i - 1] : this.rootNode;

            Point3f currentPoint = this.solvedNodes[i];
            float localX = currentPoint.x - previousPoint.x;
            float localY = currentPoint.y - previousPoint.y;
            float localZ = currentPoint.z - previousPoint.z;

            Vector3f nodeVelocity = this.nodeVelocity[i];

            nodeVelocity.x *= this.dampening;
            nodeVelocity.y *= this.dampening;
            nodeVelocity.z *= this.dampening;

            nodeVelocity.x += this.tightness * (restingPoint.x - localX);
            nodeVelocity.y += this.tightness * (restingPoint.y - localY);
            nodeVelocity.z += this.tightness * (restingPoint.z - localZ);

            currentPoint.x += nodeVelocity.x;
            currentPoint.y += nodeVelocity.y;
            currentPoint.z += nodeVelocity.z;
        }
    }

    private void computeTransforms(E entity) {
        this.matrix.identity();
        this.transformationMethod.transform(entity, this.matrix);

        for (int i = 0; i < this.restingPoints.length; i++) {
            Point3f restingPoint = this.restingPoints[i];
            Point3f transformedPoint = this.transformedRestingPoints[i];
            transformedPoint.set(restingPoint);

            this.matrix.transform(transformedPoint);
        }
    }

    private void storePreviousState() {
        for (int i = 0; i < this.solvedNodes.length; i++) {
            this.previousNodes[i].set(this.solvedNodes[i]);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void apply(RendererModel[] parts, float partialTicks) {
        if (parts.length != this.solvedNodes.length) {
            throw new IllegalArgumentException("Expected " + this.solvedNodes.length + " parts but got " + parts.length);
        }

        // TODO: compute angles on update tick (keep solvedNodes global)
        for (int i = 0; i < this.solvedNodes.length; i++) {
            Point3f solvedNode = this.solvedNodes[i];
            Point3f connectedNode = i > 1 ? this.solvedNodes[i] : this.rootNode;

            float deltaX = solvedNode.x - connectedNode.x;
            float deltaY = solvedNode.y - connectedNode.y;
            float deltaZ = solvedNode.z - connectedNode.z;

            float distanceHorizontal = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);

            RendererModel part = parts[i];
            part.rotateAngleY = (float) (Math.atan2(deltaZ, deltaX) - Math.PI / 2.0F);
            part.rotateAngleX = (float) Math.atan2(deltaY, distanceHorizontal);
        }
    }
}
