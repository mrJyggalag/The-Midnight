package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightCarvers;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class ClosedCavernBiome extends CavernousBiome {
    public ClosedCavernBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.CAVERN, MidnightSurfaceBuilders.NIGHTSTONE_CONFIG)
                .cavernDensity(5.0F)
        );

        this.add(GenerationStage.Carving.AIR, Biome.createCarver(
                MidnightCarvers.WIDE_CAVE, new ProbabilityConfig(1.0F / 7.0F)
        ));
    }
}
