package com.mushroom.midnight.common.biome.config;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.biome.Biome;

import java.util.function.IntPredicate;

public interface BiomeSpawnEntry {
    int getBiomeId();

    boolean canReplace(int biome);

    int getWeight();

    class Basic implements BiomeSpawnEntry {
        private final int biomeId;
        private IntPredicate canReplace;

        private final int weight;

        public Basic(Biome biome, int weight) {
            this.biomeId = Biome.getIdForBiome(biome);
            this.weight = weight;
        }

        public Basic canReplace(IntPredicate predicate) {
            this.canReplace = predicate;
            return this;
        }

        public Basic canReplace(Biome... biomes) {
            IntSet biomeIds = new IntOpenHashSet();
            for (Biome biome : biomes) {
                biomeIds.add(Biome.getIdForBiome(biome));
            }

            this.canReplace = biomeIds::contains;

            return this;
        }

        @Override
        public int getBiomeId() {
            return this.biomeId;
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
