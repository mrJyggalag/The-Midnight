package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;

public class DeceitfulBogBiome extends SurfaceBiome {
    public DeceitfulBogBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.BOG, MidnightSurfaceBuilders.PEAT_CONFIG)
                .category(Category.SWAMP)
                .grassColor(0x8893AD)
                .depth(-0.9F)
                .scale(0.2F)
                .ridgeWeight(0.0F)
                .wet()
        );

        // TODO
//      .withFeature(LARGE_BOGSHROOM_FEATURE, new SurfacePlacementConfig(1))
//      .withFeature(BOGWEED_FEATURE, new ScatterPlacementConfig(1, 24))
//      .withFeature(DOUBLE_BOG_FUNGI_FEATURE, new ScatterPlacementConfig(1, 8))

        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.BOG_DEAD_TREE, IFeatureConfig.NO_FEATURE_CONFIG,
                Placement.CHANCE_HEIGHTMAP, new ChanceConfig(3)
        ));

        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.RANDOM_BOOLEAN_SELECTOR, new TwoFeatureChoiceConfig(
                        MidnightFeatures.SHADOWROOT_TREE, IFeatureConfig.NO_FEATURE_CONFIG,
                        MidnightFeatures.DARK_WILLOW_TREE, IFeatureConfig.NO_FEATURE_CONFIG
                ),
                Placement.COUNT_HEIGHTMAP, new FrequencyConfig(4)
        ));

        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.DEAD_LOG, IFeatureConfig.NO_FEATURE_CONFIG,
                Placement.COUNT_HEIGHTMAP, new FrequencyConfig(5)
        ));

        MidnightBiomeConfigurator.addSmallBogFungis(this);

        MidnightBiomeConfigurator.addAlgaeAndMoss(this);
        MidnightBiomeConfigurator.addGrasses(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);

        this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(MidnightEntities.DECEITFUL_SNAPPER, 100, 5, 10));
    }
}
