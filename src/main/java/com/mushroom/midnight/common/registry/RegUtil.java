package com.mushroom.midnight.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegUtil {
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static <T> T injected() {
        return null;
    }

    public static <T extends IForgeRegistryEntry<T>> Generic<T> generic(IForgeRegistry<T> registry) {
        return new Generic<>(registry);
    }

    public static Blocks blocks(IForgeRegistry<Block> registry) {
        return new Blocks(registry);
    }

    public static Items items(IForgeRegistry<Item> registry) {
        return new Items(registry);
    }

    public static class Items {
        private final IForgeRegistry<Item> registry;
        private Supplier<Item.Properties> propertiesSupplier = Item.Properties::new;

        private Items(IForgeRegistry<Item> registry) {
            this.registry = registry;
        }

        public Items withProperties(Supplier<Item.Properties> propertiesSupplier) {
            this.propertiesSupplier = propertiesSupplier;
            return this;
        }

        public Items add(String name, Item item) {
            ResourceLocation registryName = GameData.checkPrefix(name, false);
            item.setRegistryName(registryName);

            this.registry.register(item);

            return this;
        }

        public Items add(String name, Function<Item.Properties, Item> function) {
            Item item = function.apply(this.propertiesSupplier.get());
            return this.add(name, item);
        }

        public Items add(Block block, BiFunction<Block, Item.Properties, Item> function) {
            Item item = function.apply(block, this.propertiesSupplier.get());
            item.setRegistryName(block.getRegistryName());

            this.registry.register(item);

            return this;
        }
    }

    public static class Blocks {
        private final IForgeRegistry<Block> registry;

        private Blocks(IForgeRegistry<Block> registry) {
            this.registry = registry;
        }

        public Blocks add(String name, Block block) {
            ResourceLocation registryName = GameData.checkPrefix(name, false);
            block.setRegistryName(registryName);

            this.registry.register(block);

            return this;
        }
    }

    public static class Generic<T extends IForgeRegistryEntry<T>> {
        private final IForgeRegistry<T> registry;

        private Generic(IForgeRegistry<T> registry) {
            this.registry = registry;
        }

        public Generic<T> add(String name, T entry) {
            ResourceLocation registryName = GameData.checkPrefix(name, false);
            entry.setRegistryName(registryName);

            this.registry.register(entry);

            return this;
        }
    }
}
