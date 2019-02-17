package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.EntitySpawnConfigured;
import com.mushroom.midnight.common.biome.config.SpawnerConfig;
import com.mushroom.midnight.common.biome.config.SurfaceConfig;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import com.mushroom.midnight.common.world.SurfaceCoverGenerator;
import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class SurfaceBiome extends Biome implements EntitySpawnConfigured {
    protected final SurfaceBiomeConfig config;
    private final SurfaceConfig localSurfaceConfig;

    private final SurfaceCoverGenerator coverGenerator = new SurfaceCoverGenerator(0, 0);

    public SurfaceBiome(String name, SurfaceBiomeConfig config) {
        super(config.buildProperties(name));

        this.config = config;
        this.localSurfaceConfig = new SurfaceConfig(this.config.getSurfaceConfig());

        this.decorator = config.getFeatureConfig().createDecorator(PlacementLevel.INSTANCE);

        for (EnumCreatureType creatureType : EnumCreatureType.values()) {
            this.getSpawnableList(creatureType).clear();
        }

        config.getSurfaceConfig().apply(this);
    }

    public SurfaceBiomeConfig getConfig() {
        return this.config;
    }

    @Override
    public String getBiomeName() {
        return I18n.format("biome." + Midnight.MODID + "." + super.getBiomeName() + ".name");
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, ChunkPrimer primer, int z, int x, double noiseVal) {
        this.configureSurface(this.localSurfaceConfig, this.config.getSurfaceConfig(), x, z, rand);
        int fillerDepth = (int) (noiseVal / 3.0 + 3.0 + rand.nextDouble() * 0.25);

        this.coverGenerator.coverSurface(this.localSurfaceConfig, primer, x, z, fillerDepth);
    }

    protected SurfaceConfig configureSurface(SurfaceConfig config, SurfaceConfig parent, int x, int z, Random random) {
        return config;
    }

    @Override
    public int getGrassColorAtPos(BlockPos pos) {
        return this.getModdedBiomeGrassColor(this.config.getGrassColor());
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos) {
        return this.getModdedBiomeFoliageColor(this.config.getFoliageColor());
    }

    public static SurfaceTerrainConfig getTerrainConfig(Biome biome) {
        if (biome instanceof SurfaceBiome) {
            return ((SurfaceBiome) biome).getConfig().getTerrainConfig();
        }
        return SurfaceTerrainConfig.DEFAULT;
    }

    @Override
    public SpawnerConfig getSpawnerConfig() {
        return this.config.getSpawnerConfig();
    }

    public static final class PlacementLevel implements SurfacePlacementLevel {
        public static SurfacePlacementLevel INSTANCE = new PlacementLevel();

        private PlacementLevel() {
        }

        @Override
        public BlockPos getSurfacePos(World world, BlockPos pos) {
            return world.getHeight(pos);
        }

        @Override
        public int generateUpTo(World world, Random random, int y) {
            int bound = Math.max(y - MidnightChunkGenerator.MIN_SURFACE_LEVEL, 1);
            return random.nextInt(bound) + MidnightChunkGenerator.MIN_SURFACE_LEVEL;
        }
    }
}
