package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.registry.ModDimensions;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;

public class MidnightWorldProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.MIDNIGHT;
    }
}
