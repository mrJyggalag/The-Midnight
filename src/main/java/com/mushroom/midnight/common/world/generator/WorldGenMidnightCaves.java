package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;

public class WorldGenMidnightCaves extends MapGenCaves {
    private static final float RADIUS_SCALE = 2.2F;

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
        super.addTunnel(seed, originChunkX, originChunkZ, primer, x, y, z, radius * RADIUS_SCALE, yaw, pitch, p_180702_15_, p_180702_16_, length);
    }
}
