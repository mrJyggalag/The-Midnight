package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.registry.ModCavernousBiomes;
import com.mushroom.midnight.common.registry.ModSurfaceBiomes;
import com.mushroom.midnight.common.world.layer.AddOutlineLayer;
import com.mushroom.midnight.common.world.layer.CavernSeedLayer;
import com.mushroom.midnight.common.world.layer.CellSeedLayer;
import com.mushroom.midnight.common.world.layer.CreateGroupPocketsLayer;
import com.mushroom.midnight.common.world.layer.EdgeMergeLayer;
import com.mushroom.midnight.common.world.layer.OutlineProducerLayer;
import com.mushroom.midnight.common.world.layer.RidgeMergeLayer;
import com.mushroom.midnight.common.world.layer.SeedGroupLayer;
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
        GenLayer ridgeLayer = buildEdgeHighlightLayer(100);
        GenLayer valleyLayer = buildEdgeHighlightLayer(200);

        GenLayer layer = new SeedGroupLayer(0, MidnightBiomeGroup.SURFACE);
        layer = new GenLayerVoronoiZoom(1000, layer);

        layer = new CreateGroupPocketsLayer(2000, layer, MidnightBiomeGroup.SURFACE_POCKET, 10);
        layer = new GenLayerFuzzyZoom(3000, layer);
        layer = new RidgeMergeLayer(4000, layer, ridgeLayer);

        layer = GenLayerZoom.magnify(5000, layer, 2);

        int plateauId = Biome.getIdForBiome(ModSurfaceBiomes.OBSCURED_PLATEAU);
        int valleyId = Biome.getIdForBiome(ModSurfaceBiomes.PHANTASMAL_VALLEY);
        layer = new EdgeMergeLayer(6000, layer, valleyLayer, plateauId, valleyId);

        layer = GenLayerZoom.magnify(7000, layer, 1);
        layer = new GenLayerSmooth(8000, layer);

        return layer;
    }

    private static GenLayer buildUndergroundProcedure() {
        int closedCavernId = ModCavernousBiomes.getId(ModCavernousBiomes.CLOSED_CAVERN);

        GenLayer passageLayer = buildEdgeHighlightLayer(300);

        GenLayer layer = new CavernSeedLayer(0, MidnightBiomeGroup.UNDERGROUND);
        layer = new GenLayerVoronoiZoom(1000, layer);

        layer = new AddOutlineLayer(2000, layer, closedCavernId);
        layer = new CreateGroupPocketsLayer(3000, layer, MidnightBiomeGroup.UNDERGROUND_POCKET, 8);
        layer = new GenLayerFuzzyZoom(4000, layer);

        layer = GenLayerZoom.magnify(5000, layer, 2);

        int fungalCavernId = ModCavernousBiomes.getId(ModCavernousBiomes.FUNGAL_CAVERN);
        layer = new EdgeMergeLayer(6000, layer, passageLayer, closedCavernId, fungalCavernId);

        layer = GenLayerZoom.magnify(7000, layer, 1);

        layer = new GenLayerSmooth(8000, layer);

        return layer;
    }

    private static GenLayer buildEdgeHighlightLayer(long seed) {
        GenLayer valleyLayer = new CellSeedLayer(10 + seed);
        valleyLayer = new GenLayerVoronoiZoom(20 + seed, valleyLayer);
        valleyLayer = GenLayerZoom.magnify(30 + seed, valleyLayer, 2);
        valleyLayer = new OutlineProducerLayer(40 + seed, valleyLayer);

        return valleyLayer;
    }
}
