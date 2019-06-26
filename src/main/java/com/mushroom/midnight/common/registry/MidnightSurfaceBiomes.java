package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.MidnightBiomeConfigs;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.biome.config.BiomeSpawnEntry;
import com.mushroom.midnight.common.biome.surface.DeceitfulBogBiome;
import com.mushroom.midnight.common.biome.surface.SurfaceBiome;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
@ObjectHolder(Midnight.MODID)
public class MidnightSurfaceBiomes {
    public static final Biome BLACK_RIDGE = Biomes.DEFAULT;
    public static final Biome VIGILANT_FOREST = Biomes.DEFAULT;
    public static final Biome DECEITFUL_BOG = Biomes.DEFAULT;
    public static final Biome FUNGI_FOREST = Biomes.DEFAULT;
    public static final Biome OBSCURED_PEAKS = Biomes.DEFAULT;
    public static final Biome WARPED_FIELDS = Biomes.DEFAULT;
    public static final Biome CRYSTAL_SPIRES = Biomes.DEFAULT;
    public static final Biome NIGHT_PLAINS = Biomes.DEFAULT;
    public static final Biome OBSCURED_PLATEAU = Biomes.DEFAULT;
    public static final Biome PHANTASMAL_VALLEY = Biomes.DEFAULT;
    public static final Biome RUNEBUSH_GROVE = Biomes.DEFAULT;
    public static final Biome HILLY_VIGILANT_FOREST = Biomes.DEFAULT;
    public static final Biome HILLY_FUNGI_FOREST = Biomes.DEFAULT;

    @SubscribeEvent
    public static void onRegisterBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(
                RegUtil.applyName(new SurfaceBiome("vigilant_forest", MidnightBiomeConfigs.VIGILANT_FOREST_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("black_ridge", MidnightBiomeConfigs.BLACK_RIDGE_CONFIG)),
                RegUtil.applyName(new DeceitfulBogBiome("deceitful_bog", MidnightBiomeConfigs.DECEITFUL_BOG_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("fungi_forest", MidnightBiomeConfigs.FUNGI_FOREST_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("obscured_peaks", MidnightBiomeConfigs.OBSCURED_PEAK_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("warped_fields", MidnightBiomeConfigs.WARPED_FIELDS_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("crystal_spires", MidnightBiomeConfigs.CRYSTAL_SPIRES_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("night_plains", MidnightBiomeConfigs.NIGHT_PLAINS_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("obscured_plateau", MidnightBiomeConfigs.OBSCURED_PLATEAU_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("phantasmal_valley", MidnightBiomeConfigs.PHANTASMAL_VALLEY_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("runebush_grove", MidnightBiomeConfigs.RUNEBUSH_GROVE_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("hilly_vigilant_forest", MidnightBiomeConfigs.HILLY_VIGILANT_FOREST_CONFIG)),
                RegUtil.applyName(new SurfaceBiome("hilly_fungi_forest", MidnightBiomeConfigs.HILLY_FUNGI_FOREST_CONFIG))
        );
    }

    public static void onInit() {
        BiomeDictionary.addTypes(VIGILANT_FOREST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(RUNEBUSH_GROVE, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(BLACK_RIDGE, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(FUNGI_FOREST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(OBSCURED_PEAKS, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(WARPED_FIELDS, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(CRYSTAL_SPIRES, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MAGICAL);
        BiomeDictionary.addTypes(DECEITFUL_BOG, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(NIGHT_PLAINS, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(OBSCURED_PLATEAU, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(PHANTASMAL_VALLEY, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(HILLY_VIGILANT_FOREST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(HILLY_FUNGI_FOREST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.SPOOKY);

        MidnightBiomeGroup.SURFACE.add(
                new BiomeSpawnEntry.Basic(VIGILANT_FOREST, 100),
                new BiomeSpawnEntry.Basic(FUNGI_FOREST, 70),
                new BiomeSpawnEntry.Basic(DECEITFUL_BOG, 70),
                new BiomeSpawnEntry.Basic(OBSCURED_PLATEAU, 50),
                new BiomeSpawnEntry.Basic(NIGHT_PLAINS, 100)
        );

        MidnightBiomeGroup.SURFACE_POCKET.add(
                new BiomeSpawnEntry.Basic(OBSCURED_PEAKS, 10)
                        .canReplace(OBSCURED_PLATEAU, BLACK_RIDGE),
                new BiomeSpawnEntry.Basic(WARPED_FIELDS, 5),
                new BiomeSpawnEntry.Basic(CRYSTAL_SPIRES, 3),
                new BiomeSpawnEntry.Basic(RUNEBUSH_GROVE, 3)
                        .canReplace(VIGILANT_FOREST),
                new BiomeSpawnEntry.Basic(HILLY_VIGILANT_FOREST, 5)
                        .canReplace(VIGILANT_FOREST),
                new BiomeSpawnEntry.Basic(HILLY_FUNGI_FOREST, 5)
                        .canReplace(FUNGI_FOREST)
        );
    }
}
