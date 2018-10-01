package com.mushroom.midnight.common.world.noise;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.NoiseGenerator;

import java.util.Arrays;
import java.util.Random;

public class OctavedNoiseGenerator extends NoiseGenerator {
    private final FixedPerlinNoiseGenerator[] generators;
    private final int octaveCount;

    private final double amplitude;
    private final double frequency;
    private final double persistence;
    private final double lacunarity;

    public OctavedNoiseGenerator(Random seed, int octaveCount, double amplitude, double frequency, double persistence, double lacunarity, int sizeX, int sizeY, int sizeZ) {
        this.generators = new FixedPerlinNoiseGenerator[octaveCount];
        for (int i = 0; i < octaveCount; ++i) {
            this.generators[i] = new FixedPerlinNoiseGenerator(seed, sizeX, sizeY, sizeZ);
        }

        this.octaveCount = octaveCount;

        this.amplitude = amplitude;
        this.frequency = frequency;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
    }

    public OctavedNoiseGenerator(Random seed, int octaveCount, double amplitude, double frequency, double persistence, double lacunarity, int sizeX, int sizeZ) {
        this(seed, octaveCount, amplitude, frequency, persistence, lacunarity, sizeX, 1, sizeZ);
    }

    public void generateOctavedNoise3D(double[] noiseBuffer, double originX, double originY, double originZ) {
        Arrays.fill(noiseBuffer, 0.0);

        double currentAmplitude = this.amplitude;
        double currentFrequency = this.frequency;

        for (int octave = 0; octave < this.octaveCount; octave++) {
            FixedPerlinNoiseGenerator generator = this.generators[octave];

            double sampleX = this.maintainPrecision(originX * currentFrequency);
            double sampleY = this.maintainPrecision(originY * currentFrequency);
            double sampleZ = this.maintainPrecision(originZ * currentFrequency);

            generator.populate3D(noiseBuffer, sampleX, sampleY, sampleZ, currentFrequency, currentFrequency, currentFrequency, 1.0 / currentAmplitude);

            currentAmplitude *= this.persistence;
            currentFrequency *= this.lacunarity;
        }
    }

    public void generateOctavedNoise2D(double[] noiseBuffer, double originX, double originZ) {
        Arrays.fill(noiseBuffer, 0.0);

        double currentAmplitude = this.amplitude;
        double currentFrequency = this.frequency;

        for (int octave = 0; octave < this.octaveCount; octave++) {
            FixedPerlinNoiseGenerator generator = this.generators[octave];

            double sampleX = this.maintainPrecision(originX * currentFrequency);
            double sampleZ = this.maintainPrecision(originZ * currentFrequency);

            generator.populate2D(noiseBuffer, sampleX, sampleZ, currentFrequency, currentFrequency, 1.0 / currentAmplitude);

            currentAmplitude *= this.persistence;
            currentFrequency *= this.lacunarity;
        }
    }

    private double maintainPrecision(double coordinate) {
        long origin = MathHelper.lfloor(coordinate);
        double intermediate = coordinate - (double) origin;
        return intermediate + (origin % 16777216L);
    }
}
