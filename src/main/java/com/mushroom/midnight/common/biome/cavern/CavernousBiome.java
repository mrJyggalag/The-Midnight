package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.common.biome.config.SurfaceConfig;
import com.mushroom.midnight.common.world.SurfaceCoverGenerator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Random;

public class CavernousBiome extends IForgeRegistryEntry.Impl<CavernousBiome> {
    private final CavernousBiomeConfig config;

    private final SurfaceCoverGenerator coverGenerator = new SurfaceCoverGenerator(1, Integer.MAX_VALUE);

    public CavernousBiome(CavernousBiomeConfig config) {
        this.config = config;
    }

    public void coverSurface(Random rand, ChunkPrimer primer, int x, int z, double noiseVal) {
        SurfaceConfig config = this.config.getSurfaceConfig();
        if (config == null) {
            return;
        }

        int fillerDepth = (int) (noiseVal / 3.0 + 3.0 + rand.nextDouble() * 0.25);
        this.coverGenerator.coverSurface(config, primer, x, z, fillerDepth);
    }

    public CavernousBiomeConfig getConfig() {
        return this.config;
    }
}
