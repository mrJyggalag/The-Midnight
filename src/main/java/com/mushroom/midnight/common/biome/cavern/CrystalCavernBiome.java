package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;

public class CrystalCavernBiome extends CavernousBiome {
    public CrystalCavernBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.CAVERN, MidnightSurfaceBuilders.NIGHTSTONE_CONFIG)
                .caveRadiusScale(0.0F)
                .heightScale(0.4F)
                .cavernDensity(5.0F)
        );

        // .withFeature(ROUXE_FEATURE, new SurfacePlacementConfig(5))

        MidnightBiomeConfigurator.addUndergroundSpawns(this);

        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(MidnightEntities.NOVA, 100, 1, 2));
    }
}
