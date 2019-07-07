package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.fluid.DarkWaterFluid;
import com.mushroom.midnight.common.fluid.MiasmaFluid;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightFluids {
    public static final FlowingFluid DARK_WATER = new DarkWaterFluid.Source();
    public static final FlowingFluid MIASMA = new MiasmaFluid.Source();
    public static final FlowingFluid FLOWING_DARK_WATER = new DarkWaterFluid.Flowing();
    public static final FlowingFluid FLOWING_MIASMA = new MiasmaFluid.Flowing();

    @SubscribeEvent
    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
        RegUtil.generic(event.getRegistry())
                .add("dark_water", DARK_WATER)
                .add("flowing_dark_water", FLOWING_DARK_WATER)
                .add("miasma", MIASMA)
                .add("flowing_miasma", FLOWING_MIASMA);
    }
}
