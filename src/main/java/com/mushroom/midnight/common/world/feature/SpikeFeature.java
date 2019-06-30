package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.world.feature.config.UniformCompositionConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class SpikeFeature extends Feature<UniformCompositionConfig> {
    public SpikeFeature(Function<Dynamic<?>, ? extends UniformCompositionConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos origin, UniformCompositionConfig config) {
        while (world.isAirBlock(origin) && origin.getY() > 2) {
            origin = origin.down();
        }

        origin = origin.up(random.nextInt(4));

        int height = random.nextInt(4) + 7;
        int baseRadius = Math.max(height / 4 + random.nextInt(2), 2);

        for (int y = 0; y < height; y++) {
            float radius = (1.0F - (float) y / height) * baseRadius;
            int bound = MathHelper.ceil(radius);

            for (int x = -bound; x <= bound; ++x) {
                float deltaX = MathHelper.abs(x) - 0.25F;

                for (int z = -bound; z <= bound; ++z) {
                    float deltaZ = MathHelper.abs(z) - 0.25F;

                    if ((x == 0 && z == 0 || deltaX * deltaX + deltaZ * deltaZ <= radius * radius) && (x != -bound && x != bound && z != -bound && z != bound || random.nextFloat() <= 0.75F)) {
                        BlockPos pos = origin.add(x, y, z);

                        if (this.canReplace(world, pos)) {
                            this.setBlockState(world, pos, config.state);
                        }

                        if (y != 0 && bound > 1) {
                            BlockPos inversePos = origin.add(x, -y, z);
                            if (this.canReplace(world, inversePos)) {
                                this.setBlockState(world, inversePos, config.state);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean canReplace(IWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getMaterial() == Material.EARTH || state.getMaterial() == Material.ORGANIC;
    }
}
