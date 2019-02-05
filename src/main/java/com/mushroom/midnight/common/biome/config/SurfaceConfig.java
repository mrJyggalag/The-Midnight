package com.mushroom.midnight.common.biome.config;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;

public class SurfaceConfig {
    private IBlockState topState = ModBlocks.MIDNIGHT_GRASS.getDefaultState();
    private IBlockState fillerState = ModBlocks.MIDNIGHT_DIRT.getDefaultState();
    private IBlockState wetState = ModBlocks.DECEITFUL_MUD.getDefaultState();

    public SurfaceConfig() {
    }

    public SurfaceConfig(SurfaceConfig config) {
        this.topState = config.topState;
        this.fillerState = config.fillerState;
        this.wetState = config.wetState;
    }

    public SurfaceConfig withTopState(IBlockState state) {
        this.topState = state;
        return this;
    }

    public SurfaceConfig withFillerState(IBlockState state) {
        this.fillerState = state;
        return this;
    }

    public SurfaceConfig withWetState(IBlockState state) {
        this.wetState = state;
        return this;
    }

    public IBlockState getTopState() {
        return this.topState;
    }

    public IBlockState getFillerState() {
        return this.fillerState;
    }

    public IBlockState getWetState() {
        return this.wetState;
    }

    public void apply(Biome biome) {
        biome.topBlock = this.topState;
        biome.fillerBlock = this.fillerState;
    }
}
