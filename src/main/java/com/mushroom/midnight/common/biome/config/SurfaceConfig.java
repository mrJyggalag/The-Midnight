package com.mushroom.midnight.common.biome.config;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;

public class SurfaceConfig {
    private BlockState topState = MidnightBlocks.MIDNIGHT_GRASS.getDefaultState();
    private BlockState fillerState = MidnightBlocks.MIDNIGHT_DIRT.getDefaultState();
    private BlockState wetState = MidnightBlocks.DECEITFUL_MUD.getDefaultState();

    public SurfaceConfig() {
    }

    public SurfaceConfig(SurfaceConfig config) {
        this.topState = config.topState;
        this.fillerState = config.fillerState;
        this.wetState = config.wetState;
    }

    public SurfaceConfig withTopState(BlockState state) {
        this.topState = state;
        return this;
    }

    public SurfaceConfig withFillerState(BlockState state) {
        this.fillerState = state;
        return this;
    }

    public SurfaceConfig withWetState(BlockState state) {
        this.wetState = state;
        return this;
    }

    public BlockState getTopState() {
        return this.topState;
    }

    public BlockState getFillerState() {
        return this.fillerState;
    }

    public BlockState getWetState() {
        return this.wetState;
    }

    public void apply(Biome biome) {
        biome.topBlock = this.topState;
        biome.fillerBlock = this.fillerState;
    }
}
