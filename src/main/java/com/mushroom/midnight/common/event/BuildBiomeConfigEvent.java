package com.mushroom.midnight.common.event;

import com.mushroom.midnight.common.biome.MidnightBiome;
import com.mushroom.midnight.common.biome.MidnightBiomeConfig;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BuildBiomeConfigEvent extends Event {
    private final MidnightBiome biome;
    private MidnightBiomeConfig.Builder builder;

    public BuildBiomeConfigEvent(MidnightBiome biome, MidnightBiomeConfig.Builder builder) {
        this.biome = biome;
        this.builder = builder;
    }

    public void clear() {
        this.builder = MidnightBiomeConfig.builder();
    }

    public void set(MidnightBiomeConfig.Builder builder) {
        this.builder = builder;
    }

    public Biome getBiome() {
        return this.biome;
    }

    public MidnightBiomeConfig.Builder getBuilder() {
        return this.builder;
    }
}
