package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.world.layer.MidnightSeedLayer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.storage.WorldInfo;

public class MidnightBiomeProvider extends BiomeProvider {
    public MidnightBiomeProvider(WorldInfo info) {
        super(info);
    }

    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
        GenLayer generationLayer = getBiomeProcedure();
        GenLayerVoronoiZoom layer = new GenLayerVoronoiZoom(10, generationLayer);
        layer.initWorldGenSeed(seed);

        GenLayer[] layers = new GenLayer[] { generationLayer, layer, generationLayer };
        return super.getModdedBiomeGenerators(worldType, seed, layers);
    }

    private static GenLayer getBiomeProcedure() {
        GenLayer layer = new MidnightSeedLayer(0);
        layer = new GenLayerVoronoiZoom(1000, layer);
        layer = new GenLayerFuzzyZoom(2000, layer);
        layer = GenLayerZoom.magnify(3000, layer, 3);
        layer = new GenLayerFuzzyZoom(4000, layer);
        return layer;
    }
}
