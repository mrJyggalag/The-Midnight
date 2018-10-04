package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.blocks.BlockBasic;
import com.mushroom.midnight.common.blocks.BlockMidnightFurnace;
import com.mushroom.midnight.common.blocks.BlockMidnightGrass;
import com.mushroom.midnight.common.blocks.BlockMidnightLeaves;
import com.mushroom.midnight.common.blocks.BlockMidnightLog;
import com.mushroom.midnight.common.blocks.BlockShadowrootChest;
import com.mushroom.midnight.common.blocks.BlockShadowrootCraftingTable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@GameRegistry.ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class ModBlocks {

    static List<Block> blocks;

    public static final Block SHADOWROOT_LOG = Blocks.AIR;
    public static final Block SHADOWROOT_LEAVES = Blocks.AIR;
    public static final Block SHADOWROOT_PLANKS = Blocks.AIR;
    public static final Block DEAD_WOOD_LOG = Blocks.AIR;
    public static final Block DEAD_WOOD_PLANKS = Blocks.AIR;
    public static final Block DARK_WILLOW_LOG = Blocks.AIR;
    public static final Block DARK_WILLOW_PLANKS = Blocks.AIR;
    public static final Block NIGHTSTONE = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICKS = Blocks.AIR;
    public static final Block CHISELED_NIGHTSTONE_BRICKS = Blocks.AIR;
    public static final Block DARK_PEARL_ORE = Blocks.AIR;
    public static final Block DARK_PEARL_BLOCK = Blocks.AIR;
    public static final Block EBONYS_ORE = Blocks.AIR;
    public static final Block EBONYS_BLOCK = Blocks.AIR;
    public static final Block NAGRILITE_ORE = Blocks.AIR;
    public static final Block NAGRILITE_BLOCK = Blocks.AIR;
    public static final Block TENEBRUM_ORE = Blocks.AIR;
    public static final Block TENEBRUM_BLOCK = Blocks.AIR;

    public static final Block SHADOWROOT_CRAFTING_TABLE = Blocks.AIR;
    public static final Block SHADOWROOT_CHEST = Blocks.AIR;
    public static final Block MIDNIGHT_FURNACE = Blocks.AIR;

    public static final Block MIDNIGHT_DIRT = Blocks.AIR;
    public static final Block MIDNIGHT_GRASS = Blocks.AIR;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        blocks = Lists.newArrayList(
                RegUtil.withName(new BlockMidnightLog(), "shadowroot_log"),
                RegUtil.withName(new BlockMidnightLeaves(), "shadowroot_leaves"),
                RegUtil.withName(new BlockBasic(Material.WOOD), "shadowroot_planks")
                        .setSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightLog(), "dead_wood_log"),
                RegUtil.withName(new BlockBasic(Material.WOOD), "dead_wood_planks")
                        .setSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightLog(), "dark_willow_log"),
                RegUtil.withName(new BlockBasic(Material.WOOD), "dark_willow_planks")
                        .setSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockBasic(Material.ROCK), "nightstone"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "nightstone_bricks"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "chiseled_nightstone_bricks"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "dark_pearl_ore"),
                RegUtil.withName(new BlockBasic(Material.IRON), "dark_pearl_block"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "ebonys_ore"),
                RegUtil.withName(new BlockBasic(Material.IRON), "ebonys_block"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "nagrilite_ore"),
                RegUtil.withName(new BlockBasic(Material.IRON), "nagrilite_block"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "tenebrum_ore"),
                RegUtil.withName(new BlockBasic(Material.IRON), "tenebrum_block"),
                RegUtil.withName(new BlockShadowrootCraftingTable(), "shadowroot_crafting_table"),
                RegUtil.withName(new BlockShadowrootChest(), "shadowroot_chest"),
                RegUtil.withName(new BlockMidnightFurnace(), "midnight_furnace"),
                RegUtil.withName(new BlockBasic(Material.GROUND).setSoundType(SoundType.GROUND), "midnight_dirt"),
                RegUtil.withName(new BlockMidnightGrass(), "midnight_grass")
        );

        blocks.forEach(event.getRegistry()::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(itemBlocks(
                SHADOWROOT_LOG, SHADOWROOT_LEAVES, SHADOWROOT_PLANKS,
                DEAD_WOOD_LOG, DEAD_WOOD_PLANKS,
                DARK_WILLOW_LOG, DARK_WILLOW_PLANKS,
                NIGHTSTONE, NIGHTSTONE_BRICKS, CHISELED_NIGHTSTONE_BRICKS,
                DARK_PEARL_ORE, DARK_PEARL_BLOCK,
                EBONYS_ORE, EBONYS_BLOCK,
                NAGRILITE_ORE, NAGRILITE_BLOCK,
                TENEBRUM_ORE, TENEBRUM_BLOCK,
                SHADOWROOT_CRAFTING_TABLE,
                SHADOWROOT_CHEST,
                MIDNIGHT_FURNACE,
                MIDNIGHT_DIRT, MIDNIGHT_GRASS
        ));
    }

    private static Item[] itemBlocks(Block... blocks) {
        Item[] items = new Item[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            items[i] = itemBlock(blocks[i]);
        }
        return items;
    }

    private static Item itemBlock(Block block) {
        ItemBlock item = new ItemBlock(block);
        if (block.getRegistryName() == null) {
            throw new IllegalArgumentException("Cannot create ItemBlock for Block without registry name");
        }
        item.setRegistryName(block.getRegistryName());
        return item;
    }

    private static void registerTile(Class<? extends TileEntity> entityClass, String registryName) {
        GameRegistry.registerTileEntity(entityClass, new ResourceLocation(Midnight.MODID, registryName));
    }

    public static Collection<Block> getBlocks() {
        return Collections.unmodifiableCollection(blocks);
    }
}
