package com.mushroom.midnight.common.fluid;

import com.mushroom.midnight.Midnight;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.Color;

public class FluidMiasma extends Fluid {
    private static final ResourceLocation STILL = new ResourceLocation(Midnight.MODID, "blocks/miasma_still");
    private static final ResourceLocation FLOWING = new ResourceLocation(Midnight.MODID, "blocks/miasma_flow");

    public FluidMiasma() {
        super("miasma", STILL, FLOWING, new Color(206, 69, 216));
        this.setUnlocalizedName(Midnight.MODID + ".miasma");
        this.setDensity(3000);
        this.setViscosity(3000);
    }
}
