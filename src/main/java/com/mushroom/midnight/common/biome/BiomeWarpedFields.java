package com.mushroom.midnight.common.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BiomeWarpedFields extends BiomeBase {
    public BiomeWarpedFields(BiomeProperties properties) {
        super(properties);
    }

    @Override
    public float getDensityScale() {
        return 0.5F;
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);

        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
            this.generateCoverPlant(world, rand, pos, 1, CRYSTAL_FLOWER_GENERATOR);
        }

        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.SHROOM)) {
            this.generateCoverPlant(world, rand, pos, 2, DEWSHROOM_GENERATOR);
            this.generateCoverPlant(world, rand, pos, 1, DOUBLE_DEWSHROOM_GENERATOR);
            this.generateCoverPlant(world, rand, pos, 2, NIGHTSHROOM_GENERATOR);
            this.generateCoverPlant(world, rand, pos, 1, DOUBLE_NIGHTSHROOM_GENERATOR);
        }
    }
}
