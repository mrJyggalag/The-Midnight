package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class HillyVigilantForestBiome extends SurfaceBiome {
    public HillyVigilantForestBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .depth(2.25F)
                .scale(0.4F)
        );

        MidnightBiomeConfigurator.addLumen(this);
        MidnightBiomeConfigurator.addSmallFungis(this);
        MidnightBiomeConfigurator.addGrasses(this);
        MidnightBiomeConfigurator.addVigilantForestVegetation(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addForestSpawns(this);
    }
}
