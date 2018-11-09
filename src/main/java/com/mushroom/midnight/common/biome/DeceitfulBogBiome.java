package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;

import java.util.Random;

public class DeceitfulBogBiome extends MidnightBiome {
    public DeceitfulBogBiome(BiomeProperties properties, MidnightBiomeConfig config) {
        super(properties, config);
    }

    @Override
    protected SurfaceConfig configureSurface(SurfaceConfig config, SurfaceConfig parent, int x, int z, Random random) {
        IBlockState surfaceState = this.sampleSurfaceState(x, z, random);
        return config
                .withTopState(surfaceState)
                .withFillerState(surfaceState);
    }

    private IBlockState sampleSurfaceState(int x, int z, Random random) {
        double noise = GRASS_COLOR_NOISE.getValue(x * 0.03, z * 0.03);
        noise += (random.nextDouble() - 0.5) * 0.1;
        if (noise > 0.05) {
            return ModBlocks.DECEITFUL_PEAT.getDefaultState();
        }
        return ModBlocks.DECEITFUL_MUD.getDefaultState();
    }
}
