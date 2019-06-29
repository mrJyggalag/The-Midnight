package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.cavern.CavernStructureConfig;
import com.mushroom.midnight.common.biome.cavern.CavernousBiomeConfig;
import com.mushroom.midnight.common.block.BladeshroomBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightFeatures;
import com.mushroom.midnight.common.world.feature.config.ScatterPlacementConfig;
import com.mushroom.midnight.common.world.feature.config.SurfacePlacementConfig;
import com.mushroom.midnight.common.world.feature.config.UniformCompositionConfig;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.DoublePlantConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GrassFeatureConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;

import static com.mushroom.midnight.common.biome.MidnightBiomeFeatures.*;

public class MidnightBiomeConfigurator {
    public static final FeatureConfig GREAT_CAVERN_FEATURE_CONFIG = FeatureConfig.builder()
            .build();

    public static final FeatureConfig CRYSTAL_CAVERN_FEATURE_CONFIG = FeatureConfig.builder()
            .withFeature(ROUXE_FEATURE, new SurfacePlacementConfig(5))
            .build();

    public static final FeatureConfig FUNGAL_CAVERN_FEATURE_CONFIG = FeatureConfig.builder()
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(6, 16))
            .withFeature(DOUBLE_FUNGI_FEATURE, new ScatterPlacementConfig(4, 8))
            .withFeature(LARGE_BULB_FUNGUS_FEATURE, new SurfacePlacementConfig(3))
            .withFeature(BULB_FUNGUS_FEATURE, new SurfacePlacementConfig(5, 8))
            .build();

    public static final SpawnerConfig GREAT_CAVERN_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(UNDERGROUND_SPAWN_CONFIG)
            .build();

    public static final SpawnerConfig CRYSTAL_CAVERN_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(UNDERGROUND_SPAWN_CONFIG)
            .withMonster(new Biome.SpawnListEntry(MidnightEntities.NOVA, 100, 1, 2))
            .build();

    public static final SpawnerConfig FUNGAL_CAVERN_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(UNDERGROUND_SPAWN_CONFIG)
            .build();

    public static final CavernStructureConfig GREAT_CAVERN_STRUCTURE_CONFIG = new CavernStructureConfig()
            .withCaveRadiusScale(0.0F)
            .withHeightVariation(0.4F);

    public static final CavernStructureConfig FUNGAL_CAVERN_STRUCTURE_CONFIG = new CavernStructureConfig()
            .withCavernDensity(-15.0F)
            .withPillarWeight(0.0F)
            .withFloorHeight(0.1F)
            .withCeilingHeight(0.8F)
            .withHeightVariation(0.6F);

    public static final CavernousBiomeConfig GREAT_CAVERN_CONFIG = CavernousBiomeConfig.builder()
            .withSpawner(GREAT_CAVERN_SPAWN_CONFIG)
            .withFeatures(GREAT_CAVERN_FEATURE_CONFIG)
            .withStructure(GREAT_CAVERN_STRUCTURE_CONFIG)
            .build();

    public static final CavernousBiomeConfig CRYSTAL_CAVERN_CONFIG = CavernousBiomeConfig.builder()
            .withSpawner(CRYSTAL_CAVERN_SPAWN_CONFIG)
            .withFeatures(CRYSTAL_CAVERN_FEATURE_CONFIG)
            .withStructure(GREAT_CAVERN_STRUCTURE_CONFIG)
            .build();

    public static final CavernousBiomeConfig FUNGAL_CAVERN_CONFIG = CavernousBiomeConfig.builder()
            .withSurface(new SurfaceConfig().withTopState(MidnightBlocks.MYCELIUM.getDefaultState()).withFillerState(MidnightBlocks.NIGHTSTONE.getDefaultState()).withWetState(MidnightBlocks.NIGHTSTONE.getDefaultState()))
            .withSpawner(FUNGAL_CAVERN_SPAWN_CONFIG)
            .withFeatures(FUNGAL_CAVERN_FEATURE_CONFIG)
            .withStructure(FUNGAL_CAVERN_STRUCTURE_CONFIG)
            .build();

    public static void addGlobalFeatures(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(
                MidnightFeatures.HEAP, new UniformCompositionConfig(MidnightBlocks.ROCKSHROOM.getDefaultState()),
                Placement.CHANCE_HEIGHTMAP_DOUBLE, new ChanceConfig(100)
        ));

        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.BUSH, new BushConfig(MidnightBlocks.GHOST_PLANT.getDefaultState()),
                Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE, new HeightWithChanceConfig(4, 0.3F)
        ));

//      .withFeature(FeatureSorting.LAST, DRAGON_NEST_FEATURE, new DragonNestPlacementConfig(32, 32))
//      .withFeature(FeatureSorting.LAST, UNSTABLE_BUSH_FEATURE, new ParcelPlacementConfig(6, 10, 0.2f))
//      .withFeature(FeatureSorting.LAST, UNDERGROUND_FEATURES, new UndergroundPlacementConfig(1, 3, 10, 50))
    }

    public static void addLumen(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.BUSH, new BushConfig(MidnightBlocks.LUMEN_BUD.getDefaultState()),
                Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(1)
        ));

        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.DOUBLE_PLANT, new DoublePlantConfig(MidnightBlocks.DOUBLE_LUMEN_BUD.getDefaultState()),
                Placement.CHANCE_HEIGHTMAP_DOUBLE, new ChanceConfig(3)
        ));
    }

    public static void addSmallFungis(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.FUNGI_FLOWERS, IFeatureConfig.NO_FEATURE_CONFIG,
                Placement.COUNT_HEIGHTMAP_32, new FrequencyConfig(1)
        ));
    }

    public static void addGrasses(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.GRASS, new GrassFeatureConfig(MidnightBlocks.TALL_MIDNIGHT_GRASS.getDefaultState()),
                Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(6)
        ));

        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.DOUBLE_PLANT, new DoublePlantConfig(MidnightBlocks.DOUBLE_MIDNIGHT_GRASS.getDefaultState()),
                Placement.COUNT_HEIGHTMAP_32, new FrequencyConfig(3)
        ));
    }

    public static void addBoulders(Biome biome) {
//                .withFeature(TRENCHSTONE_BOULDER_FEATURE, new SurfacePlacementConfig(-3, 1))
    }

    public static void addVigilantForestFeatures(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.RANDOM_BOOLEAN_SELECTOR, new TwoFeatureChoiceConfig(
                        MidnightFeatures.SHADOWROOT_TREE, IFeatureConfig.NO_FEATURE_CONFIG,
                        MidnightFeatures.DARK_WILLOW_TREE, IFeatureConfig.NO_FEATURE_CONFIG
                ),
                Placement.COUNT_HEIGHTMAP, new FrequencyConfig(8)
        ));

        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.SUAVIS, IFeatureConfig.NO_FEATURE_CONFIG,
                Placement.CHANCE_HEIGHTMAP_DOUBLE, new ChanceConfig(32)
        ));

        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.DEAD_LOG, IFeatureConfig.NO_FEATURE_CONFIG,
                Placement.COUNT_HEIGHTMAP, new FrequencyConfig(6)
        ));

        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.DEAD_TREE, IFeatureConfig.NO_FEATURE_CONFIG,
                Placement.CHANCE_HEIGHTMAP, new ChanceConfig(6)
        ));

        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.BUSH, new BushConfig(MidnightBlocks.VIOLEAF.getDefaultState()),
                Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(2)
        ));
    }

    public static void addFungiForestFeatures(Biome biome) {
//       .withFeature(LARGE_FUNGI_FEATURES, new SurfacePlacementConfig(6))
//       .withFeature(DOUBLE_FUNGI_FEATURE, new ScatterPlacementConfig(4, 8))
    }

    public static void addBladeshrooms(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.BUSH, new BushConfig(MidnightBlocks.BLADESHROOM.getDefaultState().with(BladeshroomBlock.STAGE, BladeshroomBlock.Stage.CAPPED)),
                Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(1)
        ));
    }

    public static void addStandardCreatureSpawns(Biome biome) {
        addCreature(biome, MidnightEntities.NIGHTSTAG, 100, 1, 3);
    }

    public static void addStandardMonsterSpawns(Biome biome) {
        addMonster(biome, MidnightEntities.RIFTER, 100, 1, 2);
        addMonster(biome, EntityType.ENDERMAN, 10, 4, 4);
    }

    public static void addRockySpawns(Biome biome) {
        addMonster(biome, MidnightEntities.HUNTER, 5, 1, 2);
    }

    public static void addUndergroundSpawns(Biome biome) {
        addMonster(biome, MidnightEntities.STINGER, 100, 2, 4);
        addMonster(biome, EntityType.ENDERMAN, 10, 4, 4);
    }

    public static void addForestSpawns(Biome biome) {
        addMonster(biome, MidnightEntities.SKULK, 100, 1, 2);
    }

    private static void addCreature(Biome biome, EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        addSpawn(biome, EntityClassification.CREATURE, type, weight, minGroupSize, maxGroupSize);
    }

    private static void addMonster(Biome biome, EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        addSpawn(biome, EntityClassification.MONSTER, type, weight, minGroupSize, maxGroupSize);
    }

    private static void addSpawn(Biome biome, EntityClassification classification, EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        biome.getSpawns(classification).add(new Biome.SpawnListEntry(type, weight, minGroupSize, maxGroupSize));
    }
}
