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
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class MidnightCarvers {
    public static final WorldCarver<ProbabilityConfig> CAVE = RegUtil.injected();

    @SubscribeEvent
    public static void registerCarvers(RegistryEvent.Register<WorldCarver<?>> event) {
        RegUtil.generic(event.getRegistry())
                .add("cave", new MidnightCaveCarver(ProbabilityConfig::deserialize, 0.5f)); // TODO scale me
    }
}
