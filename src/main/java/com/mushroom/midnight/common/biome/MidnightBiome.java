package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.config.MidnightBiomeConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.biome.Biome;

public abstract class MidnightBiome<C extends MidnightBiomeConfig> extends Biome implements ConfiguredMidnightBiome<C> {
    protected final C config;

    protected MidnightBiome(String name, C config) {
        super(config.buildProperties(name));

        this.config = config;
        this.decorator = config.getFeatureConfig().createDecorator();

        config.getSpawnerConfig().apply(this);
        config.getSurfaceConfig().apply(this);
    }

    @Override
    public String getBiomeName() {
        return I18n.format("biome." + Midnight.MODID + "." + super.getBiomeName() + ".name");
    }

    @Override
    public C getConfig() {
        return this.config;
    }
}
