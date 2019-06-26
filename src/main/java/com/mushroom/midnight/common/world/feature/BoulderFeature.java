package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BoulderFeature extends MidnightAbstractFeature {
    protected final float radius;

    protected BoulderFeature(float radius) {
        this.radius = radius;
    }

    @Override
    public boolean placeFeature(World world, Random random, BlockPos origin) {
        origin = origin.up(MathHelper.floor(radius / 2.0F));

        generateBlob(world, random, origin, radius);
        for (int i = 0; i < 2; i++) {
            int offsetX = random.nextInt(5) - 2;
            int offsetY = -random.nextInt(2);
            int offsetZ = random.nextInt(5) - 2;
            BlockPos center = origin.add(offsetX, offsetY, offsetZ);

            float radius = this.radius + random.nextFloat() * 0.5F;
            generateBlob(world, random, center, radius);
        }
        return true;
    }

    private void generateBlob(World world, Random random, BlockPos origin, float radius) {
        float radiusSquare = radius * radius;
        int radiusCeil = MathHelper.ceil(radius);

        BlockPos minPos = origin.add(-radiusCeil, -radiusCeil, -radiusCeil);
        BlockPos maxPos = origin.add(radiusCeil, radiusCeil, radiusCeil);
        double dist;
        for (BlockPos pos : BlockPos.getAllInBox(minPos, maxPos)) {
            if ((dist = pos.distanceSq(origin)) <= radiusSquare) {
                setBlockAndNotifyAdequately(world, pos, getStateForPlacement(world, origin, pos, dist, radiusSquare, random));
            }
        }
    }

    protected abstract BlockState getStateForPlacement(World world, BlockPos origin, BlockPos pos, double dist, float radiusSquare, Random random);
}
