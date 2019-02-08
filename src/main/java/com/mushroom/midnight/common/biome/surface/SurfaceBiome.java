package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.config.SurfaceConfig;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class SurfaceBiome extends Biome {
    protected final SurfaceBiomeConfig config;
    private final SurfaceConfig localSurfaceConfig;

    public SurfaceBiome(String name, SurfaceBiomeConfig config) {
        super(config.buildProperties(name));

        this.config = config;
        this.localSurfaceConfig = new SurfaceConfig(this.config.getSurfaceConfig());

        this.decorator = config.getFeatureConfig().createDecorator();

        config.getSpawnerConfig().apply(this);
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

        IBlockState chosenTopBlock = this.localSurfaceConfig.getTopState();
        IBlockState chosenFillerBlock = this.localSurfaceConfig.getFillerState();
        IBlockState chosenWetBlock = this.localSurfaceConfig.getWetState();

        int seaLevel = world.getSeaLevel();
        IBlockState topBlock = chosenTopBlock;
        IBlockState fillerBlock = chosenFillerBlock;

        int currentDepth = -1;
        int fillerDepth = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int localZ = z & 15;
        int localX = x & 15;

        boolean wet = false;
        for (int height = 255; height >= 0; --height) {
            if (height <= rand.nextInt(5)) {
                primer.setBlockState(localX, height, localZ, BEDROCK);
            } else {
                IBlockState state = primer.getBlockState(localX, height, localZ);
                if (state.getMaterial() == Material.WATER) {
                    wet = true;
                }
                if (state.getMaterial() == Material.AIR) {
                    currentDepth = -1;
                } else if (state.getBlock() == ModBlocks.NIGHTSTONE) {
                    if (currentDepth == -1) {
                        if (fillerDepth <= 0) {
                            topBlock = AIR;
                            fillerBlock = ModBlocks.NIGHTSTONE.getDefaultState();
                        } else if (height >= seaLevel - 4 && height <= seaLevel + 1) {
                            topBlock = chosenTopBlock;
                            fillerBlock = chosenFillerBlock;
                        }

                        currentDepth = fillerDepth;

                        primer.setBlockState(localX, height, localZ, wet ? chosenWetBlock : topBlock);
                    } else if (currentDepth > 0) {
                        --currentDepth;
                        primer.setBlockState(localX, height, localZ, wet ? chosenWetBlock : fillerBlock);
                    }
                }
            }
        }
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
}
