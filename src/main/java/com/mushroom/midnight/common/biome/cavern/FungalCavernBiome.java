package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class FungalCavernBiome extends CavernousBiome {
    public FungalCavernBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.CAVERN, MidnightSurfaceBuilders.MYCELIUM_CONFIG)
                .cavernDensity(-15.0F)
                .pillarWeight(0.0F)
                .floorHeight(0.1F)
                .ceilingHeight(0.8F)
                .heightScale(0.6F)
        );

//      .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(6, 16))
//      .withFeature(DOUBLE_FUNGI_FEATURE, new ScatterPlacementConfig(4, 8))
//      .withFeature(LARGE_BULB_FUNGUS_FEATURE, new SurfacePlacementConfig(3))
//      .withFeature(BULB_FUNGUS_FEATURE, new SurfacePlacementConfig(5, 8))

        MidnightBiomeConfigurator.addUndergroundSpawns(this);
    }
}
