package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;

import java.util.Random;

public class MoltenCraterBiome extends MidnightBiome {
    public MoltenCraterBiome(BiomeProperties properties, MidnightBiomeConfig config) {
        super(properties, config);
    }

    @Override
    protected IBlockState chooseTopBlock(int x, int z, Random random) {
        if (random.nextInt(5) == 0) {
            return ModBlocks.MIASMA_SURFACE.getDefaultState();
        }
        return ModBlocks.TRENCHSTONE.getDefaultState();
    }
}
