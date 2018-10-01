package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.world.MidnightWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {
    public static final DimensionType MIDNIGHT = DimensionType.register("midnight", "_midnight", MidnightConfig.midnightDimensionId, MidnightWorldProvider.class, false);

    public static void register() {
        DimensionManager.registerDimension(MidnightConfig.midnightDimensionId, MIDNIGHT);
    }
}
