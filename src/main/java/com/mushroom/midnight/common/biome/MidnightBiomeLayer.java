package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.registry.ModCavernousBiomes;
import com.mushroom.midnight.common.world.layer.AddOutlineLayer;
import com.mushroom.midnight.common.world.layer.CavernSeedLayer;
import com.mushroom.midnight.common.world.layer.CellSeedLayer;
import com.mushroom.midnight.common.world.layer.CreateGroupPocketsLayer;
import com.mushroom.midnight.common.world.layer.OutlineProducerLayer;
import com.mushroom.midnight.common.world.layer.RidgeMergeLayer;
import com.mushroom.midnight.common.world.layer.SeedGroupLayer;
import com.mushroom.midnight.common.world.layer.ValleyMergeLayer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

import java.util.function.Supplier;

public final class MidnightBiomeLayer<T> implements BiomeLayerType<T> {
    public static final MidnightBiomeLayer<Biome> SURFACE = new MidnightBiomeLayer<>(MidnightBiomeLayer::buildSurfaceProcedure);
    public static final MidnightBiomeLayer<CavernousBiome> UNDERGROUND = new MidnightBiomeLayer<>(MidnightBiomeLayer::buildUndergroundProcedure);

    private final Supplier<GenLayer> procedureBuilder;

    private MidnightBiomeLayer(Supplier<GenLayer> procedureBuilder) {
        this.procedureBuilder = procedureBuilder;
    }

    public GenLayer buildProcedure() {
        return this.procedureBuilder.get();
    }

    private static GenLayer buildSurfaceProcedure() {
        GenLayer ridgeLayer = buildRidgeLayer();
        GenLayer valleyLayer = buildValleyLayer();

        GenLayer layer = new SeedGroupLayer(0, MidnightBiomeGroup.SURFACE);
        layer = new GenLayerVoronoiZoom(1000, layer);

        layer = new CreateGroupPocketsLayer(2000, layer, MidnightBiomeGroup.SURFACE_POCKET, 6);
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

    private static GenLayer buildUndergroundProcedure() {
        GenLayer layer = new CavernSeedLayer(0, MidnightBiomeGroup.UNDERGROUND);
        layer = new GenLayerVoronoiZoom(1000, layer);

        layer = new AddOutlineLayer(2000, layer, ModCavernousBiomes.getId(ModCavernousBiomes.CLOSED_CAVERN));
        layer = new CreateGroupPocketsLayer(3000, layer, MidnightBiomeGroup.UNDERGROUND_POCKET, 6);
        layer = new GenLayerFuzzyZoom(4000, layer);

        layer = GenLayerZoom.magnify(5000, layer, 3);
        layer = new GenLayerSmooth(6000, layer);

        return layer;
    }
}
