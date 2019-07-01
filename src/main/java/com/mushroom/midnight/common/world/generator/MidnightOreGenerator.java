package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class MidnightOreGenerator implements IWorldGenerator {
    private static final long SEED = 21052088057241959L;

    private static final BlockMatcher NIGHTSTONE_MATCHER = BlockMatcher.forBlock(MidnightBlocks.NIGHTSTONE);

    /**
     *  TODO gen ores
        {@link DefaultBiomeFeatures#addOres(net.minecraft.world.biome.Biome)}
     */
    private static final WorldGenerator DARK_PEARL_GENERATOR = new WorldGenMinable(MidnightBlocks.DARK_PEARL_ORE.getDefaultState(), 14, NIGHTSTONE_MATCHER);
    private static final WorldGenerator TENEBRUM_GENERATOR = new WorldGenMinable(MidnightBlocks.TENEBRUM_ORE.getDefaultState(), 4, NIGHTSTONE_MATCHER);
    private static final WorldGenerator NAGRILITE_GENERATOR = new WorldGenMinable(MidnightBlocks.NAGRILITE_ORE.getDefaultState(), 4, NIGHTSTONE_MATCHER);
    private static final WorldGenerator EBONYS_GENERATOR = new WorldGenMinable(MidnightBlocks.EBONYS_ORE.getDefaultState(), 6, NIGHTSTONE_MATCHER);

    private static final int DARK_PEARL_PER_CHUNK = 8;
    private static final int TENEBRUM_PER_CHUNK = 6;
    private static final int NAGRILITE_PER_CHUNK = 4;
    private static final int EBONYS_PER_CHUNK = 4;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, ChunkGenerator chunkGenerator, AbstractChunkProvider chunkProvider) {
        if (!Helper.isMidnightDimension(world)) {
            return;
        }

        random.setSeed(random.nextLong() ^ SEED);

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        int globalX = chunkX << 4;
        int globalZ = chunkZ << 4;

        for (int i = 0; i < DARK_PEARL_PER_CHUNK; i++) {
            int offsetX = random.nextInt(16);
            int offsetY = random.nextInt(56);
            int offsetZ = random.nextInt(16);

            pos.setPos(globalX + offsetX, offsetY, globalZ + offsetZ);
            DARK_PEARL_GENERATOR.generate(world, random, pos);
        }
        for (int f = 0; f < TENEBRUM_PER_CHUNK; f++) {
            int offsetX = random.nextInt(16);
            int offsetY = random.nextInt(56);
            int offsetZ = random.nextInt(16);
            pos.setPos(globalX + offsetX, offsetY, globalZ + offsetZ);
            TENEBRUM_GENERATOR.generate(world, random, pos);
        }
        for (int f = 0; f < NAGRILITE_PER_CHUNK; f++) {
            int offsetX = random.nextInt(16);
            int offsetY = random.nextInt(24);
            int offsetZ = random.nextInt(16);
            pos.setPos(globalX + offsetX, offsetY, globalZ + offsetZ);
            NAGRILITE_GENERATOR.generate(world, random, pos);
        }
        for (int f = 0; f < EBONYS_PER_CHUNK; f++) {
            int offsetX = random.nextInt(16);
            int offsetY = random.nextInt(24);
            int offsetZ = random.nextInt(16);
            pos.setPos(globalX + offsetX, offsetY, globalZ + offsetZ);
            EBONYS_GENERATOR.generate(world, random, pos);
        }
    }
}
