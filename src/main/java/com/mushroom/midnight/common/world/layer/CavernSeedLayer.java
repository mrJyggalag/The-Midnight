package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.BiomeSpawnEntry;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.registry.MidnightCavernousBiomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class CavernSeedLayer implements IAreaTransformer0 {
    private final MidnightBiomeGroup group;
    private final int closedCavernId;

    public CavernSeedLayer(MidnightBiomeGroup group) {
        this.group = group;
        this.closedCavernId = MidnightCavernousBiomes.getId(MidnightCavernousBiomes.CLOSED_CAVERN);
    }

    @Override
    public int apply(INoiseRandom random, int x, int y) {
        if (random.random(2) == 0) {
            BiomeSpawnEntry entry = this.group.getGlobalPool().selectEntry(random::random);
            if (entry != null) {
                return entry.getBiomeId();
            }
        }
        return this.closedCavernId;
    }
}
