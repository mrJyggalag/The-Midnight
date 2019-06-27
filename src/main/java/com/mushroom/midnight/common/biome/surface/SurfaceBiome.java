package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.EntitySpawnConfigured;
import com.mushroom.midnight.common.biome.config.SpawnerConfig;
import com.mushroom.midnight.common.biome.config.SurfaceConfig;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import com.mushroom.midnight.common.world.SurfaceCoverGenerator;
import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        this.flowers.clear();
    }

    public SurfaceBiomeConfig getConfig() {
        return this.config;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
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
    public void addFlower(BlockState state, int weight) {
        if (MidnightConfig.general.foreignFlowersFromBonemeal.get()) {
            super.addFlower(state, weight);
        }
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

    @Override
    public void plantFlower(World world, Random rand, BlockPos pos) {
        final List<FlowerEntry> flowerList;
        if (MidnightConfig.general.foreignFlowersFromBonemeal.get() && !this.flowers.isEmpty()) {
            flowerList = Stream.concat(this.flowers.stream(), this.config.getFeatureConfig().getFlowers().stream()).collect(Collectors.toList());
        } else {
            flowerList = this.config.getFeatureConfig().getFlowers();
        }
        if (!flowerList.isEmpty()) {
            Biome.FlowerEntry flower = WeightedRandom.getRandomItem(rand, flowerList);
            if (flower != null && flower.state != null && (!(flower.state.getBlock() instanceof BlockBush) || ((BlockBush) flower.state.getBlock()).canBlockStay(world, pos, flower.state))) {
                world.setBlockState(pos, flower.state, 3);
            }
        }
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
