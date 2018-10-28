package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BoulderFeature extends MidnightAbstractFeature {
    private final IBlockState state;
    private final float radius;

    public BoulderFeature(IBlockState state, float radius) {
        this.state = state;
        this.radius = radius;
    }

    @Override
    public boolean placeFeature(World world, Random random, BlockPos origin) {
        origin = origin.up(MathHelper.floor(this.radius / 2.0F));

        this.generateBlob(world, origin, this.radius);

        for (int i = 0; i < 2; i++) {
            int offsetX = random.nextInt(5) - 2;
            int offsetY = -random.nextInt(2);
            int offsetZ = random.nextInt(5) - 2;
            BlockPos center = origin.add(offsetX, offsetY, offsetZ);

            float radius = this.radius + random.nextFloat() * 0.5F;
            this.generateBlob(world, center, radius);
        }

        return true;
    }

    private void generateBlob(World world, BlockPos origin, float radius) {
        float radiusSquare = radius * radius;
        int radiusCeil = MathHelper.ceil(radius);

        BlockPos minPos = origin.add(-radiusCeil, -radiusCeil, -radiusCeil);
        BlockPos maxPos = origin.add(radiusCeil, radiusCeil, radiusCeil);
        for (BlockPos pos : BlockPos.getAllInBox(minPos, maxPos)) {
            if (pos.distanceSq(origin) <= radiusSquare) {
                this.setBlockAndNotifyAdequately(world, pos, this.state);
            }
        }
    }
}
