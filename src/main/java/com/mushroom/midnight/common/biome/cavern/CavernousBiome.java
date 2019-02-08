package com.mushroom.midnight.common.biome.cavern;

import net.minecraftforge.registries.IForgeRegistryEntry;

public class CavernousBiome extends IForgeRegistryEntry.Impl<CavernousBiome> {
    private final CavernousBiomeConfig config;

    public CavernousBiome(CavernousBiomeConfig config) {
        this.config = config;
    }

    public CavernousBiomeConfig getConfig() {
        return this.config;
    }
}
