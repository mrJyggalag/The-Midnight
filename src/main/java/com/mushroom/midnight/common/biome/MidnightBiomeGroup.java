package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.config.BiomeSpawnEntry;
import com.mushroom.midnight.common.util.INumberGenerator;

import java.util.ArrayList;
import java.util.List;

public enum MidnightBiomeGroup {
    SURFACE,
    UNDERGROUND,
    SMALL_SURFACE(new BiomeSpawnEntry.Void(100));

    private final List<BiomeSpawnEntry> spawnEntries = new ArrayList<>();
    private int totalWeight;

    MidnightBiomeGroup(BiomeSpawnEntry... entries) {
        this.add(entries);
    }

    public void add(BiomeSpawnEntry... entries) {
        for (BiomeSpawnEntry entry : entries) {
            this.spawnEntries.add(entry);
            this.totalWeight += entry.getWeight();
        }
    }

    public BiomeSpawnEntry selectEntry(INumberGenerator numberGenerator) {
        int weight = numberGenerator.nextInt(this.totalWeight);
        for (BiomeSpawnEntry entry : this.spawnEntries) {
            weight -= entry.getWeight();
            if (weight < 0) {
                return entry;
            }
        }
        throw new IllegalStateException("Cannot select biome entry, no entries registered!");
    }
}
