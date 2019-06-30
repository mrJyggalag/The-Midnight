package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class RunebushGroveBiome extends SurfaceBiome {
    public RunebushGroveBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x8C84BC)
                .depth(0.155F)
                .scale(0.07F)
        );

        MidnightBiomeConfigurator.addLumen(this);

        MidnightBiomeConfigurator.addVigilantForestTreesAndLogs(this);

        // TODO
//      .withFeature(COMMON_SUAVIS_FEATURE, new ScatterPlacementConfig(8, 8))
//      .withFeature(RUNEBUSH_FEATURE, new ScatterPlacementConfig(16, 128))

        MidnightBiomeConfigurator.addGrasses(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addForestSpawns(this);
    }
}
