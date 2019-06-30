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

        MidnightBiomeConfigurator.addSparseShadowrootTrees(this);
        MidnightBiomeConfigurator.addGrasses(this);
        MidnightBiomeConfigurator.addFingeredGrass(this);

        MidnightBiomeConfigurator.addNightstoneSpikesAndBoulders(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);

        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(MidnightEntities.HUNTER, 5, 1, 2));
    }
}
