package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.biome.BiomeLayer;
import com.mushroom.midnight.common.biome.BiomeProcedure;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.feature.structure.Structure;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MidnightBiomeProvider extends BiomeProvider {
    private final BiomeLayer<Biome> layer;
    private final BiomeProcedure<LazyArea> procedure;

    public MidnightBiomeProvider(BiomeLayer<Biome> layer, BiomeProcedure<LazyArea> procedure) {
        this.layer = layer;
        this.procedure = procedure;
    }

    @Override
    public Biome getBiome(int x, int y) {
        return this.layer.sample(this.procedure.getBlock(), x, y);
    }

    @Override
    public Biome func_222366_b(int x, int y) {
        return this.layer.sample(this.procedure.getNoise(), x, y);
    }

    @Override
    public Biome[] getBiomes(int x, int y, int width, int height, boolean p_201537_5_) {
        return this.layer.sample(this.procedure.getBlock(), x, y, width, height);
    }

    @Override
    public Set<Biome> getBiomesInSquare(int x, int y, int radius) {
        int minX = x - radius >> 2;
        int minY = y - radius >> 2;
        int maxX = x + radius >> 2;
        int maxY = y + radius >> 2;
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        Set<Biome> biomes = new HashSet<>();
        Collections.addAll(biomes, this.layer.sample(this.procedure.getNoise(), minX, minY, width, height));
        return biomes;
    }

    @Override
    @Nullable
    public BlockPos findBiomePosition(int x, int y, int radius, List<Biome> find, Random random) {
        int minX = x - radius >> 2;
        int minY = y - radius >> 2;
        int maxX = x + radius >> 2;
        int maxY = y + radius >> 2;
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        Biome[] biomes = this.layer.sample(this.procedure.getNoise(), minX, minY, width, height);
        BlockPos result = null;
        int count = 0;

        for (int i = 0; i < width * height; i++) {
            int findX = minX + i % width << 2;
            int findZ = minY + i / width << 2;

            if (find.contains(biomes[i])) {
                if (result == null || random.nextInt(count + 1) == 0) {
                    result = new BlockPos(findX, 0, findZ);
                }

                count++;
            }
        }

        return result;
    }

    @Override
    public boolean hasStructure(Structure<?> structure) {
        return this.hasStructureCache.computeIfAbsent(structure, s -> {
            return MidnightSurfaceBiomes.allBiomes()
                    .anyMatch(biome -> biome.hasStructure(s));
        });
    }

    @Override
    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            MidnightSurfaceBiomes.allBiomes().forEach(biome -> {
                this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
            });
        }
        return this.topBlocksCache;
    }
}
