package com.mushroom.midnight.common.fluid;

import com.mushroom.midnight.Midnight;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidDarkWater extends Fluid {
    private static final ResourceLocation STILL = new ResourceLocation(Midnight.MODID, "blocks/dark_water_still");
    private static final ResourceLocation FLOWING = new ResourceLocation(Midnight.MODID, "blocks/dark_water_flow");

    public FluidDarkWater() {
        super("dark_water", STILL, FLOWING);
        this.setUnlocalizedName(Midnight.MODID + ".dark_water");
    }
}
