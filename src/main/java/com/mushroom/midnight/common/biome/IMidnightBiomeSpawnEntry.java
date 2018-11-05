package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.util.INumberGenerator;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public interface IMidnightBiomeSpawnEntry {
    @Nullable
    Biome selectBiome(INumberGenerator numberGenerator);

    boolean canReplace(Biome biome);

    int getWeight();
}
