package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class SurfaceBiome extends Biome {
    private final float ridgeWeight;
    private final float densityScale;
    private final boolean wet;

    private final int grassColor;
    private final int foliageColor;

    protected SurfaceBiome(Builder builder) {
        super(builder);

        this.ridgeWeight = builder.ridgeWeight;
        this.densityScale = builder.densityScale;
        this.wet = builder.wet;

        this.grassColor = builder.grassColor;
        this.foliageColor = builder.foliageColor;
    }

    @Override
    public int getGrassColor(BlockPos pos) {
        return this.grassColor;
    }

    @Override
    public int getFoliageColor(BlockPos pos) {
        return this.foliageColor;
    }

    public float getRidgeWeight() {
        return this.ridgeWeight;
    }

    public float getDensityScale() {
        return this.densityScale;
    }

    public boolean isWet() {
        return this.wet;
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

    public static class Builder extends Biome.Builder {
        private float ridgeWeight = 1.0F;
        private float densityScale = 1.0F;
        private boolean wet;
        private int grassColor = 0xB084BC;
        private int foliageColor = 0x8F6DBC;

        public Builder() {
            super.precipitation(RainType.NONE);
            super.downfall(0.0F);
            super.temperature(0.0F);
            super.waterColor(0x361D78);
            super.waterFogColor(0x50533);
        }

        public Builder ridgeWeight(float ridgeWeight) {
            this.ridgeWeight = ridgeWeight;
            return this;
        }

        public Builder densityScale(float densityScale) {
            this.densityScale = densityScale;
            return this;
        }

        public Builder wet() {
            this.wet = true;
            return this;
        }

        public Builder grassColor(int grassColor) {
            this.grassColor = grassColor;
            return this;
        }

        public Builder foliageColor(int foliageColor) {
            this.foliageColor = foliageColor;
            return this;
        }

        @Override
        public <SC extends ISurfaceBuilderConfig> Builder surfaceBuilder(SurfaceBuilder<SC> surface, SC config) {
            super.surfaceBuilder(surface, config);
            return this;
        }

        @Override
        public Builder surfaceBuilder(ConfiguredSurfaceBuilder<?> surface) {
            super.surfaceBuilder(surface);
            return this;
        }

        @Override
        public Builder precipitation(RainType rainType) {
            super.precipitation(rainType);
            return this;
        }

        @Override
        public Builder category(Category category) {
            super.category(category);
            return this;
        }

        @Override
        public Builder depth(float depth) {
            super.depth(depth);
            return this;
        }

        @Override
        public Builder scale(float scale) {
            super.scale(scale);
            return this;
        }

        @Override
        public Builder temperature(float temperature) {
            super.temperature(temperature);
            return this;
        }

        @Override
        public Builder downfall(float downfall) {
            super.downfall(downfall);
            return this;
        }

        @Override
        public Builder waterColor(int waterColor) {
            super.waterColor(waterColor);
            return this;
        }

        @Override
        public Builder waterFogColor(int waterFogColor) {
            super.waterFogColor(waterFogColor);
            return this;
        }

        @Override
        public Builder parent(@Nullable String parent) {
            super.parent(parent);
            return this;
        }
    }
}
