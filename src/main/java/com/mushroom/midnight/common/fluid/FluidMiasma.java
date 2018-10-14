package com.mushroom.midnight.common.fluid;

import com.mushroom.midnight.Midnight;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidMiasma extends Fluid {
    private static final ResourceLocation STILL = new ResourceLocation(Midnight.MODID, "blocks/miasma_still");
    private static final ResourceLocation FLOWING = new ResourceLocation(Midnight.MODID, "blocks/miasma_flow");

    public FluidMiasma() {
        super("miasma", STILL, FLOWING);
        this.setUnlocalizedName(Midnight.MODID + ".miasma");
        this.setDensity(3000);
        this.setViscosity(3000);
        this.setLuminosity(15);
        this.setTemperature(400);
    }
}
