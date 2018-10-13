package com.mushroom.midnight.common.world.generator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenCrystalCluster extends WorldGenerator {
    private final int radius;
    private final int maxHeight;

    private final IBlockState rock;
    private final IBlockState crystal;

    public WorldGenCrystalCluster(int radius, int maxHeight, IBlockState rock, IBlockState crystal) {
        this.radius = radius;
        this.maxHeight = maxHeight;
        this.rock = rock;
        this.crystal = crystal;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        BlockPos minPos = pos.add(-this.radius, 0, -this.radius);
        BlockPos maxPos = pos.add(this.radius, 0, this.radius);
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (BlockPos.MutableBlockPos localPos : BlockPos.getAllInBoxMutable(minPos, maxPos)) {
            double deltaX = (localPos.getX() - pos.getX()) + rand.nextDouble() * 2.0 - 1.0;
            double deltaZ = (localPos.getZ() - pos.getZ()) + rand.nextDouble() * 2.0 - 1.0;
            double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
            double alpha = (this.radius - distance) / this.radius;
            int height = MathHelper.floor(Math.max(alpha * this.maxHeight, 0));
            if (height > 0) {
                BlockPos surfacePos = world.getTopSolidOrLiquidBlock(localPos);
                mutablePos.setPos(surfacePos);
                for (int offsetY = 0; offsetY < height; offsetY++) {
                    mutablePos.setY(surfacePos.getY() + offsetY);
                    world.setBlockState(mutablePos, this.rock, 3);
                }
                if (rand.nextInt(2) == 0) {
                    mutablePos.setY(surfacePos.getY() + height);
                    world.setBlockState(mutablePos, this.crystal, 3);
                }
            }
        }
        return true;
    }
}
