package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BasicBiomeSpawnEntry;
import com.mushroom.midnight.common.biome.DeceitfulBogBiome;
import com.mushroom.midnight.common.biome.MidnightBiome;
import com.mushroom.midnight.common.biome.MidnightBiomeConfigs;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
@GameRegistry.ObjectHolder(Midnight.MODID)
public class ModBiomes {
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

    // TODO: Lost rifter spawn

    @SubscribeEvent
    public static void onRegisterBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("vigilant_forest")
                                .setBaseHeight(0.155F)
                                .setHeightVariation(0.07F),
                        MidnightBiomeConfigs.VIGILANT_FOREST_CONFIG
                )),
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("black_ridge")
                                .setBaseHeight(4.5F)
                                .setHeightVariation(0.1F),
                        MidnightBiomeConfigs.BLACK_RIDGE_CONFIG
                )),
                RegUtil.applyName(new DeceitfulBogBiome(
                        new Biome.BiomeProperties("deceitful_bog")
                                .setBaseHeight(-0.38F)
                                .setHeightVariation(0.3F),
                        MidnightBiomeConfigs.DECEITFUL_BOG_CONFIG
                )),
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("fungi_forest")
                                .setBaseHeight(0.175F)
                                .setHeightVariation(0.07F),
                        MidnightBiomeConfigs.FUNGI_FOREST_CONFIG
                )),
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("obscured_peaks")
                                .setBaseHeight(6.0F)
                                .setHeightVariation(0.5F),
                        MidnightBiomeConfigs.OBSCURED_PEAKS_CONFIG
                )),
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("warped_fields")
                                .setBaseHeight(0.1F)
                                .setHeightVariation(0.8F),
                        MidnightBiomeConfigs.WARPED_FIELDS_CONFIG
                )),
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("crystal_spires")
                                .setBaseHeight(0.1F)
                                .setHeightVariation(0.12F),
                        MidnightBiomeConfigs.CRYSTAL_SPIRES_CONFIG
                )),
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("night_plains")
                                .setBaseHeight(0.12F)
                                .setHeightVariation(0.26F),
                        MidnightBiomeConfigs.NIGHT_PLAINS_CONFIG
                )),
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("obscured_plateau")
                                .setBaseHeight(5.0F)
                                .setHeightVariation(0.01F),
                        MidnightBiomeConfigs.BLACK_PLATEAU_CONFIG
                )),
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("phantasmal_valley")
                                .setBaseHeight(-1.2F)
                                .setHeightVariation(0.05F),
                        MidnightBiomeConfigs.PHANTASMAL_VALLEY_CONFIG
                ))
        );
    }

    public static void onInit() {
        BiomeDictionary.addTypes(VIGILANT_FOREST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(BLACK_RIDGE, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(FUNGI_FOREST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(OBSCURED_PEAKS, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(WARPED_FIELDS, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(CRYSTAL_SPIRES, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MAGICAL);
        BiomeDictionary.addTypes(DECEITFUL_BOG, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(NIGHT_PLAINS, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(OBSCURED_PLATEAU, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(PHANTASMAL_VALLEY, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SPOOKY);

        MidnightBiomeGroup.BASE.add(
                new BasicBiomeSpawnEntry(VIGILANT_FOREST, 100),
                new BasicBiomeSpawnEntry(FUNGI_FOREST, 70),
                new BasicBiomeSpawnEntry(DECEITFUL_BOG, 70),
                new BasicBiomeSpawnEntry(OBSCURED_PLATEAU, 50),
                new BasicBiomeSpawnEntry(NIGHT_PLAINS, 100)
        );

        MidnightBiomeGroup.SMALL.add(
                new BasicBiomeSpawnEntry(OBSCURED_PEAKS, 10)
                        .canReplace(biome -> biome == OBSCURED_PLATEAU || biome == BLACK_RIDGE),
                new BasicBiomeSpawnEntry(WARPED_FIELDS, 5),
                new BasicBiomeSpawnEntry(CRYSTAL_SPIRES, 3)
        );
    }
}
