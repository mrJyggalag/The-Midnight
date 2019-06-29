package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class RunebushGroveBiome extends SurfaceBiome {
    public RunebushGroveBiome() {
        super(new Builder()
                .surfaceBuilder(SurfaceBuilder.DEFAULT, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x8C84BC)
                .depth(0.155F)
                .scale(0.07F)
        );

//      .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
//      .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
//      .withFeature(new IMidnightFeature[] {
//              SHADOWROOT_TREE_FEATURE,
//              DARK_WILLOW_TREE_FEATURE
//      }, new SurfacePlacementConfig(8))
//      .withFeature(DEAD_LOG_FEATURE, new SurfacePlacementConfig(6))
//      .withFeature(COMMON_SUAVIS_FEATURE, new ScatterPlacementConfig(8, 8))
//      .withFeature(RUNEBUSH_FEATURE, new ScatterPlacementConfig(16, 128))
//      .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(4, 16))

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addForestSpawns(this);
    }
}
