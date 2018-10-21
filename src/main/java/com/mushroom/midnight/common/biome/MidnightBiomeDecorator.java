package com.mushroom.midnight.common.biome;

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

public class MidnightBiomeDecorator extends BiomeDecorator {
    private final MidnightBiomeConfig config;

    public MidnightBiomeDecorator(MidnightBiomeConfig config) {
        this.config = config;
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

        Collection<MidnightBiomeConfig.FeatureEntry> features = this.config.getFeatures();
        for (MidnightBiomeConfig.FeatureEntry entry : features) {
            this.generateFeatureEntry(world, random, chunkPos, entry);
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, random, chunkPos));
    }

    private void generateFeatureEntry(World world, Random random, ChunkPos chunkPos, MidnightBiomeConfig.FeatureEntry entry) {
        IMidnightFeature[] features = entry.getFeatures();
        IMidnightFeature feature = features[random.nextInt(features.length)];

        IPlacementConfig config = entry.getPlacementConfig();
        if (TerrainGen.decorate(world, random, chunkPos, this.chunkPos, feature.getEventType())) {
            config.apply(world, random, this.chunkPos, pos -> feature.placeFeature(world, random, pos));
        }
    }
}
