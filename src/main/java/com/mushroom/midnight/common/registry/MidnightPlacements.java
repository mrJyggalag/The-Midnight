package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.feature.placement.DragonNestPlacement;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class MidnightPlacements {
    public static final Placement<NoPlacementConfig> DRAGON_NEST = RegUtil.injected();

    @SubscribeEvent
    public static void registerPlacements(RegistryEvent.Register<Placement<?>> event) {
        RegUtil.generic(event.getRegistry())
                .add("dragon_nest", new DragonNestPlacement(NoPlacementConfig::deserialize));
    }
}
