package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.feature.BoulderFeature;
import com.mushroom.midnight.common.world.feature.CrystalClusterFeature;
import com.mushroom.midnight.common.world.feature.DeadTreeFeature;
import com.mushroom.midnight.common.world.feature.DefaultTreeFeature;
import com.mushroom.midnight.common.world.feature.DoubleFungiFeature;
import com.mushroom.midnight.common.world.feature.DoublePlantFeature;
import com.mushroom.midnight.common.world.feature.FungiFeature;
import com.mushroom.midnight.common.world.feature.IMidnightFeature;
import com.mushroom.midnight.common.world.feature.LargeFungiFeature;
import com.mushroom.midnight.common.world.feature.MiasmaSourceFeature;
import com.mushroom.midnight.common.world.feature.PlantFeature;
import com.mushroom.midnight.common.world.feature.ShadowrootTreeFeature;
import com.mushroom.midnight.common.world.feature.config.ScatterPlacementConfig;
import com.mushroom.midnight.common.world.feature.config.SurfacePlacementConfig;
import net.minecraft.block.BlockBush;

public class MidnightBiomeConfigs {
    public static final IMidnightFeature SHADOWROOT_TREE_FEATURE = new ShadowrootTreeFeature();
    public static final IMidnightFeature DARK_WILLOW_TREE_FEATURE = new DefaultTreeFeature(ModBlocks.DARK_WILLOW_LOG, ModBlocks.DARK_WILLOW_LEAVES, 8);
    public static final IMidnightFeature DEAD_TREE_FEATURE = new DeadTreeFeature();

    public static final IMidnightFeature TALL_GRASS_FEATURE = new PlantFeature(
            ModBlocks.TALL_MIDNIGHT_GRASS.getDefaultState(),
            ((BlockBush) ModBlocks.TALL_MIDNIGHT_GRASS)::canBlockStay
    );

    public static final IMidnightFeature DOUBLE_GRASS_FEATURE = new DoublePlantFeature(
            ModBlocks.DOUBLE_MIDNIGHT_GRASS.getDefaultState(),
            (world, pos, state) -> ModBlocks.DOUBLE_MIDNIGHT_GRASS.canPlaceBlockAt(world, pos)
    );

    public static final IMidnightFeature LUMEN_FEATURE = new PlantFeature(
            ModBlocks.LUMEN_BUD.getDefaultState(),
            ((BlockBush) ModBlocks.LUMEN_BUD)::canBlockStay
    );

    public static final IMidnightFeature DECEITFUL_ALGAE_FEATURE = new PlantFeature(
            ModBlocks.DECEITFUL_ALGAE.getDefaultState(),
            (world, pos, state) -> ModBlocks.DECEITFUL_ALGAE.canPlaceBlockAt(world, pos)
    );

    public static final IMidnightFeature DOUBLE_LUMEN_FEATURE = new DoublePlantFeature(
            ModBlocks.DOUBLE_LUMEN_BUD.getDefaultState(),
            (world, pos, state) -> ModBlocks.DOUBLE_LUMEN_BUD.canPlaceBlockAt(world, pos)
    );

    public static final IMidnightFeature FUNGI_FEATURE = new FungiFeature();
    public static final IMidnightFeature DOUBLE_FUNGI_FEATURE = new DoubleFungiFeature();

    public static final IMidnightFeature CRYSTAL_FLOWER_FEATURE = new PlantFeature(
            ModBlocks.CRYSTAL_FLOWER.getDefaultState(),
            ((BlockBush) ModBlocks.CRYSTAL_FLOWER)::canBlockStay
    );

    public static final IMidnightFeature[] LARGE_FUNGI_FEATURES = new LargeFungiFeature[] {
            new LargeFungiFeature(
                    ModBlocks.DEWSHROOM_STEM.getDefaultState(),
                    ModBlocks.DEWSHROOM_HAT.getDefaultState()
            ),
            new LargeFungiFeature(
                    ModBlocks.NIGHTSHROOM_STEM.getDefaultState(),
                    ModBlocks.NIGHTSHROOM_HAT.getDefaultState()
            ),
            new LargeFungiFeature(
                    ModBlocks.VIRIDSHROOM_STEM.getDefaultState(),
                    ModBlocks.VIRIDSHROOM_HAT.getDefaultState()
            )
    };

    public static final IMidnightFeature BLOOMCRYSTAL_FEATURE = new CrystalClusterFeature(3, 4,
            ModBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(),
            ModBlocks.BLOOMCRYSTAL.getDefaultState()
    );

    public static final IMidnightFeature BLOOMCRYSTAL_SPIRE_FEATURE = new CrystalClusterFeature(1, 4,
            ModBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(),
            ModBlocks.BLOOMCRYSTAL.getDefaultState()
    );

    public static final IMidnightFeature NIGHTSTONE_BOULDER_FEATURE = new BoulderFeature(ModBlocks.NIGHTSTONE.getDefaultState(), 2);

    public static final IMidnightFeature MIASMA_SOURCE_FEATURE = new MiasmaSourceFeature();

    public static final MidnightBiomeConfig VEGETATED_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(6, 64))
            .withFeature(DOUBLE_GRASS_FEATURE, new ScatterPlacementConfig(3, 32))
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
            .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(1, 16))
            .build();

    public static final MidnightBiomeConfig ROCKY_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
            .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(2, 16))
            .withTopBlock(ModBlocks.NIGHTSTONE.getDefaultState())
            .withFillerBlock(ModBlocks.NIGHTSTONE.getDefaultState())
            .build();

    public static final MidnightBiomeConfig BLACK_RIDGE_CONFIG = MidnightBiomeConfig.builder(ROCKY_CONFIG)
            .build();

    public static final MidnightBiomeConfig OBSCURED_PEAKS_CONFIG = MidnightBiomeConfig.builder(ROCKY_CONFIG)
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(2, 16))
            .build();

    public static final MidnightBiomeConfig VIGILANT_FOREST_CONFIG = MidnightBiomeConfig.builder(VEGETATED_CONFIG)
            .withFeature(new IMidnightFeature[] {
                    SHADOWROOT_TREE_FEATURE,
                    DARK_WILLOW_TREE_FEATURE
            }, new SurfacePlacementConfig(8))
            .withFeature(DEAD_TREE_FEATURE, new SurfacePlacementConfig(6))
            .build();

    public static final MidnightBiomeConfig FUNGI_FOREST_CONFIG = MidnightBiomeConfig.builder(VEGETATED_CONFIG)
            .withFeature(LARGE_FUNGI_FEATURES, new SurfacePlacementConfig(6))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(8, 16))
            .withFeature(DOUBLE_FUNGI_FEATURE, new ScatterPlacementConfig(6, 8))
            .withGrassColor(0x8489B5)
            .build();

    public static final MidnightBiomeConfig CRYSTAL_SPIRES_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(SHADOWROOT_TREE_FEATURE, new SurfacePlacementConfig(-3, 1))
            .withFeature(BLOOMCRYSTAL_FEATURE, new SurfacePlacementConfig(3, 4))
            .withFeature(BLOOMCRYSTAL_SPIRE_FEATURE, new SurfacePlacementConfig(2, 3))
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
            .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(CRYSTAL_FLOWER_FEATURE, new ScatterPlacementConfig(5, 12))
            .withGrassColor(0xBAA3C6)
            .build();

    public static final MidnightBiomeConfig WARPED_FIELDS_CONFIG = MidnightBiomeConfig.builder(VEGETATED_CONFIG)
            .withFeature(SHADOWROOT_TREE_FEATURE, new SurfacePlacementConfig(-3, 1))
            .withFeature(CRYSTAL_FLOWER_FEATURE, new ScatterPlacementConfig(1, 12))
            .withDensityScale(0.5F)
            .withRidgeWeight(0.0F)
            .build();

    public static final MidnightBiomeConfig MOLTEN_CRATER_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(MIASMA_SOURCE_FEATURE, new SurfacePlacementConfig(16))
            .withRidgeWeight(0.0F)
            .withTopBlock(ModBlocks.TRENCHSTONE.getDefaultState())
            .withFillerBlock(ModBlocks.TRENCHSTONE.getDefaultState())
            .build();

    public static final MidnightBiomeConfig DECEITFUL_BOG_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(new IMidnightFeature[] {
                    SHADOWROOT_TREE_FEATURE,
                    DARK_WILLOW_TREE_FEATURE
            }, new SurfacePlacementConfig(4))
            .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(2, 32))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(1, 4))
            .withFeature(DEAD_TREE_FEATURE, new SurfacePlacementConfig(7))
            .withFeature(DECEITFUL_ALGAE_FEATURE, new ScatterPlacementConfig(8, 10))
            .withTopBlock(ModBlocks.DECEITFUL_PEAT.getDefaultState())
            .withGrassColor(0x8893AD)
            .withRidgeWeight(0.0F)
            .build();

    public static final MidnightBiomeConfig NIGHT_PLAINS_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(2, 32))
            .withFeature(new IMidnightFeature[] {
                    SHADOWROOT_TREE_FEATURE,
                    DARK_WILLOW_TREE_FEATURE
            }, new SurfacePlacementConfig(-5, 1))
            .withFeature(NIGHTSTONE_BOULDER_FEATURE, new SurfacePlacementConfig(-3, 1))
            .withRidgeWeight(0.0F)
            .withGrassColor(0xBAA3C6)
            .build();

    public static final MidnightBiomeConfig BLACK_PLATEAU_CONFIG = MidnightBiomeConfig.builder(ROCKY_CONFIG)
            .build();

    public static final MidnightBiomeConfig PHANTASMAL_VALLEY_CONFIG = MidnightBiomeConfig.builder()
            .withRidgeWeight(0.0F)
            .build();
}
