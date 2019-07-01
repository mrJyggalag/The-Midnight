package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightCarvers;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class FungalCavernBiome extends CavernousBiome {
    public FungalCavernBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.CAVERN, MidnightSurfaceBuilders.MYCELIUM_CONFIG)
                .cavernDensity(-15.0F)
                .pillarWeight(0.0F)
                .floorHeight(0.1F)
                .ceilingHeight(0.8F)
                .heightScale(0.6F)
        );

        this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(
                MidnightCarvers.CAVE, new ProbabilityConfig(1.0F / 7.0F)
        ));

        MidnightBiomeConfigurator.addDenseSmallFungis(this);
        MidnightBiomeConfigurator.addTallFungis(this);

        MidnightBiomeConfigurator.addBulbFungi(this);

        MidnightBiomeConfigurator.addUndergroundSpawns(this);
    }
}
