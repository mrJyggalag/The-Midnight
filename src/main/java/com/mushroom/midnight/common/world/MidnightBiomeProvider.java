package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.registry.ModBiomes;
import com.mushroom.midnight.common.world.layer.MidnightSeedLayer;
import com.mushroom.midnight.common.world.layer.OutlineProducerLayer;
import com.mushroom.midnight.common.world.layer.ReplaceRandomLayer;
import com.mushroom.midnight.common.world.layer.RidgeMergeLayer;
import com.mushroom.midnight.common.world.layer.RidgeSeedLayer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
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
        GenLayer ridgeLayer = new RidgeSeedLayer(10);
        ridgeLayer = new GenLayerVoronoiZoom(20, ridgeLayer);
        ridgeLayer = GenLayerZoom.magnify(30, ridgeLayer, 2);
        ridgeLayer = new OutlineProducerLayer(40, ridgeLayer);

        GenLayer layer = new MidnightSeedLayer(0);
        layer = new GenLayerVoronoiZoom(1000, layer);
        layer = new ReplaceRandomLayer(2000, 5, Biome.getIdForBiome(ModBiomes.OBSCURED_PEAKS), layer);
        layer = new ReplaceRandomLayer(3000, 20, Biome.getIdForBiome(ModBiomes.WARPED_FIELDS), layer);
        layer = new GenLayerFuzzyZoom(4000, layer);
        layer = new RidgeMergeLayer(5000, layer, ridgeLayer);

        layer = GenLayerZoom.magnify(6000, layer, 3);

        return layer;
    }
}
