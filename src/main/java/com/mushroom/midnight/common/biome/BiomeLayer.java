package com.mushroom.midnight.common.biome;

import net.minecraft.world.gen.area.LazyArea;

import java.lang.reflect.Array;
import java.util.function.IntFunction;

public final class BiomeLayer<T> {
    private final Class<T> type;
    private final LazyArea sampler;
    private final IntFunction<T> function;

    BiomeLayer(Class<T> type, LazyArea sampler, IntFunction<T> function) {
        this.type = type;
        this.sampler = sampler;
        this.function = function;
    }

    public T sample(int x, int y) {
        int value = this.sampler.getValue(x, y);
        return this.function.apply(value);
    }

    @SuppressWarnings("unchecked")
    public T[] sample(int x, int y, int width, int height) {
        T[] result = (T[]) Array.newInstance(this.type, width * height);
        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                int value = this.sampler.getValue(localX + x, localY + y);
                result[localX + localY * width] = this.function.apply(value);
            }
        }

        return result;
    }
}
