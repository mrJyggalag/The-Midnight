package com.mushroom.midnight.common.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public interface ConfigurableBiome {
    void add(GenerationStage.Decoration stage, ConfiguredFeature<?> feature);

    <C extends ICarverConfig> void add(GenerationStage.Carving stage, ConfiguredCarver<C> carver);

    <C extends IFeatureConfig> void add(Structure<C> structure, C config);

    void add(EntityClassification classification, Biome.SpawnListEntry entry);
}
