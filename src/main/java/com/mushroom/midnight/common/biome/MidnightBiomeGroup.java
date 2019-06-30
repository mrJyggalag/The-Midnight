package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.util.INumberGenerator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum MidnightBiomeGroup {
    SURFACE,
    SURFACE_POCKET,
    UNDERGROUND,
    UNDERGROUND_POCKET;

    private final List<BiomeSpawnEntry> spawnEntries = new ArrayList<>();
    private final Int2ObjectMap<Pool> pools = new Int2ObjectOpenHashMap<>();

    private Pool globalPool;

    public void add(BiomeSpawnEntry... entries) {
        Collections.addAll(this.spawnEntries, entries);
        this.pools.clear();
        this.globalPool = null;
    }

    @Nonnull
    public Pool getGlobalPool() {
        if (this.globalPool == null) {
            this.globalPool = new ListPool(this.spawnEntries);
        }
        return this.globalPool;
    }

    @Nonnull
    public Pool getPoolForBiome(int biomeId) {
        Pool pool = this.pools.get(biomeId);
        if (pool == null) {
            pool = this.createPool(biomeId);
            this.pools.put(biomeId, pool);
        }
        return pool;
    }

    private Pool createPool(int biomeId) {
        List<BiomeSpawnEntry> applicableEntries = this.spawnEntries.stream()
                .filter(e -> e.canReplace(biomeId))
                .collect(Collectors.toList());
        if (applicableEntries.isEmpty()) {
            return EmptyPool.INSTANCE;
        }
        return new ListPool(applicableEntries);
    }

    public interface Pool {
        @Nullable
        BiomeSpawnEntry selectEntry(INumberGenerator numberGenerator);
    }

    private static class ListPool implements Pool{
        private final List<BiomeSpawnEntry> entries;
        private final int totalWeight;

        private ListPool (List<BiomeSpawnEntry> entries) {
            this.entries = entries;
            this.totalWeight = entries.stream().mapToInt(BiomeSpawnEntry::getWeight).sum();
        }

        @Override
        @Nullable
        public BiomeSpawnEntry selectEntry(INumberGenerator numberGenerator) {
            int weight = numberGenerator.nextInt(this.totalWeight);
            for (BiomeSpawnEntry entry : this.entries) {
                weight -= entry.getWeight();
                if (weight < 0) {
                    return entry;
                }
            }
            return null;
        }
    }

    private static class EmptyPool implements Pool {
        public static final Pool INSTANCE = new EmptyPool();

        private EmptyPool() {
        }

        @Nullable
        @Override
        public BiomeSpawnEntry selectEntry(INumberGenerator numberGenerator) {
            return null;
        }
    }
}
