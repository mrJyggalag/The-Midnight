package com.mushroom.midnight.common.entities;

import net.minecraft.util.math.MathHelper;

import javax.vecmath.Point2f;
import java.util.Random;

public class RiftGeometry {
    private static final int POINT_COUNT = 12;
    private static final float DISPLACEMENT_MULTIPLIER = 0.4F;

    private final Point2f[] ring;

    public RiftGeometry(Point2f[] ring) {
        this.ring = ring;
    }

    public static RiftGeometry generate(Random random, float sizeX, float sizeY) {
        Point2f[] ring = new Point2f[POINT_COUNT];

        // Generates a circle around (0; 0), stretching it horizontally and displacing each vertex by a random amount

        float tau = (float) (Math.PI * 2.0F);
        for (int i = 0; i < POINT_COUNT; i++) {
            float angle = i * tau / POINT_COUNT;

            float displacement = (random.nextFloat() * 2.0F - 1.0F) * DISPLACEMENT_MULTIPLIER;

            float pointX = -MathHelper.sin(angle) * (sizeX + displacement);
            float pointY = MathHelper.cos(angle) * (sizeY + displacement);

            ring[i] = new Point2f(pointX, pointY);
        }

        return new RiftGeometry(ring);
    }

    public Point2f[] getRing() {
        return this.ring;
    }
}
