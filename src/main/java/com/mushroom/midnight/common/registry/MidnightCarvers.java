package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.generator.MidnightCaveCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightCarvers {
    public static final WorldCarver<ProbabilityConfig> WIDE_CAVE = RegUtil.injected();

    @SubscribeEvent
    public static void registerCarvers(RegistryEvent.Register<WorldCarver<?>> event) {
        RegUtil.generic(event.getRegistry())
                .add("wide_cave", new MidnightCaveCarver(ProbabilityConfig::deserialize, 5.0F));
    }
}
