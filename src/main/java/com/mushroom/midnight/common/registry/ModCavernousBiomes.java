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

import static com.mushroom.midnight.common.biome.MidnightBiomeConfigs.*;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
@GameRegistry.ObjectHolder(Midnight.MODID)
public class ModCavernousBiomes {
    private static final CavernStructureConfig CLOSED_STRUCTURE_CONFIG = new CavernStructureConfig()
            .withCavernDensity(5.0F);

    public static final CavernousBiome CLOSED_CAVERN = RegUtil.withName(new CavernousBiome(CavernousBiomeConfig.builder()
            .withStructure(CLOSED_STRUCTURE_CONFIG)
            .build()), "closed_cavern");

    public static final CavernousBiome GREAT_CAVERN = CLOSED_CAVERN;
    public static final CavernousBiome CRYSTAL_CAVERN = CLOSED_CAVERN;
    public static final CavernousBiome CRAMPED_CAVERN = CLOSED_CAVERN;

    private static ForgeRegistry<CavernousBiome> registry;

    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event) {
        registry = (ForgeRegistry<CavernousBiome>) new RegistryBuilder<CavernousBiome>()
                .setType(CavernousBiome.class)
                .setName(new ResourceLocation(Midnight.MODID, "cavernous_biomes"))
                .setDefaultKey(new ResourceLocation(Midnight.MODID, "closed_cavern"))
                .create();
    }

    @SubscribeEvent
    public static void onRegisterBiomes(RegistryEvent.Register<CavernousBiome> event) {
        event.getRegistry().registerAll(
                CLOSED_CAVERN,
                RegUtil.withName(new CavernousBiome(GREAT_CAVERN_CONFIG), "great_cavern"),
                RegUtil.withName(new CavernousBiome(CRYSTAL_CAVERN_CONFIG), "crystal_cavern"),
                RegUtil.withName(new CavernousBiome(CRAMPED_CAVERN_CONFIG), "cramped_cavern")
        );
    }

    public static void onInit() {
        MidnightBiomeGroup.UNDERGROUND.add(
                new BiomeSpawnEntry.Basic(GREAT_CAVERN, 100),
                new BiomeSpawnEntry.Basic(CRAMPED_CAVERN, 50)
        );

        MidnightBiomeGroup.UNDERGROUND_POCKET.add(
                new BiomeSpawnEntry.Basic(CRYSTAL_CAVERN, 10)
                        .canReplace(GREAT_CAVERN)
        );
    }

    public static ForgeRegistry<CavernousBiome> getRegistry() {
        if (registry == null) {
            throw new IllegalStateException("Registry not yet initialized");
        }
        return registry;
    }

    public static int getId(CavernousBiome biome) {
        return registry.getID(biome);
    }

    public static CavernousBiome fromId(int id) {
        return registry.getValue(id);
    }
}
