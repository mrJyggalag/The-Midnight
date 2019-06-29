package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class ObscuredPlateauBiome extends SurfaceBiome {
    public ObscuredPlateauBiome() {
        super(new Builder()
                .surfaceBuilder(SurfaceBuilder.DEFAULT, MidnightSurfaceBuilders.NIGHTSTONE_CONFIG)
                .category(Category.EXTREME_HILLS)
                .depth(5.0F)
                .scale(0.01F)
        );

        MidnightBiomeConfigurator.addSmallFungis(this);
        MidnightBiomeConfigurator.addLumen(this);
        MidnightBiomeConfigurator.addBoulders(this);
        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addRockySpawns(this);
    }
}
