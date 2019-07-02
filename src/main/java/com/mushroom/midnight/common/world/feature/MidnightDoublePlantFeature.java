package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.block.MidnightDoublePlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.DoublePlantConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.function.Function;

public class MidnightDoublePlantFeature extends Feature<DoublePlantConfig> {
    public MidnightDoublePlantFeature(Function<Dynamic<?>, ? extends DoublePlantConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos origin, DoublePlantConfig config) {
        boolean result = false;

        for (int i = 0; i < 64; i++) {
            BlockPos pos = origin.add(
                    random.nextInt(8) - random.nextInt(8),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(8) - random.nextInt(8)
            );

            if (world.isAirBlock(pos) && pos.getY() < world.getWorld().getDimension().getHeight() - 2 && config.state.isValidPosition(world, pos)) {
                ((MidnightDoublePlantBlock) config.state.getBlock()).placeAt(world, pos, Constants.BlockFlags.NOTIFY_LISTENERS);
                result = true;
            }
        }

        return result;
    }
}
