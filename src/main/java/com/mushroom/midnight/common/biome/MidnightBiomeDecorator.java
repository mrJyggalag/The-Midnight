package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.config.BiomeFeatureEntry;
import com.mushroom.midnight.common.biome.config.FeatureConfig;
import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import com.mushroom.midnight.common.world.feature.IMidnightFeature;
import com.mushroom.midnight.common.world.feature.config.IPlacementConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Collection;
import java.util.Random;
import java.util.function.Function;

public class MidnightBiomeDecorator extends BiomeDecorator {
    private final FeatureConfig config;
    private final Function<World, SurfacePlacementLevel> placementLevel;

    public MidnightBiomeDecorator(FeatureConfig config, Function<World, SurfacePlacementLevel> placementLevel) {
        this.config = config;
        this.placementLevel = placementLevel;
    }

    @Override
    public void decorate(World world, Random random, Biome biome, BlockPos pos) {
        if (this.decorating) {
            throw new RuntimeException("Already decorating");
        }

        try {
            this.decorating = true;
            this.chunkPos = pos;
            this.genDecorations(biome, world, random);
        } finally {
            this.decorating = false;
        }
    }

    @Override
    protected void genDecorations(Biome biome, World world, Random random) {
        ChunkPos chunkPos = new ChunkPos(this.chunkPos);

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, random, chunkPos));

        SurfacePlacementLevel placementLevel = this.placementLevel.apply(world);

        Collection<BiomeFeatureEntry> features = this.config.getFeatures();
        for (BiomeFeatureEntry entry : features) {
            this.generateFeatureEntry(world, placementLevel, random, chunkPos, entry);
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, random, chunkPos));
    }

    private void generateFeatureEntry(World world, SurfacePlacementLevel placementLevel, Random random, ChunkPos chunkPos, BiomeFeatureEntry entry) {
        IMidnightFeature[] features = entry.getFeatures();
        IMidnightFeature feature = features[random.nextInt(features.length)];

        IPlacementConfig config = entry.getPlacementConfig();

        if (TerrainGen.decorate(world, random, chunkPos, this.chunkPos, feature.getEventType())) {
            config.apply(world, placementLevel, random, this.chunkPos, pos -> feature.placeFeature(world, random, pos));
        }
    }
}
