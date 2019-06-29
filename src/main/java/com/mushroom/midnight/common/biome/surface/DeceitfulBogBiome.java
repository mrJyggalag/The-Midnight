package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;

public class DeceitfulBogBiome extends SurfaceBiome {
    public DeceitfulBogBiome() {
        super(new Builder()
                .surfaceBuilder(MidnightSurfaceBuilders.BOG, MidnightSurfaceBuilders.PEAT_CONFIG)
                .category(Category.SWAMP)
                .grassColor(0x8893AD)
                .depth(-0.9F)
                .scale(0.2F)
                .ridgeWeight(0.0F)
                .wet()
        );

//        .withFeature(ROCKSHROOM_HEAP_FEATURE, new SurfacePlacementConfig(-99, 1))
//        .withFeature(BOG_DEAD_TREE_FEATURE, new SurfacePlacementConfig(-1, 1))
//        .withFeature(new IMidnightFeature[] {
//                SHADOWROOT_TREE_FEATURE,
//                DARK_WILLOW_TREE_FEATURE
//        }, new SurfacePlacementConfig(4))
//        .withFeature(LARGE_BOGSHROOM_FEATURE, new SurfacePlacementConfig(1))
//        .withFeature(BOGWEED_FEATURE, new ScatterPlacementConfig(1, 24))
//        .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(5, 32))
//        .withFeature(DOUBLE_GRASS_FEATURE, new ScatterPlacementConfig(3, 16))
//        .withFeature(DECEITFUL_MOSS_FEATURE, new ScatterPlacementConfig(16, 32))
//        .withFeature(BOG_FUNGI_FEATURE, new ScatterPlacementConfig(2, 16))
//        .withFeature(DOUBLE_BOG_FUNGI_FEATURE, new ScatterPlacementConfig(1, 8))
//        .withFeature(DEAD_LOG_FEATURE, new SurfacePlacementConfig(5))
//        .withFeature(DECEITFUL_ALGAE_FEATURE, new ScatterPlacementConfig(10, 20))

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);

        this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(MidnightEntities.DECEITFUL_SNAPPER, 100, 5, 10));
    }
}
