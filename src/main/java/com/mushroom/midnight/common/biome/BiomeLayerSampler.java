package com.mushroom.midnight.common.biome;

import net.minecraft.world.World;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

import java.util.Arrays;
import java.util.function.IntFunction;

public interface BiomeLayerSampler<T> {
    static <T> BiomeLayerSampler<T> fromGenLayer(World world, GenLayer noiseLayer, IntFunction<T> function) {
        WorldInfo worldInfo = world.getWorldInfo();
        long seed = worldInfo.getSeed();

        GenLayerVoronoiZoom layer = new GenLayerVoronoiZoom(10, noiseLayer);
        layer.initWorldGenSeed(seed);

        GenLayer[] layers = new GenLayer[] { noiseLayer, layer, noiseLayer };

        WorldTypeEvent.InitBiomeGens event = new WorldTypeEvent.InitBiomeGens(worldInfo.getTerrainType(), seed, layers);
        MinecraftForge.TERRAIN_GEN_BUS.post(event);

        return new Vanilla<>(event.getNewBiomeGens(), function);
    }

    T[] sample(T[] array, int x, int y, int width, int height);

    T[] sampleNoise(T[] array, int x, int y, int width, int height);

    T sample(int x, int y);

    class Constant<T> implements BiomeLayerSampler<T> {
        private final T value;

        public Constant(T value) {
            this.value = value;
        }

        @Override
        public T[] sample(T[] array, int x, int y, int width, int height) {
            Arrays.fill(array, this.value);
            return array;
        }

        @Override
        public T[] sampleNoise(T[] array, int x, int y, int width, int height) {
            Arrays.fill(array, this.value);
            return array;
        }

        @Override
        public T sample(int x, int y) {
            return this.value;
        }
    }

    class Vanilla<T> implements BiomeLayerSampler<T> {
        private final GenLayer layer;
        private final GenLayer noiseLayer;

        private final IntFunction<T> function;

        private Vanilla(GenLayer[] layers, IntFunction<T> function) {
            this.layer = layers[1];
            this.noiseLayer = layers[0];
            this.function = function;
        }

        @Override
        public T[] sample(T[] array, int x, int y, int width, int height) {
            return this.sampleLayer(array, x, y, width, height, this.layer);
        }

        @Override
        public T[] sampleNoise(T[] array, int x, int y, int width, int height) {
            return this.sampleLayer(array, x, y, width, height, this.noiseLayer);
        }

        @Override
        public T sample(int x, int y) {
            IntCache.resetIntCache();

            int[] ints = this.layer.getInts(x, y, 1, 1);
            return this.function.apply(ints[0]);
        }

        private T[] sampleLayer(T[] array, int x, int y, int width, int height, GenLayer layer) {
            if (array.length != width * height) {
                throw new IllegalArgumentException("Given input array of wrong dimensions!");
            }

            IntCache.resetIntCache();

            int[] ints = layer.getInts(x, y, width, height);
            for (int i = 0; i < array.length; i++) {
                array[i] = this.function.apply(ints[i]);
            }

            return array;
        }
    }
}
