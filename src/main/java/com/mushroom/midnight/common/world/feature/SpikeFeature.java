package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class SpikeFeature extends MidnightAbstractFeature {
    private final BlockState state;

    public SpikeFeature(BlockState state) {
        this.state = state;
    }

    @Override
    public boolean placeFeature(World world, Random random, BlockPos origin) {
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
                            this.setBlockAndNotifyAdequately(world, pos, this.state);
                        }

                        if (y != 0 && bound > 1) {
                            BlockPos inversePos = origin.add(x, -y, z);
                            if (this.canReplace(world, inversePos)) {
                                this.setBlockAndNotifyAdequately(world, inversePos, this.state);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean canReplace(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getMaterial() == Material.GROUND || state.getMaterial() == Material.GRASS;
    }
}
