package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.biome.cavern.ClosedCavernBiome;
import com.mushroom.midnight.common.biome.cavern.CrystalCavernBiome;
import com.mushroom.midnight.common.biome.cavern.FungalCavernBiome;
import com.mushroom.midnight.common.biome.cavern.GreatCavernBiome;
import com.mushroom.midnight.common.biome.BiomeSpawnEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Midnight.MODID)
public class MidnightCavernousBiomes {
    public static final CavernousBiome CLOSED_CAVERN = new ClosedCavernBiome();

    public static final CavernousBiome GREAT_CAVERN = CLOSED_CAVERN;
    public static final CavernousBiome CRYSTAL_CAVERN = CLOSED_CAVERN;
    public static final CavernousBiome FUNGAL_CAVERN = CLOSED_CAVERN;

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
        RegUtil.generic(event.getRegistry())
                .add("closed_cavern", CLOSED_CAVERN)
                .add("great_cavern", new GreatCavernBiome())
                .add("crystal_cavern", new CrystalCavernBiome())
                .add("fungal_cavern", new FungalCavernBiome());
    }

    public static void onInit() {
        MidnightBiomeGroup.UNDERGROUND.add(
                new BiomeSpawnEntry.Basic(GREAT_CAVERN, 100)
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

    public static CavernousBiome byId(int id) {
        return registry.getValue(id);
    }
}
