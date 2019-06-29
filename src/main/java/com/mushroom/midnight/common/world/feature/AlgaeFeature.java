package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.function.Function;

public class AlgaeFeature extends Feature<NoFeatureConfig> {
    private static final BlockState BLOCK = MidnightBlocks.DECEITFUL_ALGAE.getDefaultState();

    public AlgaeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos origin, NoFeatureConfig config) {
        for (int i = 0; i < 10; i++) {
            BlockPos pos = origin.add(
                    random.nextInt(8) - random.nextInt(8),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(8) - random.nextInt(8)
            );

            if (world.isAirBlock(pos) && BLOCK.isValidPosition(world, pos)) {
                world.setBlockState(pos, BLOCK, Constants.BlockFlags.NOTIFY_LISTENERS);
            }
        }

        return true;
    }
}
