package com.mushroom.midnight.common.biome.config;

import com.mushroom.midnight.common.util.INumberGenerator;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Predicate;

public interface BiomeSpawnEntry {
    @Nullable
    Biome selectBiome(INumberGenerator numberGenerator);

    boolean canReplace(Biome biome);

    int getWeight();

    class Basic implements BiomeSpawnEntry {
        private final Function<INumberGenerator, Biome> supplier;
        private Predicate<Biome> canReplace;

        private final int weight;

        public Basic(Function<INumberGenerator, Biome> supplier, int weight) {
            this.supplier = supplier;
            this.weight = weight;
        }

        public Basic(Biome biome, int weight) {
            this(rng -> biome, weight);
        }

        public Basic canReplace(Predicate<Biome> predicate) {
            this.canReplace = predicate;
            return this;
        }

        @Override
        public Biome selectBiome(INumberGenerator numberGenerator) {
            return this.supplier.apply(numberGenerator);
        }

        @Override
        public boolean canReplace(Biome biome) {
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

    class Void implements BiomeSpawnEntry {
        private final int weight;

        public Void(int weight) {
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
}
