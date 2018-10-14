package com.mushroom.midnight.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.GameData;

public class RegUtil {
    public static <T extends Block> T withName(T block, String name) {
        ResourceLocation registryName = GameData.checkPrefix(name);
        block.setRegistryName(registryName);
        block.setTranslationKey(registryName.getNamespace() + "." + registryName.getPath());
        return block;
    }

    public static <T extends Item> T withName(T item, String name) {
        ResourceLocation registryName = GameData.checkPrefix(name);
        item.setRegistryName(registryName);
        item.setTranslationKey(registryName.getNamespace() + "." + registryName.getPath());
        return item;
    }

    public static <T extends Potion> T withName(T potion, String name) {
        ResourceLocation registryName = GameData.checkPrefix(name);
        potion.setRegistryName(registryName);
        potion.setPotionName(registryName.getNamespace() + "." + registryName.getPath());
        return potion;
    }

    public static <T extends Biome> T applyName(T biome) {
        String name = ReflectionHelper.getPrivateValue(Biome.class, biome, "biomeName", "field_76791_y");
        ResourceLocation registryName = GameData.checkPrefix(name);
        biome.setRegistryName(registryName);
        return biome;
    }

    public static <T extends SoundEvent> T applyName(T sound) {
        ResourceLocation name = ReflectionHelper.getPrivateValue(SoundEvent.class, sound, "soundName", "field_187506_b");
        sound.setRegistryName(name);
        return sound;
    }
}
