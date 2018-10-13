package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.fluid.FluidMiasma;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
    public static final Fluid MIASMA = new FluidMiasma();

    public static void register() {
        FluidRegistry.registerFluid(MIASMA);
        FluidRegistry.addBucketForFluid(MIASMA);
    }
}
