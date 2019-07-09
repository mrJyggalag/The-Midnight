package com.mushroom.midnight.common.biome.cavern;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mushroom.midnight.common.biome.ConfigurableBiome;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CavernousBiome extends ForgeRegistryEntry<CavernousBiome> implements ConfigurableBiome {
    protected final ConfiguredSurfaceBuilder<?> surfaceBuilder;
    protected final float cavernDensity;
    protected final float floorHeight;
    protected final float ceilingHeight;
    protected final float heightScale;
    protected final float pillarWeight;

    protected final Multimap<GenerationStage.Carving, ConfiguredCarver<?>> carvers = HashMultimap.create();
    protected final Multimap<GenerationStage.Decoration, ConfiguredFeature<?>> features = HashMultimap.create();
    protected final List<ConfiguredFeature<?>> flowers = new ArrayList<>();
    protected final Map<Structure<?>, IFeatureConfig> structures = new HashMap<>();
    protected final Multimap<EntityClassification, Biome.SpawnListEntry> spawns = HashMultimap.create();

    public CavernousBiome(Properties properties) {
        Preconditions.checkNotNull(properties.surfaceBuilder, "must have surfacebuilder");

        this.surfaceBuilder = properties.surfaceBuilder;
        this.cavernDensity = properties.cavernDensity;
        this.floorHeight = properties.floorHeight;
        this.ceilingHeight = properties.ceilingHeight;
        this.heightScale = properties.heightScale;
        this.pillarWeight = properties.pillarWeight;
    }

    @Override
    public void add(GenerationStage.Decoration stage, ConfiguredFeature<?> feature) {
        if (feature.feature == Feature.DECORATED_FLOWER) {
            this.flowers.add(feature);
        }
        this.features.put(stage, feature);
    }

    @Override
    public <C extends ICarverConfig> void add(GenerationStage.Carving stage, ConfiguredCarver<C> carver) {
        this.carvers.put(stage, carver);
    }

    @Override
    public <C extends IFeatureConfig> void add(Structure<C> structure, C config) {
        this.structures.put(structure, config);
    }

    @Override
    public void add(EntityClassification classification, Biome.SpawnListEntry entry) {
        this.spawns.put(classification, entry);
    }

    @Override
    public void placeFeatures(GenerationStage.Decoration stage, MidnightChunkGenerator generator, WorldGenRegion world, long seed, SharedSeedRandom random, BlockPos origin) {
        int index = 0;

        for (ConfiguredFeature<?> feature : this.features.get(stage)) {
            random.setFeatureSeed(seed, index, stage.ordinal());
            feature.place(world, generator, random, origin);

            index++;
        }
    }

    @Override
    public Collection<ConfiguredCarver<?>> getCarversFor(GenerationStage.Carving stage) {
        return this.carvers.get(stage);
    }

    @Override
    public void generateSurface(SharedSeedRandom random, IChunk chunk, int x, int z, int startY, double depth, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed) {
        this.surfaceBuilder.setSeed(seed);
        this.surfaceBuilder.buildSurface(random, chunk, Biomes.DEFAULT, x, z, startY, depth, defaultBlock, defaultFluid, seaLevel, seed);
    }

    public float getCavernDensity() {
        return this.cavernDensity;
    }

    public float getFloorHeight() {
        return this.floorHeight;
    }

    public float getCeilingHeight() {
        return this.ceilingHeight;
    }

    public float getHeightScale() {
        return this.heightScale;
    }

    public float getPillarWeight() {
        return this.pillarWeight;
    }

    public static class Properties {
        private ConfiguredSurfaceBuilder<?> surfaceBuilder;
        private float cavernDensity = -5.0F;
        private float floorHeight = 0.0F;
        private float ceilingHeight = 1.0F;
        private float heightScale = 0.1F;
        private float pillarWeight = 1.0F;

        protected Properties() {
        }

        public <SC extends ISurfaceBuilderConfig> Properties surfaceBuilder(SurfaceBuilder<SC> surface, SC config) {
            this.surfaceBuilder = new ConfiguredSurfaceBuilder<>(surface, config);
            return this;
        }

        public Properties cavernDensity(float density) {
            this.cavernDensity = density;
            return this;
        }

        public Properties floorHeight(float floorHeight) {
            this.floorHeight = floorHeight;
            return this;
        }

        public Properties ceilingHeight(float ceilingHeight) {
            this.ceilingHeight = ceilingHeight;
            return this;
        }

        public Properties heightScale(float heightScale) {
            this.heightScale = heightScale;
            return this;
        }

        public Properties pillarWeight(float pillarWeight) {
            this.pillarWeight = pillarWeight;
            return this;
        }
    }
}
