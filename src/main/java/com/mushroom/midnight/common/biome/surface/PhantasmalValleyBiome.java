package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class PhantasmalValleyBiome extends SurfaceBiome {
    public PhantasmalValleyBiome() {
        super(new Builder()
                .surfaceBuilder(SurfaceBuilder.DEFAULT, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.RIVER)
                .depth(-1.2F)
                .scale(0.05F)
                .ridgeWeight(0.0F)
                .wet()
        );

        // .withFeature(ROCKSHROOM_HEAP_FEATURE, new SurfacePlacementConfig(-99, 1))

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);

        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(MidnightEntities.HUNTER, 5, 1, 2));
    }
}
