package com.mushroom.midnight.common.biome.config;

import com.google.common.collect.ImmutableList;
import com.mushroom.midnight.Midnight;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;

import java.util.Collection;

public class SpawnerConfig {
    public static final SpawnerConfig EMPTY = new SpawnerConfig(ImmutableList.of(), ImmutableList.of(), ImmutableList.of(), ImmutableList.of());

    private final ImmutableList<Biome.SpawnListEntry> monsterSpawns;
    private final ImmutableList<Biome.SpawnListEntry> creatureSpawns;
    private final ImmutableList<Biome.SpawnListEntry> ambientCreatureSpawns;
    private final ImmutableList<Biome.SpawnListEntry> waterCreatureSpawns;

    private SpawnerConfig(
            ImmutableList<Biome.SpawnListEntry> monsterSpawns,
            ImmutableList<Biome.SpawnListEntry> creatureSpawns,
            ImmutableList<Biome.SpawnListEntry> ambientCreatureSpawns,
            ImmutableList<Biome.SpawnListEntry> waterCreatureSpawns
    ) {
        this.monsterSpawns = monsterSpawns;
        this.creatureSpawns = creatureSpawns;
        this.ambientCreatureSpawns = ambientCreatureSpawns;
        this.waterCreatureSpawns = waterCreatureSpawns;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void apply(Biome biome) {
        for (EnumCreatureType creatureType : EnumCreatureType.values()) {
            biome.getSpawnableList(creatureType).clear();
        }

        biome.getSpawnableList(Midnight.MIDNIGHT_MOB).addAll(this.monsterSpawns);
        biome.getSpawnableList(Midnight.MIDNIGHT_AMBIENT).addAll(this.ambientCreatureSpawns);

        biome.getSpawnableList(EnumCreatureType.CREATURE).addAll(this.creatureSpawns);
        biome.getSpawnableList(EnumCreatureType.WATER_CREATURE).addAll(this.waterCreatureSpawns);
    }

    public Collection<Biome.SpawnListEntry> getMonsterSpawns() {
        return this.monsterSpawns;
    }

    public Collection<Biome.SpawnListEntry> getCreatureSpawns() {
        return this.creatureSpawns;
    }

    public Collection<Biome.SpawnListEntry> getAmbientCreatureSpawns() {
        return this.ambientCreatureSpawns;
    }

    public Collection<Biome.SpawnListEntry> getWaterCreatureSpawns() {
        return this.waterCreatureSpawns;
    }

    public static class Builder {
        private final ImmutableList.Builder<Biome.SpawnListEntry> monsterSpawns = new ImmutableList.Builder<>();
        private final ImmutableList.Builder<Biome.SpawnListEntry> creatureSpawns = new ImmutableList.Builder<>();
        private final ImmutableList.Builder<Biome.SpawnListEntry> ambientCreatureSpawns = new ImmutableList.Builder<>();
        private final ImmutableList.Builder<Biome.SpawnListEntry> waterCreatureSpawns = new ImmutableList.Builder<>();

        Builder() {
        }

        public Builder extendsFrom(SpawnerConfig config) {
            this.monsterSpawns.addAll(config.getMonsterSpawns());
            this.creatureSpawns.addAll(config.getCreatureSpawns());
            this.ambientCreatureSpawns.addAll(config.getAmbientCreatureSpawns());
            this.waterCreatureSpawns.addAll(config.getWaterCreatureSpawns());
            return this;
        }

        public Builder withMonster(Biome.SpawnListEntry entry) {
            this.monsterSpawns.add(entry);
            return this;
        }

        public Builder withCreature(Biome.SpawnListEntry entry) {
            this.creatureSpawns.add(entry);
            return this;
        }

        public Builder withAmbientCreature(Biome.SpawnListEntry entry) {
            this.ambientCreatureSpawns.add(entry);
            return this;
        }

        public Builder withWaterCreature(Biome.SpawnListEntry entry) {
            this.waterCreatureSpawns.add(entry);
            return this;
        }

        public SpawnerConfig build() {
            return new SpawnerConfig(
                    this.monsterSpawns.build(),
                    this.creatureSpawns.build(),
                    this.ambientCreatureSpawns.build(),
                    this.waterCreatureSpawns.build()
            );
        }
    }
}
