package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.util.INumberGenerator;
import net.minecraft.world.biome.Biome;

import java.util.function.Function;
import java.util.function.Predicate;

public class BasicBiomeSpawnEntry implements IMidnightBiomeSpawnEntry {
    private final Function<INumberGenerator, Biome> supplier;
    private Predicate<Biome> canReplace;

    private final int weight;

    public BasicBiomeSpawnEntry(Function<INumberGenerator, Biome> supplier, int weight) {
        this.supplier = supplier;
        this.weight = weight;
    }

    public BasicBiomeSpawnEntry(Biome biome, int weight) {
        this(rng -> biome, weight);
    }

    public BasicBiomeSpawnEntry canReplace(Predicate<Biome> predicate) {
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
