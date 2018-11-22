package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.item.ItemBasic;
import com.mushroom.midnight.common.item.ItemCookSuavis;
import com.mushroom.midnight.common.item.ItemMidnightDoor;
import com.mushroom.midnight.common.item.ItemRawSuavis;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@GameRegistry.ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class ModItems {
    public static final Item SHADOWROOT_DOOR = Items.AIR;
    public static final Item DARK_WILLOW_DOOR = Items.AIR;
    public static final Item DEAD_WOOD_DOOR = Items.AIR;

    public static final Item NIGHTSHROOM_DOOR = Items.AIR;
    public static final Item DEWSHROOM_DOOR = Items.AIR;
    public static final Item VIRIDSHROOM_DOOR = Items.AIR;

    public static final Item GEODE = Items.AIR;
    public static final Item DARK_PEARL = Items.AIR;
    public static final Item DARK_STICK = Items.AIR;
    public static final Item ROCKSHROOM_CLUMP = Items.AIR;
    public static final Item TENEBRUM_INGOT = Items.AIR;
    public static final Item TENEBRUM_NUGGET = Items.AIR;

    public static final Item RAW_SUAVIS = Items.AIR;
    public static final Item COOK_SUAVIS = Items.AIR;

    static List<Item> items;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        items = Lists.newArrayList(
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.SHADOWROOT_DOOR), "shadowroot_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.DARK_WILLOW_DOOR), "dark_willow_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.DEAD_WOOD_DOOR), "dead_wood_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.NIGHTSHROOM_DOOR), "nightshroom_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.DEWSHROOM_DOOR), "dewshroom_door"),
                RegUtil.withName(new ItemMidnightDoor(ModBlocks.VIRIDSHROOM_DOOR), "viridshroom_door"),
                RegUtil.withName(new ItemBasic(), "geode"),
                RegUtil.withName(new ItemBasic(), "dark_pearl"),
                RegUtil.withName(new ItemBasic(), "dark_stick"),
                RegUtil.withName(new ItemBasic(), "rockshroom_clump"),
                RegUtil.withName(new ItemBasic(), "tenebrum_ingot"),
                RegUtil.withName(new ItemBasic(), "tenebrum_nugget"),
                RegUtil.withName(new ItemRawSuavis(), "raw_suavis"),
                RegUtil.withName(new ItemCookSuavis(), "cook_suavis")
        );

        items.forEach(event.getRegistry()::register);
    }

    public static void onInit() {
        OreDictionary.registerOre("doorWood", SHADOWROOT_DOOR);
        OreDictionary.registerOre("doorWood", DARK_WILLOW_DOOR);
        OreDictionary.registerOre("doorWood", DEAD_WOOD_DOOR);
    }

    public static Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items);
    }
}
