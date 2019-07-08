package com.mushroom.midnight.common.world.feature.placement;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.world.PlacementLevel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public class ChanceSurfaceDoublePlacement extends Placement<ChanceConfig> {
    private final PlacementLevel placementLevel;

    public ChanceSurfaceDoublePlacement(Function<Dynamic<?>, ? extends ChanceConfig> deserialize, PlacementLevel placementLevel) {
        super(deserialize);
        this.placementLevel = placementLevel;
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, ChanceConfig config, BlockPos origin) {
        if (random.nextFloat() < 1.0F / config.chance) {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            int maxY = this.placementLevel.getSurfacePos(world, Type.MOTION_BLOCKING, origin.add(x, 0, z)).getY() * 2;
            if (maxY <= 0) return null;

            int y = this.placementLevel.generateUpTo(world, random, maxY);
            return Stream.of(origin.add(x, y, z));
        }

        return Stream.empty();
    }
}
