package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.world.layer.CellSeedLayer;
import com.mushroom.midnight.common.world.layer.CreateGroupPocketsLayer;
import com.mushroom.midnight.common.world.layer.OutlineProducerLayer;
import com.mushroom.midnight.common.world.layer.RidgeMergeLayer;
import com.mushroom.midnight.common.world.layer.SeedGroupLayer;
import com.mushroom.midnight.common.world.layer.ValleyMergeLayer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.storage.WorldInfo;

public class MidnightBiomeProvider extends BiomeProvider {
    public MidnightBiomeProvider(WorldInfo info) {
        super(info);
    }

    // TODO: All biome sampling on world cap

    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
        return this.buildProcedureArray(worldType, seed, buildBiomeProcedure());
    }

    private GenLayer[] buildProcedureArray(WorldType worldType, long seed, GenLayer noiseLayer) {
        GenLayerVoronoiZoom layer = new GenLayerVoronoiZoom(10, noiseLayer);
        layer.initWorldGenSeed(seed);

        GenLayer[] layers = new GenLayer[] { noiseLayer, layer, noiseLayer };
        return super.getModdedBiomeGenerators(worldType, seed, layers);
    }

    private static GenLayer buildBiomeProcedure() {
        GenLayer ridgeLayer = buildRidgeLayer();
        GenLayer valleyLayer = buildValleyLayer();

        GenLayer layer = new SeedGroupLayer(0, MidnightBiomeGroup.SURFACE);
        layer = new GenLayerVoronoiZoom(1000, layer);

        layer = new CreateGroupPocketsLayer(2000, layer, MidnightBiomeGroup.SURFACE_POCKET, 100);
        layer = new GenLayerFuzzyZoom(3000, layer);
        layer = new RidgeMergeLayer(4000, layer, ridgeLayer);

        layer = GenLayerZoom.magnify(5000, layer, 2);

        layer = new ValleyMergeLayer(6000, layer, valleyLayer);

        layer = GenLayerZoom.magnify(7000, layer, 1);
        layer = new GenLayerSmooth(8000, layer);

        return layer;
    }

    private static GenLayer buildRidgeLayer() {
        GenLayer ridgeLayer = new CellSeedLayer(10);
        ridgeLayer = new GenLayerVoronoiZoom(20, ridgeLayer);
        ridgeLayer = GenLayerZoom.magnify(30, ridgeLayer, 2);
        ridgeLayer = new OutlineProducerLayer(40, ridgeLayer);

        return ridgeLayer;
    }

    private static GenLayer buildValleyLayer() {
        GenLayer valleyLayer = new CellSeedLayer(50);
        valleyLayer = new GenLayerVoronoiZoom(60, valleyLayer);
        valleyLayer = GenLayerZoom.magnify(70, valleyLayer, 2);
        valleyLayer = new OutlineProducerLayer(80, valleyLayer);

        return valleyLayer;
    }
}
