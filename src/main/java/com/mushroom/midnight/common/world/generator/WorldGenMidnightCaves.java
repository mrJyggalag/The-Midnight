package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BiomeLayerSampler;
import com.mushroom.midnight.common.biome.MidnightBiomeLayer;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.capability.MultiLayerBiomeSampler;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModCavernousBiomes;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;

public class WorldGenMidnightCaves extends MapGenCaves {
    @Override
    protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, IBlockState state, IBlockState up) {
        Biome biome = this.world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState top = biome.topBlock;
        IBlockState filler = biome.fillerBlock;

        if (this.canReplaceBlock(state, up) || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock()) {
            if (y > 10) {
                data.setBlockState(x, y, z, BLK_AIR);
                if (foundTop && data.getBlockState(x, y - 1, z).getBlock() == filler.getBlock()) {
                    data.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
                }
            } else {
                data.setBlockState(x, y, z, ModBlocks.MIASMA.getDefaultState());
            }
        }
    }

    @Override
    protected boolean canReplaceBlock(IBlockState state, IBlockState aboveState) {
        Material material = state.getMaterial();
        Material aboveMaterial = aboveState.getMaterial();
        return (material == Material.ROCK || material == Material.GROUND || material == Material.GRASS)
                && material != Material.WATER && material != Material.LAVA
                && aboveMaterial != Material.WATER && aboveMaterial != Material.LAVA;
    }

    @Override
    protected void addTunnel(long seed, int originChunkX, int originChunkZ, ChunkPrimer primer, double x, double y, double z, float radius, float yaw, float pitch, int p_180702_15_, int p_180702_16_, double length) {
        CavernousBiome cavernousBiome = this.getCavernousBiome(MathHelper.floor(x), MathHelper.floor(z));
        float caveRadiusScale = cavernousBiome.getConfig().getStructureConfig().getCaveRadiusScale();
        if (caveRadiusScale <= 0.01F) {
            return;
        }

        super.addTunnel(seed, originChunkX, originChunkZ, primer, x, y, z, radius * caveRadiusScale, yaw, pitch, p_180702_15_, p_180702_16_, length);
    }

    private CavernousBiome getCavernousBiome(int x, int z) {
        MultiLayerBiomeSampler multiLayerSampler = this.world.getCapability(Midnight.MULTI_LAYER_BIOME_SAMPLER_CAP, null);
        if (multiLayerSampler != null) {
            BiomeLayerSampler<CavernousBiome> undergroundLayer = multiLayerSampler.getLayer(MidnightBiomeLayer.UNDERGROUND);
            if (undergroundLayer != null) {
                return undergroundLayer.sample(x, z);
            }
        }

        return ModCavernousBiomes.CLOSED_CAVERN;
    }
}
