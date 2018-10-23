package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.util.INumberGenerator;

import java.util.ArrayList;
import java.util.List;

public enum MidnightBiomeGroup {
    BASE,
    SMALL(new VoidBiomeSpawnEntry(100));

    private final List<IMidnightBiomeSpawnEntry> spawnEntries = new ArrayList<>();
    private int totalWeight;

    MidnightBiomeGroup(IMidnightBiomeSpawnEntry... entries) {
        this.add(entries);
    }

    public void add(IMidnightBiomeSpawnEntry... entries) {
        for (IMidnightBiomeSpawnEntry entry : entries) {
            this.spawnEntries.add(entry);
            this.totalWeight += entry.getWeight();
        }
    }

    public IMidnightBiomeSpawnEntry selectEntry(INumberGenerator numberGenerator) {
        int weight = numberGenerator.nextInt(this.totalWeight);
        for (IMidnightBiomeSpawnEntry entry : this.spawnEntries) {
            weight -= entry.getWeight();
            if (weight < 0) {
                return entry;
            }
        }
        throw new IllegalStateException("Cannot select biome entry, no entries registered!");
    }
}
