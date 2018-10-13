package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.generator.WorldGenCrystalCluster;
import com.mushroom.midnight.common.world.generator.WorldGenMidnightPlant;
import net.minecraft.block.BlockBush;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BiomeCrystalPlains extends BiomeBase {
    private static final WorldGenerator CRYSTAL_GENERATOR = new WorldGenCrystalCluster(3, 3, ModBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(), ModBlocks.BLOOMCRYSTAL.getDefaultState());
    private static final WorldGenerator CRYSTAL_SPIRE_GENERATOR = new WorldGenCrystalCluster(1, 3, ModBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(), ModBlocks.BLOOMCRYSTAL.getDefaultState());

    private static final WorldGenerator CRYSTAL_FLOWER_GENERATOR = new WorldGenMidnightPlant(
            ModBlocks.CRYSTAL_FLOWER.getDefaultState(),
            ((BlockBush) ModBlocks.CRYSTAL_FLOWER)::canBlockStay,
            12
    );

    public BiomeCrystalPlains(BiomeProperties properties) {
        super(properties);
        this.decorator.extraTreeChance = 0.1F;
        this.decorator.grassPerChunk = 2;
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos) {
        int count = rand.nextInt(2) + 2;
        for (int i = 0; i < count; i++) {
            int offsetX = rand.nextInt(16) + 8;
            int offsetZ = rand.nextInt(16) + 8;
            CRYSTAL_GENERATOR.generate(world, rand, pos.add(offsetX, 0, offsetZ));
        }

        int spireCount = rand.nextInt(2) + 2;
        for (int i = 0; i < spireCount; i++) {
            int offsetX = rand.nextInt(16) + 8;
            int offsetZ = rand.nextInt(16) + 8;
            CRYSTAL_SPIRE_GENERATOR.generate(world, rand, pos.add(offsetX, 0, offsetZ));
        }

        ChunkPos chunkPos = new ChunkPos(pos);
        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
            this.generateCoverPlant(world, rand, pos, 2, LUMEN_GENERATOR);
            this.generateCoverPlant(world, rand, pos, 2, DOUBLE_LUMEN_GENERATOR);

            this.generateCoverPlant(world, rand, pos, 5, CRYSTAL_FLOWER_GENERATOR);
        }

        super.decorate(world, rand, pos);
    }
}
