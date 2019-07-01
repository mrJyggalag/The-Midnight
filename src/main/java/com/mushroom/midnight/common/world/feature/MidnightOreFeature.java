package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightTags;
import com.mushroom.midnight.common.world.feature.config.MidnightOreConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class MidnightOreFeature extends Feature<MidnightOreConfig> {
    public MidnightOreFeature(Function<Dynamic<?>, ? extends MidnightOreConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, MidnightOreConfig config) {
        float f = random.nextFloat() * (float) Math.PI;
        float length = (float) config.size / 8.0F;

        int halfSize = MathHelper.ceil((config.size / 16.0F * 2.0F + 1.0F) / 2.0F);

        double startX = pos.getX() + MathHelper.sin(f) * length;
        double endX = pos.getX() - MathHelper.sin(f) * length;
        double startZ = pos.getZ() + MathHelper.cos(f) * length;
        double endZ = pos.getZ() - MathHelper.cos(f) * length;

        double startY = pos.getY() + random.nextInt(3) - 2;
        double endY = pos.getY() + random.nextInt(3) - 2;

        int minX = pos.getX() - MathHelper.ceil(length) - halfSize;
        int minY = pos.getY() - 2 - halfSize;
        int minZ = pos.getZ() - MathHelper.ceil(length) - halfSize;

        int sizeXZ = 2 * (MathHelper.ceil(length) + halfSize);
        int sizeY = 2 * (2 + halfSize);

        for (int x = minX; x <= minX + sizeXZ; ++x) {
            for (int z = minZ; z <= minZ + sizeXZ; ++z) {
                if (minY <= world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z)) {
                    return this.tryPlaceVein(world, random, config, startX, endX, startZ, endZ, startY, endY, minX, minY, minZ, sizeXZ, sizeY);
                }
            }
        }

        return false;
    }

    private boolean tryPlaceVein(IWorld world, Random random, MidnightOreConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int minX, int minY, int minZ, int sizeXZ, int sizeY) {
        int placedBlocks = 0;
        BitSet mask = new BitSet(sizeXZ * sizeY * sizeXZ);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        double[] xTable = new double[config.size];
        double[] yTable = new double[config.size];
        double[] zTable = new double[config.size];
        double[] distTable = new double[config.size];

        for (int i = 0; i < config.size; ++i) {
            float alpha = (float) i / config.size;
            double x = MathHelper.lerp(alpha, startX, endX);
            double y = MathHelper.lerp(alpha, startY, endY);
            double z = MathHelper.lerp(alpha, startZ, endZ);
            double s = random.nextDouble() * config.size / 16.0;
            double dist = ((MathHelper.sin((float) Math.PI * alpha) + 1.0F) * s + 1.0) / 2.0;
            xTable[i] = x;
            yTable[i] = y;
            zTable[i] = z;
            distTable[i] = dist;
        }

        for (int i = 0; i < config.size - 1; ++i) {
            if (distTable[i] <= 0.0) {
                continue;
            }

            for (int j = i + 1; j < config.size; ++j) {
                if (distTable[j] <= 0.0) {
                    continue;
                }

                double deltaX = xTable[i] - xTable[j];
                double deltaY = yTable[i] - yTable[j];
                double deltaZ = zTable[i] - zTable[j];
                double deltaRad = distTable[i] - distTable[j];
                if (deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ < deltaRad * deltaRad) {
                    if (deltaRad > 0.0) {
                        distTable[j] = -1.0;
                    } else {
                        distTable[i] = -1.0;
                    }
                }
            }
        }

        for (int i = 0; i < config.size; ++i) {
            double distance = distTable[i];
            if (distance < 0.0) {
                continue;
            }

            double x = xTable[i];
            double y = yTable[i];
            double z = zTable[i];

            int minBX = Math.max(MathHelper.floor(x - distance), minX);
            int minBY = Math.max(MathHelper.floor(y - distance), minY);
            int minBZ = Math.max(MathHelper.floor(z - distance), minZ);
            int maxBX = Math.max(MathHelper.floor(x + distance), minBX);
            int maxBY = Math.max(MathHelper.floor(y + distance), minBY);
            int maxBZ = Math.max(MathHelper.floor(z + distance), minBZ);

            for (int bx = minBX; bx <= maxBX; ++bx) {
                double deltaX = (bx + 0.5 - x) / distance;
                if (deltaX * deltaX < 1.0) {
                    for (int by = minBY; by <= maxBY; ++by) {
                        double deltaY = (by + 0.5 - y) / distance;
                        if (deltaX * deltaX + deltaY * deltaY < 1.0) {
                            for (int bz = minBZ; bz <= maxBZ; ++bz) {
                                double deltaZ = (bz + 0.5 - z) / distance;
                                if (deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ < 1.0) {
                                    int maskIndex = bx - minX + (by - minY) * sizeXZ + (bz - minZ) * sizeXZ * sizeY;
                                    if (!mask.get(maskIndex)) {
                                        mask.set(maskIndex);
                                        pos.setPos(bx, by, bz);
                                        if (world.getBlockState(pos).isIn(MidnightTags.Blocks.CAN_HOLD_ORES)) {
                                            world.setBlockState(pos, config.state, 2);
                                            placedBlocks++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return placedBlocks > 0;
    }
}
