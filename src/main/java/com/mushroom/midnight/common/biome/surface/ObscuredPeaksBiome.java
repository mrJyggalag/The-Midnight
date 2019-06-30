package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class ObscuredPeaksBiome extends SurfaceBiome {
    public ObscuredPeaksBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.NIGHTSTONE_CONFIG)
                .category(Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.5F)
        );

        MidnightBiomeConfigurator.addSmallFungis(this);
        MidnightBiomeConfigurator.addLumen(this);
        MidnightBiomeConfigurator.addTrenchstoneBoulders(this);
        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addRockySpawns(this);
    }
}
