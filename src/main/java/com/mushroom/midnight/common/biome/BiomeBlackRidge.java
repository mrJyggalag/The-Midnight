package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BiomeBlackRidge extends BiomeBase {
    public BiomeBlackRidge(BiomeProperties properties) {
        super(properties);

        this.decorator.extraTreeChance = 0.1F;

        this.topBlock = ModBlocks.NIGHTSTONE.getDefaultState();
        this.fillerBlock = ModBlocks.NIGHTSTONE.getDefaultState();
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
            this.generateCoverPlant(world, rand, pos, 2, LUMEN_GENERATOR);
            this.generateCoverPlant(world, rand, pos, 2, DOUBLE_LUMEN_GENERATOR);
        }

        super.decorate(world, rand, pos);
    }
}
