package com.mushroom.midnight.common.world.feature.placement;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.world.generator.WorldGenMoltenCrater;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DragonNestPlacement extends Placement<NoPlacementConfig> {
    private static final int CRATER_RANGE_CHUNKS = 3;

    public DragonNestPlacement(Function<Dynamic<?>, ? extends NoPlacementConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, NoPlacementConfig config, BlockPos origin) {
        if (!isNearCrater(world, random, origin)) {
            return Stream.empty();
        }

        return IntStream.range(0, 64).mapToObj(i -> {
            int offsetX = random.nextInt(16);
            int offsetZ = random.nextInt(16);
            return origin.add(offsetX, 0, offsetZ);
        });
    }

    private static boolean isNearCrater(IWorld world, Random random, BlockPos chunkOrigin) {
        if (random.nextInt(10) != 0) {
            return false;
        }

        int chunkX = chunkOrigin.getX() >> 4;
        int chunkZ = chunkOrigin.getZ() >> 4;

        for (int deltaZ = -CRATER_RANGE_CHUNKS; deltaZ <= CRATER_RANGE_CHUNKS; deltaZ++) {
            for (int deltaX = -CRATER_RANGE_CHUNKS; deltaX <= CRATER_RANGE_CHUNKS; deltaX++) {
                if (WorldGenMoltenCrater.isCraterSource(world, chunkX + deltaX, chunkZ + deltaZ)) {
                    return true;
                }
            }
        }

        return false;
    }
}
