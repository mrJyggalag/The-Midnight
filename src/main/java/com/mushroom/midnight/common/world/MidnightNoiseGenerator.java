package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.biome.surface.SurfaceBiome;
import com.mushroom.midnight.common.util.Curve;
import com.mushroom.midnight.common.util.RegionInterpolator;
import com.mushroom.midnight.common.world.noise.OctaveNoiseSampler;
import com.mushroom.midnight.common.world.noise.PerlinNoiseSampler;
import com.mushroom.midnight.common.world.util.BiomeWeightTable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

import java.util.Random;

import static com.mushroom.midnight.common.world.MidnightChunkGenerator.*;

public class MidnightNoiseGenerator {
    private static final BiomeProperties BIOME_PROPERTIES = new BiomeProperties();

    public static final int HORIZONTAL_GRANULARITY = 4;
    public static final int VERTICAL_GRANULARITY = 4;

    public static final int NOISE_WIDTH = 16 / HORIZONTAL_GRANULARITY;
    public static final int NOISE_HEIGHT = 256 / VERTICAL_GRANULARITY;

    private static final int BUFFER_WIDTH = NOISE_WIDTH + 1;
    private static final int BUFFER_HEIGHT = NOISE_HEIGHT + 1;

    private static final int BIOME_WEIGHT_RADIUS = 2;

    public static final int BIOME_NOISE_OFFSET = BIOME_WEIGHT_RADIUS;
    public static final int BIOME_NOISE_SIZE = BUFFER_WIDTH + BIOME_WEIGHT_RADIUS * 2;

    private final OctaveNoiseSampler worldNoise;
    private final OctaveNoiseSampler surfaceNoise;
    private final OctaveNoiseSampler ceilingNoise;
    private final OctaveNoiseSampler ridgedSurfaceNoise;
    private final PerlinNoiseSampler pillarNoise;

    private final BiomeWeightTable weightTable;

    public MidnightNoiseGenerator(Random random) {
        this.worldNoise = OctaveNoiseSampler.perlin(random, 3);
        this.worldNoise.setAmplitude(5.0);
        this.worldNoise.setFrequency(0.1);

        this.surfaceNoise = OctaveNoiseSampler.perlin(random, 8);
        this.surfaceNoise.setAmplitude(3.0);
        this.surfaceNoise.setFrequency(0.04);

        this.ceilingNoise = OctaveNoiseSampler.perlin(random, 6);
        this.ceilingNoise.setAmplitude(3.0);
        this.ceilingNoise.setFrequency(0.04);

        this.pillarNoise = new PerlinNoiseSampler(random);
        this.pillarNoise.setFrequency(0.2);

        this.ridgedSurfaceNoise = OctaveNoiseSampler.ridged(random, 3, 4.0);
        this.ridgedSurfaceNoise.setAmplitude(4.0);
        this.ridgedSurfaceNoise.setFrequency(0.08);

        this.weightTable = new BiomeWeightTable(BIOME_WEIGHT_RADIUS);
    }

    // TODO: Don't pass array but rather a sampler type
    public void populateColumnNoise(double[] noise, int x, int z, Biome[] biomeBuffer, CavernousBiome[] cavernousBiomeBuffer) {
        GenerationContext context = new GenerationContext(biomeBuffer, cavernousBiomeBuffer);
        BiomeProperties properties = this.computeBiomeProperties(context, x, z);

        float heightOrigin = (float) SURFACE_LEVEL / VERTICAL_GRANULARITY;
        float maxHeight = 256.0F / VERTICAL_GRANULARITY;

        float minCaveHeight = (float) MIN_CAVE_HEIGHT / VERTICAL_GRANULARITY;
        float maxCaveHeight = (float) MAX_CAVE_HEIGHT / VERTICAL_GRANULARITY;
        float caveHeightRange = maxCaveHeight - minCaveHeight;

        float baseHeight = properties.heightDepth + heightOrigin;
        float cavernFloorHeight = properties.cavernFloorHeight * caveHeightRange + minCaveHeight;
        float cavernCeilingHeight = properties.cavernCeilingHeight * caveHeightRange + minCaveHeight;

        double cavernCenter = (cavernFloorHeight + cavernCeilingHeight) / 2.0;
        double cavernHeight = cavernCeilingHeight - cavernFloorHeight;

        float heightVariation = properties.heightScale * 0.9F + 0.1F;
        float cavernHeightVariation = properties.cavernHeightScale * 0.9F + 0.1F;

        double perlinSurfaceNoise = (this.surfaceNoise.get(x, z) + 1.5) / 3.0;
        double perlinCeilingNoise = (this.ceilingNoise.get(x, z) + 1.5) / 3.0;
        double ridgedSurfaceNoise = (this.ridgedSurfaceNoise.get(x, z) + 1.5) / 3.0;

        double pillarDensity = Math.pow((this.pillarNoise.get(x, z) + 1.0) * 0.5, 4.0);

        double surfaceHeightVariationScale = Math.pow(heightVariation * 2.0, 3.0);
        double cavernHeightVariationScale = Math.pow(cavernHeightVariation * 2.0, 3.0);

        double surfaceHeight = perlinSurfaceNoise + (ridgedSurfaceNoise - perlinSurfaceNoise) * properties.ridgeWeight;
        surfaceHeight = (surfaceHeight * heightVariation * 2.0) + baseHeight;

        double cavernRegionStart = cavernFloorHeight + (perlinSurfaceNoise * cavernHeightVariation * 2.0);
        double cavernRegionEnd = cavernCeilingHeight + (perlinCeilingNoise * 0.15);

        double curveRange = 8.0 / VERTICAL_GRANULARITY;
        RegionInterpolator.Region[] regions = new RegionInterpolator.Region[] {
                RegionInterpolator.region(0.0, cavernRegionStart, 2.5, curveRange),
                RegionInterpolator.region(cavernRegionStart, cavernRegionEnd, properties.cavernDensity, curveRange),
                RegionInterpolator.region(cavernRegionEnd, surfaceHeight, 3.5, curveRange),
                RegionInterpolator.region(surfaceHeight, maxHeight, surfaceHeight - maxHeight, maxHeight - surfaceHeight)
        };

        RegionInterpolator interpolator = new RegionInterpolator(regions, Curve.linear());

        for (int y = 0; y < NOISE_HEIGHT + 1; y++) {
            double surfaceWeight = MathHelper.clamp((y - cavernRegionEnd) / (surfaceHeight - cavernRegionEnd), 0.0, 1.0);
            double cavernWeight = 1.0 - surfaceWeight;

            double densityBias = interpolator.get(y);

            double cavernCenterDistance = Math.min(Math.abs(y - cavernCenter) / cavernHeight, 1.0);
            double pillarFalloff = Math.max(1.0 - Math.pow(cavernCenterDistance, 2.0), 0.0) * 0.125;

            densityBias += (Math.max(pillarDensity * 3.5 - pillarFalloff, 0.0) * cavernWeight * 5.0) * properties.pillarWeight;

            double sampledNoise = this.worldNoise.get(x, y, z);

            double surfaceNoiseDensity = sampledNoise * surfaceHeightVariationScale;
            double cavernNoiseDensity = sampledNoise * cavernHeightVariationScale;
            double noiseDensity = (surfaceNoiseDensity * surfaceWeight) + (cavernNoiseDensity * cavernWeight);

            noise[y] = noiseDensity + densityBias;
        }
    }

    private BiomeProperties computeBiomeProperties(GenerationContext context, int localX, int localZ) {
        BiomeProperties properties = BIOME_PROPERTIES;
        properties.zero();

        float totalWeight = 0.0F;

        Biome originBiome = context.sampleNoiseBiome(localX, localZ);
        for (int neighborZ = -BIOME_WEIGHT_RADIUS; neighborZ <= BIOME_WEIGHT_RADIUS; neighborZ++) {
            for (int neighborX = -BIOME_WEIGHT_RADIUS; neighborX <= BIOME_WEIGHT_RADIUS; neighborX++) {
                Biome neighborBiome = context.sampleNoiseBiome(localX + neighborX, localZ + neighborZ);
                CavernousBiome neighborCavernBiome = context.sampleNoiseCavernBiome(localX + neighborX, localZ + neighborZ);

                float nDepth = neighborBiome.getDepth();
                float nScale = neighborBiome.getScale();

                float nRidgeWeight = 0.0F;
                float nDensityScale = 1.0F;

                if (neighborBiome instanceof SurfaceBiome) {
                    SurfaceBiome surfaceBiome = (SurfaceBiome) neighborBiome;
                    nRidgeWeight = surfaceBiome.getRidgeWeight();
                    nDensityScale = surfaceBiome.getDensityScale();
                }

                float nCavernFloorHeight = neighborCavernBiome.getFloorHeight();
                float nCavernCeilingHeight = neighborCavernBiome.getCeilingHeight();
                float nCavernDensity = neighborCavernBiome.getCavernDensity();
                float nCavernHeightScale = neighborCavernBiome.getHeightScale();
                float nCavernPillarWeight = neighborCavernBiome.getPillarWeight();

                float biomeWeight = this.weightTable.get(neighborX, neighborZ) / (nDepth + 2.0F);
                if (neighborBiome.getDepth() > originBiome.getDepth()) {
                    biomeWeight *= 2.0F;
                }

                properties.heightScale += nScale * biomeWeight;
                properties.heightDepth += nDepth * biomeWeight;
                properties.ridgeWeight += nRidgeWeight * biomeWeight;
                properties.densityScale += nDensityScale * biomeWeight;
                properties.cavernFloorHeight += nCavernFloorHeight * biomeWeight;
                properties.cavernCeilingHeight += nCavernCeilingHeight * biomeWeight;
                properties.cavernDensity += nCavernDensity * biomeWeight;
                properties.cavernHeightScale += nCavernHeightScale * biomeWeight;
                properties.pillarWeight += nCavernPillarWeight * biomeWeight;

                totalWeight += biomeWeight;
            }
        }

        properties.normalize(totalWeight);

        return properties;
    }

    private static class GenerationContext {
        private final Biome[] biomeBuffer;
        private final CavernousBiome[] cavernousBiomeBuffer;

        private GenerationContext(Biome[] biomeBuffer, CavernousBiome[] cavernousBiomeBuffer) {
            this.biomeBuffer = biomeBuffer;
            this.cavernousBiomeBuffer = cavernousBiomeBuffer;
        }

        Biome sampleNoiseBiome(int x, int z) {
            return this.biomeBuffer[(x + BIOME_NOISE_OFFSET) + (z + BIOME_NOISE_OFFSET) * BIOME_NOISE_SIZE];
        }

        CavernousBiome sampleNoiseCavernBiome(int x, int z) {
            return this.cavernousBiomeBuffer[(x + BIOME_NOISE_OFFSET) + (z + BIOME_NOISE_OFFSET) * BIOME_NOISE_SIZE];
        }
    }

    private static class BiomeProperties {
        float heightScale;
        float heightDepth;
        float densityScale;
        float ridgeWeight;
        float cavernFloorHeight;
        float cavernCeilingHeight;
        float cavernDensity;
        float cavernHeightScale;
        float pillarWeight;

        void zero() {
            this.heightScale = 0.0F;
            this.heightDepth = 0.0F;
            this.ridgeWeight = 0.0F;
            this.densityScale = 0.0F;
            this.cavernFloorHeight = 0.0F;
            this.cavernCeilingHeight = 0.0F;
            this.cavernDensity = 0.0F;
            this.cavernHeightScale = 0.0F;
            this.pillarWeight = 0.0F;
        }

        void normalize(float weight) {
            this.heightScale /= weight;
            this.heightDepth /= weight;
            this.ridgeWeight /= weight;
            this.densityScale /= weight;
            this.cavernFloorHeight /= weight;
            this.cavernCeilingHeight /= weight;
            this.cavernDensity /= weight;
            this.cavernHeightScale /= weight;
            this.pillarWeight /= weight;
        }
    }
}
