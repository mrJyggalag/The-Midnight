package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.config.MidnightBiomeConfig;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public interface ConfiguredMidnightBiome<C extends MidnightBiomeConfig> {
    C getConfig();

    @Nullable
    static MidnightBiomeConfig getConfig(Biome biome) {
        if (biome instanceof ConfiguredMidnightBiome) {
            return ((ConfiguredMidnightBiome) biome).getConfig();
        }
        return null;
    }
}
