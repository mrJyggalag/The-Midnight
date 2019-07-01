package com.mushroom.midnight.common.world.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum ProduceOutlineLayer implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, int top, int right, int bottom, int left, int value) {
        if (right != value || bottom != value) {
            return 1;
        } else {
            return 0;
        }
    }
}
