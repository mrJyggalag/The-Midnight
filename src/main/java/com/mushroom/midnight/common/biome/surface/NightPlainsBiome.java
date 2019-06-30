package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;

public class NightPlainsBiome extends SurfaceBiome {
    public NightPlainsBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.PLAINS)
                .grassColor(0xBAA3C6)
                .depth(0.12F)
                .scale(0.26F)
                .ridgeWeight(0.0F)
        );

//        .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(2, 32))
//        .withFeature(FINGERED_GRASS_FEATURE, new ScatterPlacementConfig(8, 16) {
//            @Override
//            public void apply(World world, SurfacePlacementLevel placementLevel, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator) {
//                if (world.rand.nextFloat() < 0.2f) {
//                    super.apply(world, placementLevel, random, chunkOrigin, generator);
//                }
//            }
//        })
//        .withFlower(new FlowerEntry(MidnightBlocks.FINGERED_GRASS.getDefaultState(), 40))
//        .withFeature(new IMidnightFeature[] {
//                SHADOWROOT_TREE_FEATURE,
//                DEAD_TREE_FEATURE
//        }, new SurfacePlacementConfig(-5, 1))
//        .withFeature(new IMidnightFeature[] {
//                NIGHTSTONE_BOULDER_FEATURE,
//                NIGHTSTONE_SPIKE_FEATURE
//        }, new SurfacePlacementConfig(-3, 1))

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);

        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(MidnightEntities.HUNTER, 5, 1, 2));
    }
}
