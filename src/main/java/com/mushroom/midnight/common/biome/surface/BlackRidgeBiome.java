package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BlackRidgeBiome extends SurfaceBiome {
    public BlackRidgeBiome() {
        super(new Builder()
                .surfaceBuilder(SurfaceBuilder.DEFAULT, MidnightSurfaceBuilders.NIGHTSTONE_CONFIG)
                .category(Category.EXTREME_HILLS)
                .depth(4.5F)
                .scale(0.1F)
        );

        MidnightBiomeConfigurator.addRockyFeatures(this);
        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addRockySpawns(this);
    }
}
