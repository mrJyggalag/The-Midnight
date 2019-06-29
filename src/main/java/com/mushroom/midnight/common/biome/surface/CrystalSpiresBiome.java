package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class CrystalSpiresBiome extends SurfaceBiome {
    public CrystalSpiresBiome() {
        super(new Builder()
                .surfaceBuilder(SurfaceBuilder.DEFAULT, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.PLAINS)
                .grassColor(0xD184BC)
                .depth(0.6F)
                .scale(0.26F)
                .ridgeWeight(0.0F)
        );

//      .withFeature(ROCKSHROOM_HEAP_FEATURE, new SurfacePlacementConfig(-99, 1))
//      .withFeature(SHADOWROOT_TREE_FEATURE, new SurfacePlacementConfig(-3, 1))
//      .withFeature(BLOOMCRYSTAL_FEATURE, new SurfacePlacementConfig(3))
//      .withFeature(BLOOMCRYSTAL_SPIRE_FEATURE, new SurfacePlacementConfig(2, 3))
//      .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
//      .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
//      .withFeature(CRYSTAL_FLOWER_FEATURE, new ScatterPlacementConfig(5, 12))

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(MidnightEntities.NIGHTSTAG, 100, 1, 3));
        this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(MidnightEntities.CRYSTAL_BUG, 100, 7, 10));
    }
}
