package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.fluid.DarkWaterFluid;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class MidnightFluids {
    public static final FlowingFluid DARK_WATER = Fluids.WATER;
    public static final FlowingFluid MIASMA = Fluids.LAVA;
    public static final FlowingFluid FLOWING_DARK_WATER = Fluids.FLOWING_WATER;
    public static final FlowingFluid FLOWING_MIASMA = Fluids.FLOWING_LAVA;

    @SubscribeEvent
    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
        RegUtil.generic(event.getRegistry())
                .add("dark_water", new DarkWaterFluid.Source())
                .add("flowing_dark_water", new DarkWaterFluid.Flowing());
    }
}
