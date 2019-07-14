package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.inventory.CacheContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.mushroom.midnight.Midnight.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightContainers {
    public static ContainerType<CacheContainer> CACHE = new ContainerType<>(CacheContainer::new);

    @SubscribeEvent
    public static void registerContainer(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(
                CACHE.setRegistryName(MODID, "cache")
        );
    }
}