package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BiomeLayerSampler;
import com.mushroom.midnight.common.biome.BiomeLayerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class MidnightBiomeProvider extends BiomeProvider {
    private static final BiomeLayerSampler<Biome> DEFAULT_SAMPLER = new BiomeLayerSampler.Constant<>(Biomes.DEFAULT);

    private final World world;
    private final BiomeLayerType<Biome> sampleLayer;

    public MidnightBiomeProvider(World world, BiomeLayerType<Biome> sampleLayer) {
        super(world.getWorldInfo());
        this.world = world;
        this.sampleLayer = sampleLayer;
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
        if (biomes == null || biomes.length < width * height) {
            biomes = new Biome[width * height];
        }
        return this.getSampler().sampleNoise(biomes, x, z, width, height);
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] biomes, int x, int z, int width, int height, boolean cacheFlag) {
        if (biomes == null || biomes.length < width * height) {
            biomes = new Biome[width * height];
        }
        return this.getSampler().sample(biomes, x, z, width, height);
    }

    @Override
    public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed) {
        int minX = x - radius >> 2;
        int minZ = z - radius >> 2;
        int maxX = x + radius >> 2;
        int maxZ = z + radius >> 2;
        int width = maxX - minX + 1;
        int height = maxZ - minZ + 1;

        Biome[] biomes = this.getSampler().sampleNoise(new Biome[width * height], minX, minZ, width, height);
        for (Biome biome : biomes) {
            if (!allowed.contains(biome)) {
                return false;
            }
        }

        return true;
    }

    @Override
    @Nullable
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
        int minX = x - range >> 2;
        int minZ = z - range >> 2;
        int maxX = x + range >> 2;
        int maxZ = z + range >> 2;
        int width = maxX - minX + 1;
        int height = maxZ - minZ + 1;

        Biome[] sampledBiomes = this.getSampler().sampleNoise(new Biome[width * height], minX, minZ, width, height);

        BlockPos pos = null;

        int count = 0;
        for (int index = 0; index < sampledBiomes.length; ++index) {
            int biomeX = minX + index % width << 2;
            int biomeZ = minZ + index / width << 2;

            Biome biome = sampledBiomes[index];

            if (biomes.contains(biome) && (pos == null || random.nextInt(count + 1) == 0)) {
                pos = new BlockPos(biomeX, 0, biomeZ);
                count++;
            }
        }

        return pos;
    }

    private BiomeLayerSampler<Biome> getSampler() {
        return this.world.getCapability(Midnight.MULTI_LAYER_BIOME_SAMPLER_CAP, null).map(multiLayerSampler -> {
            BiomeLayerSampler<Biome> layer = multiLayerSampler.getLayer(this.sampleLayer);
            return layer != null ? layer : DEFAULT_SAMPLER;
        }).orElse(DEFAULT_SAMPLER);
    }
}
