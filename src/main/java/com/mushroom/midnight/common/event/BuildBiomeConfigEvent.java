package com.mushroom.midnight.common.event;

import com.mushroom.midnight.common.biome.MidnightBiomeConfig;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BuildBiomeConfigEvent extends Event {
    private MidnightBiomeConfig.Builder builder;

    public BuildBiomeConfigEvent(MidnightBiomeConfig.Builder builder) {
        this.builder = builder;
    }

    public void clear() {
        this.builder = MidnightBiomeConfig.builder();
    }

    public void set(MidnightBiomeConfig.Builder builder) {
        this.builder = builder;
    }

    public MidnightBiomeConfig.Builder getBuilder() {
        return this.builder;
    }
}
