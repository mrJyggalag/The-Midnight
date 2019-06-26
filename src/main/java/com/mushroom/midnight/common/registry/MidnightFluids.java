package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.fluid.FluidDarkWater;
import com.mushroom.midnight.common.fluid.FluidMiasma;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class MidnightFluids {
    public static final Fluid MIASMA = new FluidMiasma();
    public static final Fluid DARK_WATER = new FluidDarkWater();

    public static void register() {
        FluidRegistry.registerFluid(MIASMA);
        FluidRegistry.addBucketForFluid(MIASMA);

        FluidRegistry.registerFluid(DARK_WATER);
        FluidRegistry.addBucketForFluid(DARK_WATER);
    }
}
