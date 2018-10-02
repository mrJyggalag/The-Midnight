package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biomes.BiomeBlackRidge;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
@GameRegistry.ObjectHolder(Midnight.MODID)
public class ModBiomes {
    public static final Biome ROCKY_TEST = Biomes.DEFAULT;

    @SubscribeEvent
    public static void onRegisterBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(
                RegUtil.applyName(new BiomeBlackRidge(
                        new Biome.BiomeProperties("rocky_test")
                                .setBaseHeight(0.125F)
                                .setHeightVariation(0.05F)
                ))
        );
    }
}
