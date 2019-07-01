package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.BiomeSpawnEntry;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class SeedGroupLayer implements IAreaTransformer0 {
    private final MidnightBiomeGroup group;

    public SeedGroupLayer(MidnightBiomeGroup group) {
        this.group = group;
    }

    @Override
    public int apply(INoiseRandom random, int x, int y) {
        MidnightBiomeGroup.Pool pool = this.group.getGlobalPool();

        BiomeSpawnEntry entry = pool.selectEntry(random::random);
        if (entry != null) {
            return entry.getBiomeId();
        } else {
            return 0;
        }
    }
}
