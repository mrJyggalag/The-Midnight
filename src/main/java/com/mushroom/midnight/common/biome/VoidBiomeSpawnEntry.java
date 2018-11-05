package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.util.INumberGenerator;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public class VoidBiomeSpawnEntry implements IMidnightBiomeSpawnEntry {
    private final int weight;

    public VoidBiomeSpawnEntry(int weight) {
        this.weight = weight;
    }

    @Nullable
    @Override
    public Biome selectBiome(INumberGenerator numberGenerator) {
        return null;
    }

    @Override
    public boolean canReplace(Biome biome) {
        return false;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }
}
