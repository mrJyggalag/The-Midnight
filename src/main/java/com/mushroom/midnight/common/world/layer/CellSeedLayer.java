package com.mushroom.midnight.common.world.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum CellSeedLayer implements IAreaTransformer0 {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, int x, int y) {
        return random.random(50);
    }
}
