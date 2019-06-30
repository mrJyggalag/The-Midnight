package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightFeatures;
import com.mushroom.midnight.common.world.feature.config.CrystalClusterConfig;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;

public class CrystalSpiresBiome extends SurfaceBiome {
    public CrystalSpiresBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.PLAINS)
                .grassColor(0xD184BC)
                .depth(0.6F)
                .scale(0.26F)
                .ridgeWeight(0.0F)
        );

        this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createDecoratedFeature(
                MidnightFeatures.CRYSTAL_CLUSTER, new CrystalClusterConfig(MidnightBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(), MidnightBlocks.BLOOMCRYSTAL.getDefaultState()),
                Placement.COUNT_HEIGHTMAP, new FrequencyConfig(3)
        ));

        this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createDecoratedFeature(
                MidnightFeatures.CRYSTAL_SPIRE, new CrystalClusterConfig(MidnightBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(), MidnightBlocks.BLOOMCRYSTAL.getDefaultState()),
                Placement.COUNT_HEIGHTMAP, new FrequencyConfig(2)
        ));

        MidnightBiomeConfigurator.addSparseShadowrootTrees(this);

        MidnightBiomeConfigurator.addLumen(this);
        MidnightBiomeConfigurator.addCrystalFlowers(this);
        MidnightBiomeConfigurator.addGlobalFeatures(this);

        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(MidnightEntities.NIGHTSTAG, 100, 1, 3));
        this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(MidnightEntities.CRYSTAL_BUG, 100, 7, 10));
    }
}
