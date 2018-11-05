package com.mushroom.midnight.common.world.noise;

import net.minecraft.util.math.MathHelper;

public interface INoiseSampler {
    void setFrequency(double frequency);

    void setAmplitude(double amplitude);

    void sample2D(double[] buffer, double originX, double originY, int sizeX, int sizeY);

    void sample3D(double[] buffer, double originX, double originY, double originZ, int sizeX, int sizeY, int sizeZ);

    default double maintainPrecision(double coordinate) {
        long origin = MathHelper.lfloor(coordinate);
        double intermediate = coordinate - (double) origin;
        return intermediate + (origin % 16777216L);
    }
}
