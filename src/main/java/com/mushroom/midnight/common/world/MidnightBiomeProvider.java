package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.world.layer.ApplyBiomeGroupLayer;
import com.mushroom.midnight.common.world.layer.CellSeedLayer;
import com.mushroom.midnight.common.world.layer.CraterEdgeLayer;
import com.mushroom.midnight.common.world.layer.MidnightSeedLayer;
import com.mushroom.midnight.common.world.layer.OutlineProducerLayer;
import com.mushroom.midnight.common.world.layer.RidgeMergeLayer;
import com.mushroom.midnight.common.world.layer.ValleyMergeLayer;
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
        GenLayer generationLayer = buildBiomeProcedure();
        GenLayerVoronoiZoom layer = new GenLayerVoronoiZoom(10, generationLayer);
        layer.initWorldGenSeed(seed);

        GenLayer[] layers = new GenLayer[] { generationLayer, layer, generationLayer };
        return super.getModdedBiomeGenerators(worldType, seed, layers);
    }

    private static GenLayer buildBiomeProcedure() {
        GenLayer ridgeLayer = new CellSeedLayer(10);
        ridgeLayer = new GenLayerVoronoiZoom(20, ridgeLayer);
        ridgeLayer = GenLayerZoom.magnify(30, ridgeLayer, 2);
        ridgeLayer = new OutlineProducerLayer(40, ridgeLayer);

        GenLayer valleyLayer = new CellSeedLayer(50);
        valleyLayer = new GenLayerVoronoiZoom(60, valleyLayer);
        valleyLayer = GenLayerZoom.magnify(70, valleyLayer, 1);
        valleyLayer = new OutlineProducerLayer(80, valleyLayer);

        GenLayer layer = new MidnightSeedLayer(0);
        layer = new GenLayerVoronoiZoom(1000, layer);

        layer = new ApplyBiomeGroupLayer(2000, layer, MidnightBiomeGroup.SMALL);
        layer = new GenLayerFuzzyZoom(3000, layer);
        layer = new RidgeMergeLayer(4000, layer, ridgeLayer);

        layer = GenLayerZoom.magnify(5000, layer, 2);

        layer = new ValleyMergeLayer(6000, layer, valleyLayer);
        layer = new CraterEdgeLayer(7000, layer);

        layer = GenLayerZoom.magnify(8000, layer, 1);

        return layer;
    }
}
