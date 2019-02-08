package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.biome.cavern.CavernousBiomeConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
@GameRegistry.ObjectHolder(Midnight.MODID)
public class ModCavernousBiomes {
    public static final CavernousBiome NONE = RegUtil.withName(new CavernousBiome(CavernousBiomeConfig.builder().build()), "none");

    private static ForgeRegistry<CavernousBiome> registry;

    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event) {
        registry = (ForgeRegistry<CavernousBiome>) new RegistryBuilder<CavernousBiome>()
                .setType(CavernousBiome.class)
                .setName(new ResourceLocation(Midnight.MODID, "cavernous_biomes"))
                .setDefaultKey(new ResourceLocation(Midnight.MODID, "none"))
                .create();
    }

    @SubscribeEvent
    public static void onRegisterBiomes(RegistryEvent.Register<CavernousBiome> event) {
        event.getRegistry().register(NONE);

        event.getRegistry().registerAll(
        );
    }

    public static ForgeRegistry<CavernousBiome> getRegistry() {
        if (registry == null) {
            throw new IllegalStateException("Registry not yet initialized");
        }

        return registry;
    }

    public static CavernousBiome fromId(int id) {
        return registry.getValue(id);
    }
}
