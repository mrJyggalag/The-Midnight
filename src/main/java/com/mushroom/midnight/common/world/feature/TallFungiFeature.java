package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.function.Function;

public class TallFungiFeature extends Feature<NoFeatureConfig> {
    private static final BlockState[] FUNGI = new BlockState[] {
            MidnightBlocks.DOUBLE_NIGHTSHROOM.getDefaultState(),
            MidnightBlocks.DOUBLE_DEWSHROOM.getDefaultState(),
            MidnightBlocks.DOUBLE_VIRIDSHROOM.getDefaultState()
    };

    public TallFungiFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos origin, NoFeatureConfig config) {
        boolean result = false;

        for (int i = 0; i < 64; i++) {
            BlockPos pos = origin.add(
                    rand.nextInt(8) - rand.nextInt(8),
                    rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(8) - rand.nextInt(8)
            );

            BlockState state = this.getRandomFungi(rand);

            if (world.isAirBlock(pos) && pos.getY() < world.getWorld().getDimension().getHeight() - 2 && state.isValidPosition(world, pos)) {
                DoublePlantBlock block = (DoublePlantBlock) state.getBlock();
                block.placeAt(world, pos, Constants.BlockFlags.NOTIFY_LISTENERS);

                result = true;
            }
        }

        return result;
    }

    protected BlockState getRandomFungi(Random random) {
        return FUNGI[random.nextInt(FUNGI.length)];
    }
}
