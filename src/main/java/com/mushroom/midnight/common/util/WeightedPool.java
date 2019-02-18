package com.mushroom.midnight.common.util;

import net.minecraft.util.WeightedRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedPool<T extends WeightedRandom.Item> {
    private static final WeightedPool<WeightedRandom.Item> EMPTY = new WeightedPool<>();

    private final List<T> entries = new ArrayList<>();
    private int totalWeight;

    @SuppressWarnings("unchecked")
    public static <T extends WeightedRandom.Item> WeightedPool<T> empty() {
        return (WeightedPool<T>) EMPTY;
    }

    public void add(T entry) {
        this.entries.add(entry);
        this.totalWeight += entry.itemWeight;
    }

    public T pick(Random random) {
        if (this.entries.isEmpty()) {
            return null;
        }
        return WeightedRandom.getRandomItem(random, this.entries, this.totalWeight);
    }
}
