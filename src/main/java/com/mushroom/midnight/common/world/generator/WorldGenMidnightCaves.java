package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BiomeLayerSampler;
import com.mushroom.midnight.common.biome.MidnightBiomeLayer;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.capability.MultiLayerBiomeSampler;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Arrays;

public class WorldGenMidnightCaves extends MapGenCaves {
    @Override
    protected void recursiveGenerate(World world, int chunkX, int chunkZ, int originalX, int originalZ, ChunkPrimer primer) {
        int nodeCount = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);
        if (this.rand.nextInt(7) != 0) {
            nodeCount = 0;
        }

        for (int i = 0; i < nodeCount; ++i) {
            double startX = (chunkX << 4) + this.rand.nextInt(16);
            double startY = this.rand.nextInt(this.rand.nextInt(world.getSeaLevel() * 2) + 8);
            double startZ = (chunkZ << 4) + this.rand.nextInt(16);

            int branchCount = 1;
            if (this.rand.nextInt(4) == 0) {
                this.addRoom(this.rand.nextLong(), originalX, originalZ, primer, startX, startY, startZ);
                branchCount += this.rand.nextInt(4);
            }

            for (int branch = 0; branch < branchCount; branch++) {
                float horizAngle = this.rand.nextFloat() * (float) (Math.PI * 2.0F);
                float vertAngle = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;

                float radius = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
                if (this.rand.nextInt(10) == 0) {
                    radius *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
                }

                this.addTunnel(this.rand.nextLong(), originalX, originalZ, primer, startX, startY, startZ, radius, horizAngle, vertAngle, 0, 0, 1.0D);
            }
        }
    }

    @Override
    protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, BlockState state, BlockState up) {
        Biome biome = this.world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        BlockState top = biome.topBlock;
        BlockState filler = biome.fillerBlock;

        if (this.canReplaceBlock(state, up) || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock()) {
            if (y > 10) {
                data.setBlockState(x, y, z, BLK_AIR);
                if (foundTop && data.getBlockState(x, y - 1, z).getBlock() == filler.getBlock()) {
                    data.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
                }
            } else {
                data.setBlockState(x, y, z, MidnightBlocks.MIASMA.getDefaultState());
            }
        }
    }

    @Override
    protected boolean canReplaceBlock(BlockState state, BlockState aboveState) {
        Material material = state.getMaterial();
        Material aboveMaterial = aboveState.getMaterial();
        return (material == Material.ROCK || material == Material.GROUND || material == Material.GRASS)
                && material != Material.WATER && material != Material.LAVA
                && aboveMaterial != Material.WATER && aboveMaterial != Material.LAVA;
    }

    @Override
    protected void addTunnel(long seed, int originChunkX, int originChunkZ, ChunkPrimer primer, double x, double y, double z, float radius, float yaw, float pitch, int p_180702_15_, int p_180702_16_, double length) {
        float caveRadiusScale = this.getRadiusScale(MathHelper.floor(x), MathHelper.floor(z));
        if (caveRadiusScale <= 0.01F) {
            return;
        }

        super.addTunnel(seed, originChunkX, originChunkZ, primer, x, y, z, radius * caveRadiusScale, yaw, pitch, p_180702_15_, p_180702_16_, length);
    }

    private float getRadiusScale(int x, int z) {
        MultiLayerBiomeSampler multiLayerSampler = this.world.getCapability(Midnight.MULTI_LAYER_BIOME_SAMPLER_CAP, null);
        if (multiLayerSampler != null) {
            BiomeLayerSampler<CavernousBiome> undergroundLayer = multiLayerSampler.getLayer(MidnightBiomeLayer.UNDERGROUND);

            if (undergroundLayer != null) {
                float current = getRadiusScale(undergroundLayer.sample(x, z));
                float west = getRadiusScale(undergroundLayer.sample(x - 16, z));
                float east = getRadiusScale(undergroundLayer.sample(x + 16, z));
                float north = getRadiusScale(undergroundLayer.sample(x, z - 16));
                float south = getRadiusScale(undergroundLayer.sample(x, z + 16));

                return (float) max(current, west, east, north, south);
            }
        }

        return 1.0F;
    }

    private static double max(double... values) {
        return Arrays.stream(values).max().orElse(0.0);
    }

    private static float getRadiusScale(CavernousBiome biome) {
        return biome.getConfig().getStructureConfig().getCaveRadiusScale();
    }
}
