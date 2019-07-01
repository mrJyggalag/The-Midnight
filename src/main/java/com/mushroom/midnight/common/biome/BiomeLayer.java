package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.registry.MidnightCavernousBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.area.IArea;

import java.util.function.IntFunction;

public final class BiomeLayer<T> {
    public static final BiomeLayer<Biome> SURFACE = BiomeLayer.create(Registry.BIOME::getByValue);
    public static final BiomeLayer<CavernousBiome> UNDERGROUND = BiomeLayer.create(MidnightCavernousBiomes::byId);

    private final IntFunction<T> function;

    private BiomeLayer(IntFunction<T> function) {
        this.function = function;
    }

    public static <T> BiomeLayer<T> create(IntFunction<T> function) {
        return new BiomeLayer<>(function);
    }

    public <A extends IArea> T sample(A sampler, int x, int y) {
        int value = sampler.getValue(x, y);
        return this.function.apply(value);
    }

    @SuppressWarnings("unchecked")
    public <A extends IArea> T[] sample(A sampler, int x, int y, int width, int height) {
        Object[] result = new Object[width * height];
        for (int localY = 0; localY < height; localY++) {
            for (int localX = 0; localX < width; localX++) {
                int value = sampler.getValue(localX + x, localY + y);
                result[localX + localY * width] = this.function.apply(value);
            }
        }

        return (T[]) result;
    }
}
