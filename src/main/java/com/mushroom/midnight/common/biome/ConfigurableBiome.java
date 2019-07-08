package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.Collection;

public interface ConfigurableBiome {
    void add(GenerationStage.Decoration stage, ConfiguredFeature<?> feature);

    <C extends ICarverConfig> void add(GenerationStage.Carving stage, ConfiguredCarver<C> carver);

    <C extends IFeatureConfig> void add(Structure<C> structure, C config);

    void add(EntityClassification classification, Biome.SpawnListEntry entry);

    void placeFeatures(GenerationStage.Decoration stage, MidnightChunkGenerator generator, WorldGenRegion world, long seed, SharedSeedRandom random, BlockPos origin);

    Collection<ConfiguredCarver<?>> getCarversFor(GenerationStage.Carving stage);
}
