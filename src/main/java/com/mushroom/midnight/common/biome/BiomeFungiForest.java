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
    protected static final WorldGenMidnightFungi[] LARGE_FUNGI_GENERATORS = new WorldGenMidnightFungi[] {
            new WorldGenMidnightFungi(
                    ModBlocks.DEWSHROOM_STEM.getDefaultState(),
                    ModBlocks.DEWSHROOM_HAT.getDefaultState()
            ),
            new WorldGenMidnightFungi(
                    ModBlocks.NIGHTSHROOM_STEM.getDefaultState(),
                    ModBlocks.NIGHTSHROOM_HAT.getDefaultState()
            ),
            new WorldGenMidnightFungi(
                    ModBlocks.VIRIDSHROOM_STEM.getDefaultState(),
                    ModBlocks.VIRIDSHROOM_HAT.getDefaultState()
            )
    };

    public BiomeFungiForest(BiomeProperties properties) {
        super(properties);
        this.decorator.grassPerChunk = 2;

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
                WorldGenMidnightFungi generator = LARGE_FUNGI_GENERATORS[rand.nextInt(LARGE_FUNGI_GENERATORS.length)];
                generator.generate(world, rand, surface);
            }
        }

        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.SHROOM)) {
            this.generateCoverPlant(world, rand, pos, 8, FUNGI_GENERATOR);
            this.generateCoverPlant(world, rand, pos, 6, DOUBLE_FUNGI_GENERATOR);
        }

        super.decorate(world, rand, pos);
    }
}
