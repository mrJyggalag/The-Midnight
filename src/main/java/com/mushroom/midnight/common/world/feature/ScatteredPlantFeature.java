package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightTags;
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

public class ScatteredPlantFeature extends Feature<NoFeatureConfig> {
    private final BlockState state;

    public ScatteredPlantFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize, BlockState state) {
        super(deserialize);
        this.state = state;
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos origin, NoFeatureConfig config) {
        boolean result = false;

        for (int i = 0; i < 64; i++) {
            BlockPos pos = origin.add(
                    random.nextInt(8) - random.nextInt(8),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(8) - random.nextInt(8)
            );

            if (world.isAirBlock(pos) && world.getBlockState(pos.down()).isIn(MidnightTags.Blocks.PLANTABLE_GROUNDS)) {
                world.setBlockState(pos, this.state, Constants.BlockFlags.NOTIFY_LISTENERS);
                result = true;
            }
        }

        return result;
    }
}
