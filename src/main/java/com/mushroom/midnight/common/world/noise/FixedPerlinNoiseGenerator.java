package com.mushroom.midnight.common.world.noise;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.NoiseGenerator;

import java.util.Random;

public class FixedPerlinNoiseGenerator extends NoiseGenerator {
    private final int[] permutations;

    protected final int sizeX;
    protected final int sizeY;
    protected final int sizeZ;

    protected final double originX;
    protected final double originY;
    protected final double originZ;

    private static final double[] GRAD_X = new double[] { 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0 };
    private static final double[] GRAD_Y = new double[] { 1.0, 1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0 };
    private static final double[] GRAD_Z = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0 };

    public FixedPerlinNoiseGenerator(Random random, int sizeX, int sizeY, int sizeZ) {
        this.permutations = new int[512];

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;

        this.originX = random.nextDouble() * 256.0;
        this.originY = random.nextDouble() * 256.0;
        this.originZ = random.nextDouble() * 256.0;

        for (int i = 0; i < 256; i++) {
            this.permutations[i] = i;
        }

        for (int i = 0; i < 256; ++i) {
            int shuffleIndex = random.nextInt(256 - i) + i;
            int value = this.permutations[i];
            this.permutations[i] = this.permutations[shuffleIndex];
            this.permutations[shuffleIndex] = value;
            this.permutations[i + 256] = this.permutations[i];
        }
    }

    public FixedPerlinNoiseGenerator(Random random, int sizeX, int sizeZ) {
        this(random, sizeX, 1, sizeZ);
    }

    private double lerp(double delta, double a, double b) {
        return a + delta * (b - a);
    }

    private double grad2D(int perm, double x, double z) {
        int gradIndex = perm & 15;
        return GRAD_X[gradIndex] * x + GRAD_Z[gradIndex] * z;
    }

    private double grad3D(int perm, double x, double y, double z) {
        int gradIndex = perm & 15;
        return GRAD_X[gradIndex] * x + GRAD_Y[gradIndex] * y + GRAD_Z[gradIndex] * z;
    }

    public void populate2D(double[] noiseArray, double offsetX, double offsetZ, double frequencyX, double frequencyZ, double noiseScale) {
        int index = 0;
        double scale = 1.0 / noiseScale;

        for (int localZ = 0; localZ < this.sizeZ; ++localZ) {
            double scaledZ = offsetZ + localZ * frequencyZ + this.originZ;
            int originZ = MathHelper.floor(scaledZ);
            int permZ = originZ & 255;
            double interZ = scaledZ - originZ;
            double easedZ = this.ease(interZ);

            for (int localX = 0; localX < this.sizeX; ++localX) {
                double scaledX = offsetX + localX * frequencyX + this.originX;
                int originX = MathHelper.floor(scaledX);
                int permX = originX & 255;
                double interX = scaledX - originX;
                double easedX = this.ease(interX);

                int permA = this.permutations[permX];
                int permB = this.permutations[permA] + permZ;
                int permC = this.permutations[permX + 1];
                int permD = this.permutations[permC] + permZ;

                double noiseX1 = this.lerp(easedX, this.grad2D(this.permutations[permB], interX, interZ), this.grad3D(this.permutations[permD], interX - 1.0, 0.0, interZ));
                double noiseX2 = this.lerp(easedX, this.grad3D(this.permutations[permB + 1], interX, 0.0, interZ - 1.0), this.grad3D(this.permutations[permD + 1], interX - 1.0, 0.0, interZ - 1.0));
                double noise = this.lerp(easedZ, noiseX1, noiseX2);
                noiseArray[index++] += noise * scale;
            }
        }
    }

    public void populate3D(double[] noiseArray, double offsetX, double offsetY, double offsetZ, double frequencyX, double frequencyY, double frequencyZ, double noiseScale) {
        int index = 0;
        double scale = 1.0 / noiseScale;

        for (int localZ = 0; localZ < this.sizeZ; ++localZ) {
            double scaledZ = offsetZ + localZ * frequencyZ + this.originZ;
            int originZ = MathHelper.floor(scaledZ);
            int permZ = originZ & 255;
            double interZ = scaledZ - originZ;
            double easedZ = this.ease(interZ);

            for (int localX = 0; localX < this.sizeX; ++localX) {
                double scaledX = offsetX + localX * frequencyX + this.originX;
                int originX = MathHelper.floor(scaledX);
                int permX = originX & 255;
                double interX = scaledX - originX;
                double easedX = this.ease(interX);

                for (int localY = 0; localY < this.sizeY; ++localY) {
                    double scaledY = offsetY + localY * frequencyY + this.originY;
                    int originY = MathHelper.floor(scaledY);
                    int permY = originY & 255;
                    double interY = scaledY - originY;
                    double easedY = this.ease(interY);

                    int permA = this.permutations[permX] + permY;
                    int permB = this.permutations[permA] + permZ;
                    int permC = this.permutations[permA + 1] + permZ;
                    int permD = this.permutations[permX + 1] + permY;
                    int permE = this.permutations[permD] + permZ;
                    int permF = this.permutations[permD + 1] + permZ;

                    double noiseX1Y1 = this.lerp(easedX, this.grad3D(this.permutations[permB], interX, interY, interZ), this.grad3D(this.permutations[permE], interX - 1.0, interY, interZ));
                    double noiseX2Y1 = this.lerp(easedX, this.grad3D(this.permutations[permC], interX, interY - 1.0, interZ), this.grad3D(this.permutations[permF], interX - 1.0, interY - 1.0, interZ));
                    double noiseX1Y2 = this.lerp(easedX, this.grad3D(this.permutations[permB + 1], interX, interY, interZ - 1.0), this.grad3D(this.permutations[permE + 1], interX - 1.0, interY, interZ - 1.0));
                    double noiseX2Y2 = this.lerp(easedX, this.grad3D(this.permutations[permC + 1], interX, interY - 1.0, interZ - 1.0), this.grad3D(this.permutations[permF + 1], interX - 1.0, interY - 1.0, interZ - 1.0));

                    double noiseY1 = this.lerp(easedY, noiseX1Y1, noiseX2Y1);
                    double noiseY2 = this.lerp(easedY, noiseX1Y2, noiseX2Y2);
                    double noise = this.lerp(easedZ, noiseY1, noiseY2);
                    noiseArray[index++] += noise * scale;
                }
            }
        }
    }

    private double ease(double value) {
        return value * value * value * (value * (value * 6.0 - 15.0) + 10.0);
    }
}
