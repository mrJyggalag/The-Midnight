package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.MidnightBiome;
import com.mushroom.midnight.common.biome.MidnightBiomeConfigs;
import com.mushroom.midnight.common.biome.MoltenCraterBiome;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
@GameRegistry.ObjectHolder(Midnight.MODID)
public class ModBiomes {
    public static final Biome BLACK_RIDGE = Biomes.DEFAULT;
    public static final Biome VIGILANT_FOREST = Biomes.DEFAULT;
    public static final Biome DECEITFUL_BOG = Biomes.DEFAULT;
    public static final Biome FUNGI_FOREST = Biomes.DEFAULT;
    public static final Biome MOLTEN_CRATER = Biomes.DEFAULT;
    public static final Biome MOLTEN_CRATER_EDGE = Biomes.DEFAULT;
    public static final Biome OBSCURED_PEAKS = Biomes.DEFAULT;
    public static final Biome WARPED_FIELDS = Biomes.DEFAULT;
    public static final Biome CRYSTAL_PLAINS = Biomes.DEFAULT;

    private static final List<Biome> SEED_BIOMES = new ArrayList<>();

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
                // TODO: Reimplement
                /*RegUtil.applyName(new BiomeDeceitfulBog(
                        new Biome.BiomeProperties("deceitful_bog")
                                .setBaseHeight(0.125F)
                                .setHeightVariation(0.03F)
                )),*/
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("fungi_forest")
                                .setBaseHeight(0.175F)
                                .setHeightVariation(0.07F),
                        MidnightBiomeConfigs.FUNGI_FOREST_CONFIG
                )),
                RegUtil.applyName(new MoltenCraterBiome(
                        new Biome.BiomeProperties("molten_crater")
                                .setBaseHeight(-1.7F)
                                .setHeightVariation(0.0F),
                        MidnightBiomeConfigs.MOLTEN_CRATER_CONFIG
                )),
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("molten_crater_edge")
                                .setBaseHeight(-0.2F)
                                .setHeightVariation(0.05F),
                        MidnightBiomeConfigs.MOLTEN_CRATER_CONFIG
                )),
                RegUtil.applyName(new MidnightBiome(
                        new Biome.BiomeProperties("obscured_peaks")
                                .setBaseHeight(5.0F)
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
                        new Biome.BiomeProperties("crystal_plains")
                                .setBaseHeight(0.1F)
                                .setHeightVariation(0.12F),
                        MidnightBiomeConfigs.CRYSTAL_PLAINS_CONFIG
                ))
        );
    }

    public static void onInit() {
        addSeedBiomes(VIGILANT_FOREST);
    }

    public static void addSeedBiomes(Biome... biomes) {
        SEED_BIOMES.addAll(Arrays.asList(biomes));
    }

    public static List<Biome> getSeedBiomes() {
        return Collections.unmodifiableList(SEED_BIOMES);
    }
}
