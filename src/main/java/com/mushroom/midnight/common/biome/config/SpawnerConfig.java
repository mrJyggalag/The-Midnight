package com.mushroom.midnight.common.biome.config;

import com.google.common.collect.ImmutableMap;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.util.WeightedPool;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class SpawnerConfig {
    public static final SpawnerConfig EMPTY = new SpawnerConfig(ImmutableMap.of(), 0.0F);

    private final ImmutableMap<EntityClassification, WeightedPool<Biome.SpawnListEntry>> spawnPools;
    private final float spawnChance;

    private SpawnerConfig(ImmutableMap<EntityClassification, WeightedPool<Biome.SpawnListEntry>> spawnPools, float spawnChance) {
        this.spawnPools = spawnPools;
        this.spawnChance = spawnChance;
    }

    public static Builder builder() {
        return new Builder();
    }

    public WeightedPool<Biome.SpawnListEntry> getPool(EntityClassification entityClassification) {
        return this.spawnPools.getOrDefault(entityClassification, WeightedPool.empty());
    }

    public float getSpawnChance() {
        return this.spawnChance;
    }

    public boolean isEmpty() {
        return this.spawnPools.isEmpty() || this.spawnChance <= 0.0F;
    }

    public static class Builder {
        private final Map<EntityClassification, WeightedPool<Biome.SpawnListEntry>> spawnPools = new HashMap<>();
        private float spawnChance = 0.1F;

        Builder() {
        }

        public Builder extendsFrom(SpawnerConfig config) {
            this.spawnPools.putAll(config.spawnPools);
            this.spawnChance = config.spawnChance;
            return this;
        }

        public Builder withCreature(Biome.SpawnListEntry entry) {
            return this.withEntity(EntityClassification.CREATURE, entry);
        }

        public Builder withMonster(Biome.SpawnListEntry entry) {
            return this.withEntity(Midnight.MIDNIGHT_MOB, entry);
        }

        public Builder withAmbientCreature(Biome.SpawnListEntry entry) {
            return this.withEntity(Midnight.MIDNIGHT_AMBIENT, entry);
        }

        public Builder withWaterCreature(Biome.SpawnListEntry entry) {
            return this.withEntity(EntityClassification.WATER_CREATURE, entry);
        }

        public Builder withEntity(EntityClassification creatureType, Biome.SpawnListEntry entry) {
            this.spawnPools.computeIfAbsent(creatureType, p -> new WeightedPool<>()).add(entry);
            return this;
        }

        public Builder withSpawnChance(float chance) {
            this.spawnChance = chance;
            return this;
        }

        public SpawnerConfig build() {
            return new SpawnerConfig(ImmutableMap.copyOf(this.spawnPools), this.spawnChance);
        }
    }
}
