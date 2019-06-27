package com.mushroom.midnight.common.world.noise;

import net.minecraft.util.math.MathHelper;

public interface INoiseSampler {
    void setFrequency(double frequency);

    void setAmplitude(double amplitude);

    double get(double x, double y);

    double get(double x, double y, double z);

    default double maintainPrecision(double coordinate) {
        long origin = MathHelper.lfloor(coordinate);
        double intermediate = coordinate - (double) origin;
        return intermediate + (origin % 16777216L);
    }
}
