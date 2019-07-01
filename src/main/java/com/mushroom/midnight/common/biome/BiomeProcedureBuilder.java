package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.registry.MidnightCavernousBiomes;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import com.mushroom.midnight.common.world.layer.AddOutlineLayer;
import com.mushroom.midnight.common.world.layer.CavernSeedLayer;
import com.mushroom.midnight.common.world.layer.CellSeedLayer;
import com.mushroom.midnight.common.world.layer.CreateGroupPocketsLayer;
import com.mushroom.midnight.common.world.layer.EdgeMergeLayer;
import com.mushroom.midnight.common.world.layer.ProduceOutlineLayer;
import com.mushroom.midnight.common.world.layer.SeedGroupLayer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.SmoothLayer;
import net.minecraft.world.gen.layer.VoroniZoomLayer;
import net.minecraft.world.gen.layer.ZoomLayer;

import java.util.function.LongFunction;

public abstract class BiomeProcedureBuilder<T> {
    private static final int MAX_CACHE_SIZE = 25;

    public BiomeProcedure<LazyArea> buildProcedure(long worldSeed) {
        return this.buildProcedure(value -> new LazyAreaLayerContext(MAX_CACHE_SIZE, worldSeed, value));
    }

    public abstract <A extends IArea, C extends IExtendedNoiseRandom<A>> BiomeProcedure<A> buildProcedure(LongFunction<C> contextFactory);

    private static <A extends IArea, C extends IExtendedNoiseRandom<A>> IAreaFactory<A> buildEdgeHighlightLayer(LongFunction<C> contextFactory, long seed) {
        IAreaFactory<A> valleyLayer = CellSeedLayer.INSTANCE.apply(contextFactory.apply(10 + seed));
        valleyLayer = VoroniZoomLayer.INSTANCE.apply(contextFactory.apply(20 + seed), valleyLayer);
        valleyLayer = LayerUtil.repeat(30 + seed, ZoomLayer.NORMAL, valleyLayer, 2, contextFactory);
        valleyLayer = ProduceOutlineLayer.INSTANCE.apply(contextFactory.apply(40 + seed), valleyLayer);

        return valleyLayer;
    }

    public static class Surface extends BiomeProcedureBuilder<Biome> {
        public static final BiomeProcedureBuilder<Biome> INSTANCE = new Surface();

        private Surface() {
        }

        @Override
        public <A extends IArea, C extends IExtendedNoiseRandom<A>> BiomeProcedure<A> buildProcedure(LongFunction<C> contextFactory) {
            int ridgeId = Registry.BIOME.getId(MidnightSurfaceBiomes.BLACK_RIDGE);
            int plateauId = Registry.BIOME.getId(MidnightSurfaceBiomes.OBSCURED_PLATEAU);
            int valleyId = Registry.BIOME.getId(MidnightSurfaceBiomes.PHANTASMAL_VALLEY);

            IAreaFactory<A> ridgeLayer = buildEdgeHighlightLayer(contextFactory, 100);
            IAreaFactory<A> valleyLayer = buildEdgeHighlightLayer(contextFactory, 200);

            IAreaFactory<A> layer = new SeedGroupLayer(MidnightBiomeGroup.SURFACE).apply(contextFactory.apply(0));
            layer = VoroniZoomLayer.INSTANCE.apply(contextFactory.apply(1000), layer);

            layer = new CreateGroupPocketsLayer(MidnightBiomeGroup.SURFACE_POCKET, 9).apply(contextFactory.apply(2000), layer);
            layer = ZoomLayer.FUZZY.apply(contextFactory.apply(3000), layer);
            layer = new EdgeMergeLayer(id -> id != plateauId, ridgeId).apply(contextFactory.apply(4000), layer, ridgeLayer);

            layer = LayerUtil.repeat(5000, ZoomLayer.NORMAL, layer, 2, contextFactory);

            layer = new EdgeMergeLayer(id -> id == plateauId, valleyId).apply(contextFactory.apply(6000), layer, valleyLayer);

            layer = ZoomLayer.NORMAL.apply(contextFactory.apply(7000), layer);
            layer = SmoothLayer.INSTANCE.apply(contextFactory.apply(8000), layer);

            return BiomeProcedure.of(layer, contextFactory);
        }
    }

    public static class Underground extends BiomeProcedureBuilder<CavernousBiome> {
        public static final BiomeProcedureBuilder<CavernousBiome> INSTANCE = new Underground();

        private Underground() {
        }

        @Override
        public <A extends IArea, C extends IExtendedNoiseRandom<A>> BiomeProcedure<A> buildProcedure(LongFunction<C> contextFactory) {
            int closedCavernId = MidnightCavernousBiomes.getId(MidnightCavernousBiomes.CLOSED_CAVERN);
            int fungalCavernId = MidnightCavernousBiomes.getId(MidnightCavernousBiomes.FUNGAL_CAVERN);

            IAreaFactory<A> passageLayer = buildEdgeHighlightLayer(contextFactory, 300);

            IAreaFactory<A> layer = new CavernSeedLayer(MidnightBiomeGroup.UNDERGROUND).apply(contextFactory.apply(0));
            layer = VoroniZoomLayer.INSTANCE.apply(contextFactory.apply(1000), layer);

            layer = new AddOutlineLayer(closedCavernId).apply(contextFactory.apply(2000), layer);
            layer = new CreateGroupPocketsLayer(MidnightBiomeGroup.UNDERGROUND_POCKET, 8).apply(contextFactory.apply(3000), layer);
            layer = ZoomLayer.FUZZY.apply(contextFactory.apply(4000), layer);

            layer = LayerUtil.repeat(5000, ZoomLayer.NORMAL, layer, 2, contextFactory);

            layer = new EdgeMergeLayer(id -> id == closedCavernId, fungalCavernId).apply(contextFactory.apply(6000), layer, passageLayer);

            layer = ZoomLayer.NORMAL.apply(contextFactory.apply(7000), layer);
            layer = SmoothLayer.INSTANCE.apply(contextFactory.apply(8000), layer);

            return BiomeProcedure.of(layer, contextFactory);
        }
    }
}
