package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.biome.cavern.CavernStructureConfig;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.biome.cavern.CavernousBiomeConfig;
import com.mushroom.midnight.common.biome.config.BiomeSpawnEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import static com.mushroom.midnight.common.biome.MidnightBiomeConfigs.OPEN_CAVERN_CONFIG;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
@GameRegistry.ObjectHolder(Midnight.MODID)
public class ModCavernousBiomes {
    private static final CavernStructureConfig CLOSED_STRUCTURE_CONFIG = new CavernStructureConfig()
            .withCavernDensity(5.0F);

    public static final CavernousBiome CLOSED = RegUtil.withName(new CavernousBiome(CavernousBiomeConfig.builder()
            .withStructure(CLOSED_STRUCTURE_CONFIG)
            .build()), "closed");

    public static final CavernousBiome OPEN = CLOSED;

    private static ForgeRegistry<CavernousBiome> registry;

    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event) {
        registry = (ForgeRegistry<CavernousBiome>) new RegistryBuilder<CavernousBiome>()
                .setType(CavernousBiome.class)
                .setName(new ResourceLocation(Midnight.MODID, "cavernous_biomes"))
                .setDefaultKey(new ResourceLocation(Midnight.MODID, "closed"))
                .create();
    }

    @SubscribeEvent
    public static void onRegisterBiomes(RegistryEvent.Register<CavernousBiome> event) {
        event.getRegistry().registerAll(
                CLOSED,
                RegUtil.withName(new CavernousBiome(OPEN_CAVERN_CONFIG), "open")
        );
    }

    public static void onInit() {
        MidnightBiomeGroup.UNDERGROUND.add(new BiomeSpawnEntry.Basic(CLOSED, 100));
        MidnightBiomeGroup.UNDERGROUND.add(new BiomeSpawnEntry.Basic(OPEN, 100));
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
