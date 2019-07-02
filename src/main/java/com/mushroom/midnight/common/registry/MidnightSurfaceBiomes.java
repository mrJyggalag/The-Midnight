package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BiomeSpawnEntry;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import com.mushroom.midnight.common.biome.surface.BlackRidgeBiome;
import com.mushroom.midnight.common.biome.surface.CrystalSpiresBiome;
import com.mushroom.midnight.common.biome.surface.DeceitfulBogBiome;
import com.mushroom.midnight.common.biome.surface.FungiForestBiome;
import com.mushroom.midnight.common.biome.surface.HillyFungiForestBiome;
import com.mushroom.midnight.common.biome.surface.HillyVigilantForestBiome;
import com.mushroom.midnight.common.biome.surface.NightPlainsBiome;
import com.mushroom.midnight.common.biome.surface.ObscuredPeaksBiome;
import com.mushroom.midnight.common.biome.surface.ObscuredPlateauBiome;
import com.mushroom.midnight.common.biome.surface.PhantasmalValleyBiome;
import com.mushroom.midnight.common.biome.surface.RunebushGroveBiome;
import com.mushroom.midnight.common.biome.surface.VigilantForestBiome;
import com.mushroom.midnight.common.biome.surface.WarpedFieldsBiome;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Map;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        // TODO: Thank you Forge, very cool
        MidnightPlacements.registerPlacements(ForgeRegistries.DECORATORS);
        MidnightFeatures.registerFeatures(ForgeRegistries.FEATURES);
        MidnightCarvers.registerCarvers(ForgeRegistries.WORLD_CARVERS);

        RegUtil.generic(event.getRegistry())
                .add("vigilant_forest", new VigilantForestBiome())
                .add("black_ridge", new BlackRidgeBiome())
                .add("deceitful_bog", new DeceitfulBogBiome())
                .add("fungi_forest", new FungiForestBiome())
                .add("obscured_peaks", new ObscuredPeaksBiome())
                .add("warped_fields", new WarpedFieldsBiome())
                .add("crystal_spires", new CrystalSpiresBiome())
                .add("night_plains", new NightPlainsBiome())
                .add("obscured_plateau", new ObscuredPlateauBiome())
                .add("phantasmal_valley", new PhantasmalValleyBiome())
                .add("runebush_grove", new RunebushGroveBiome())
                .add("hilly_vigilant_forest", new HillyVigilantForestBiome())
                .add("hilly_fungi_forest", new HillyFungiForestBiome());
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

    public static Stream<Biome> allBiomes() {
        return ForgeRegistries.BIOMES.getEntries().stream()
                .filter(entry -> entry.getKey().getNamespace().equals(Midnight.MODID))
                .map(Map.Entry::getValue);
    }
}
