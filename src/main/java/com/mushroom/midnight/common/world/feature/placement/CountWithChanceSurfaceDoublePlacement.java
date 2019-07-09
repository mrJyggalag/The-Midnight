package com.mushroom.midnight.common.world.feature.placement;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.world.PlacementLevel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CountWithChanceSurfaceDoublePlacement extends Placement<HeightWithChanceConfig> {
    private final PlacementLevel placementLevel;

    public CountWithChanceSurfaceDoublePlacement(Function<Dynamic<?>, ? extends HeightWithChanceConfig> deserialize, PlacementLevel placementLevel) {
        super(deserialize);
        this.placementLevel = placementLevel;
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, HeightWithChanceConfig config, BlockPos origin) {
        return IntStream.range(0, config.count).filter(i -> random.nextFloat() < config.chance).mapToObj(i -> {
            int x = random.nextInt(16);
            int z = random.nextInt(16);

            int maxY = this.placementLevel.getSurfacePos(world, Heightmap.Type.MOTION_BLOCKING, origin.add(x, 0, z)).getY() * 2;
            if (maxY <= 0) return null;

            int y = random.nextInt(maxY);
            if (!this.placementLevel.containsY(world, y)) return null;

            return origin.add(x, y, z);
        }).filter(Objects::nonNull);
    }
}
