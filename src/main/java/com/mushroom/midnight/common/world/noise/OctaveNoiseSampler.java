package com.mushroom.midnight.common.world.noise;

import java.util.Random;
import java.util.function.Supplier;

public class OctaveNoiseSampler implements INoiseSampler {
    private final INoiseSampler[] layers;
    private final int octaveCount;

    private double amplitude = 1.0;
    private double frequency = 1.0;
    private double persistence = 0.51;
    private double lacunarity = 2.01;

    public OctaveNoiseSampler(INoiseSampler[] samplers) {
        this.layers = samplers;
        this.octaveCount = samplers.length;
    }

    public static OctaveNoiseSampler count(int count, Supplier<INoiseSampler> supplier) {
        INoiseSampler[] samplers = new INoiseSampler[count];
        for (int i = 0; i < count; i++) {
            samplers[i] = supplier.get();
        }
        return new OctaveNoiseSampler(samplers);
    }

    public static OctaveNoiseSampler perlin(Random random, int count) {
        return count(count, () -> new PerlinNoiseSampler(random));
    }

    public static OctaveNoiseSampler ridged(Random random, int count, double exponent) {
        return count(count, () -> new RidgeNoiseSampler(random, exponent));
    }

    @Override
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    @Override
    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public void setPersistence(double persistence) {
        this.persistence = persistence;
    }

    public void setLacunarity(double lacunarity) {
        this.lacunarity = lacunarity;
    }

    @Override
    public double get(double x, double y) {
        double value = 0.0;

        double currentAmplitude = this.amplitude;
        double currentFrequency = this.frequency;

        for (int octave = 0; octave < this.octaveCount; octave++) {
            INoiseSampler sampler = this.layers[octave];

            sampler.setAmplitude(currentAmplitude);
            sampler.setFrequency(currentFrequency);
            value += sampler.get(x, y);

            currentAmplitude *= this.persistence;
            currentFrequency *= this.lacunarity;
        }

        return value;
    }

    @Override
    public double get(double x, double y, double z) {
        double value = 0.0;

        double currentAmplitude = this.amplitude;
        double currentFrequency = this.frequency;

        for (int octave = 0; octave < this.octaveCount; octave++) {
            INoiseSampler sampler = this.layers[octave];

            sampler.setAmplitude(currentAmplitude);
            sampler.setFrequency(currentFrequency);
            value += sampler.get(x, y, z);

            currentAmplitude *= this.persistence;
            currentFrequency *= this.lacunarity;
        }

        return value;
    }
}
