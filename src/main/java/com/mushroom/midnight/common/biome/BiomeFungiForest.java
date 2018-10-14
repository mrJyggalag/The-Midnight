package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.entity.creature.EntityRifter;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.generator.WorldGenMidnightFungi;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BiomeFungiForest extends BiomeBase {
    protected static final WorldGenMidnightFungi LARGE_DEWSHROOM_GENERATOR = new WorldGenMidnightFungi(
            ModBlocks.DEWSHROOM_STEM.getDefaultState(),
            ModBlocks.DEWSHROOM_HAT.getDefaultState()
    );
    protected static final WorldGenMidnightFungi LARGE_NIGHTSHROOM_GENERATOR = new WorldGenMidnightFungi(
            ModBlocks.NIGHTSHROOM_STEM.getDefaultState(),
            ModBlocks.NIGHTSHROOM_HAT.getDefaultState()
    );

    public BiomeFungiForest(BiomeProperties properties) {
        super(properties);
        this.decorator.grassPerChunk = 4;

        this.spawnableMonsterList.add(new SpawnListEntry(EntityRifter.class, 1, 0, 1));
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);

        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM)) {
            for (int i = 0; i < 4; i++) {
                int offsetX = rand.nextInt(16) + 8;
                int offsetZ = rand.nextInt(16) + 8;

                BlockPos surface = world.getTopSolidOrLiquidBlock(pos.add(offsetX, 0, offsetZ));
                WorldGenMidnightFungi generator = rand.nextBoolean() ? LARGE_DEWSHROOM_GENERATOR : LARGE_NIGHTSHROOM_GENERATOR;
                generator.generate(world, rand, surface);
            }
        }

        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.SHROOM)) {
            this.generateCoverPlant(world, rand, pos, 4, DEWSHROOM_GENERATOR);
            this.generateCoverPlant(world, rand, pos, 2, DOUBLE_DEWSHROOM_GENERATOR);
            this.generateCoverPlant(world, rand, pos, 4, NIGHTSHROOM_GENERATOR);
            this.generateCoverPlant(world, rand, pos, 2, DOUBLE_NIGHTSHROOM_GENERATOR);
        }

        super.decorate(world, rand, pos);
    }
}
