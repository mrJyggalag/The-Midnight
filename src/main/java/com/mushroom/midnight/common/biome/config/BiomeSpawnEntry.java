package com.mushroom.midnight.common.biome.config;

import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.registry.ModCavernousBiomes;
import com.mushroom.midnight.common.util.SessionLocal;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.biome.Biome;

import java.util.function.IntPredicate;

public interface BiomeSpawnEntry {
    int getBiomeId();

    boolean canReplace(int biome);

    int getWeight();

    class Basic implements BiomeSpawnEntry {
        private final SessionLocal<Integer> biomeId;
        private IntPredicate canReplace;

        private final int weight;

        public Basic(Biome biome, int weight) {
            this.biomeId = SessionLocal.register(() -> Biome.getIdForBiome(biome));
            this.weight = weight;
        }

        public Basic(CavernousBiome biome, int weight) {
            this.biomeId = SessionLocal.register(() -> ModCavernousBiomes.getRegistry().getID(biome));
            this.weight = weight;
        }

        public Basic canReplace(IntPredicate predicate) {
            this.canReplace = predicate;
            return this;
        }

        public Basic canReplace(Biome... biomes) {
            SessionLocal<IntSet> biomeIds = SessionLocal.register(() -> {
                IntSet ids = new IntOpenHashSet();
                for (Biome biome : biomes) {
                    ids.add(Biome.getIdForBiome(biome));
                }
                return ids;
            });

            this.canReplace = id -> biomeIds.get().contains(id);

            return this;
        }

        @Override
        public int getBiomeId() {
            return this.biomeId.get();
        }

        @Override
        public boolean canReplace(int biome) {
            if (this.canReplace == null) {
                return true;
            }
            return this.canReplace.test(biome);
        }

        @Override
        public int getWeight() {
            return this.weight;
        }
    }
}
