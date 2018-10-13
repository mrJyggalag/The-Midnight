package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.BlockBasic;
import com.mushroom.midnight.common.block.BlockCrystal;
import com.mushroom.midnight.common.block.BlockDoubleMidnightPlant;
import com.mushroom.midnight.common.block.BlockGlowingDoublePlant;
import com.mushroom.midnight.common.block.BlockGlowingPlant;
import com.mushroom.midnight.common.block.BlockMiasmaFluid;
import com.mushroom.midnight.common.block.BlockMiasmaSurface;
import com.mushroom.midnight.common.block.BlockMidnightDirt;
import com.mushroom.midnight.common.block.BlockMidnightDoor;
import com.mushroom.midnight.common.block.BlockMidnightFurnace;
import com.mushroom.midnight.common.block.BlockMidnightGem;
import com.mushroom.midnight.common.block.BlockMidnightGrass;
import com.mushroom.midnight.common.block.BlockMidnightLeaves;
import com.mushroom.midnight.common.block.BlockMidnightLog;
import com.mushroom.midnight.common.block.BlockMidnightPlant;
import com.mushroom.midnight.common.block.BlockMidnightSapling;
import com.mushroom.midnight.common.block.BlockMidnightTrapDoor;
import com.mushroom.midnight.common.block.BlockNightstone;
import com.mushroom.midnight.common.block.BlockShadowrootChest;
import com.mushroom.midnight.common.block.BlockShadowrootCraftingTable;
import com.mushroom.midnight.common.tile.base.TileEntityShadowrootChest;
import com.mushroom.midnight.common.world.generator.WorldGenMidnightTree;
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
import net.minecraftforge.oredict.OreDictionary;

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
    public static final Block DARK_WILLOW_LEAVES = Blocks.AIR;
    public static final Block DARK_WILLOW_PLANKS = Blocks.AIR;
    public static final Block NIGHTSTONE = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICKS = Blocks.AIR;
    public static final Block CHISELED_NIGHTSTONE_BRICKS = Blocks.AIR;
    public static final Block DARK_PEARL_ORE = Blocks.AIR;
    public static final Block DARK_PEARL_BLOCK = Blocks.AIR;

    public static final Block SHADOWROOT_CRAFTING_TABLE = Blocks.AIR;
    public static final Block SHADOWROOT_CHEST = Blocks.AIR;
    public static final Block MIDNIGHT_FURNACE = Blocks.AIR;

    public static final Block MIDNIGHT_DIRT = Blocks.AIR;
    public static final Block MIDNIGHT_GRASS = Blocks.AIR;

    public static final Block TALL_MIDNIGHT_GRASS = Blocks.AIR;
    public static final Block DOUBLE_MIDNIGHT_GRASS = Blocks.AIR;

    public static final Block LUMEN_BUD = Blocks.AIR;
    public static final Block DOUBLE_LUMEN_BUD = Blocks.AIR;

    public static final Block SHADOWROOT_SAPLING = Blocks.AIR;
    public static final Block DARK_WILLOW_SAPLING = Blocks.AIR;

    public static final Block SHADOWROOT_DOOR = Blocks.AIR;
    public static final Block DEAD_WOOD_DOOR = Blocks.AIR;
    public static final Block DARK_WILLOW_DOOR = Blocks.AIR;

    public static final Block SHADOWROOT_TRAPDOOR = Blocks.AIR;
    public static final Block DEAD_WOOD_TRAPDOOR = Blocks.AIR;
    public static final Block DARK_WILLOW_TRAPDOOR = Blocks.AIR;

    public static final Block CRYSTAL = Blocks.AIR;
    public static final Block CRYSTAL_ROCK = Blocks.AIR;

    public static final Block MIASMA_SURFACE = Blocks.AIR;
    public static final Block MIASMA = Blocks.AIR;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        blocks = Lists.newArrayList(
                RegUtil.withName(new BlockMidnightGrass(), "midnight_grass"),
                RegUtil.withName(new BlockMidnightDirt(), "midnight_dirt"),
                RegUtil.withName(new BlockNightstone(), "nightstone"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "nightstone_bricks"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "chiseled_nightstone_bricks"),
                RegUtil.withName(new BlockMidnightLog(), "shadowroot_log"),
                RegUtil.withName(new BlockMidnightLeaves(() -> SHADOWROOT_SAPLING), "shadowroot_leaves"),
                RegUtil.withName(new BlockBasic(Material.WOOD), "shadowroot_planks")
                        .withSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightLog(), "dead_wood_log"),
                RegUtil.withName(new BlockBasic(Material.WOOD), "dead_wood_planks")
                        .withSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockMidnightLog(), "dark_willow_log"),
                RegUtil.withName(new BlockMidnightLeaves(() -> DARK_WILLOW_SAPLING), "dark_willow_leaves"),
                RegUtil.withName(new BlockBasic(Material.WOOD), "dark_willow_planks")
                        .withSoundType(SoundType.WOOD)
                        .setHardness(2.0F)
                        .setResistance(5.0F),
                RegUtil.withName(new BlockCrystal(), "crystal"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "crystal_rock")
                        .withSoundType(SoundType.GLASS)
                        .withHarvestLevel("pickaxe", 1)
                        .setLightLevel(1.0F)
                        .setHardness(4.0F),
                RegUtil.withName(new BlockMiasmaSurface(), "miasma_surface"),
                RegUtil.withName(new BlockMiasmaFluid(), "miasma"),
                RegUtil.withName(new BlockMidnightGem(() -> ModItems.DARK_PEARL), "dark_pearl_ore"),
                RegUtil.withName(new BlockBasic(Material.IRON), "dark_pearl_block"),
                RegUtil.withName(new BlockMidnightPlant(), "tall_midnight_grass"),
                RegUtil.withName(new BlockDoubleMidnightPlant(), "double_midnight_grass"),
                RegUtil.withName(new BlockGlowingPlant(), "lumen_bud"),
                RegUtil.withName(new BlockGlowingDoublePlant(), "double_lumen_bud"),
                RegUtil.withName(new BlockMidnightSapling(() -> new WorldGenMidnightTree(SHADOWROOT_LOG, SHADOWROOT_LEAVES, 6)), "shadowroot_sapling"),
                RegUtil.withName(new BlockMidnightSapling(() -> new WorldGenMidnightTree(DARK_WILLOW_LOG, DARK_WILLOW_LEAVES, 6)), "dark_willow_sapling"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.SHADOWROOT_DOOR), "shadowroot_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.DARK_WILLOW_DOOR), "dark_willow_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.DEAD_WOOD_DOOR), "dead_wood_door"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "shadowroot_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "dark_willow_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "dead_wood_trapdoor"),
                RegUtil.withName(new BlockShadowrootCraftingTable(), "shadowroot_crafting_table"),
                RegUtil.withName(new BlockShadowrootChest(), "shadowroot_chest"),
                RegUtil.withName(new BlockMidnightFurnace(), "midnight_furnace")
        );

        blocks.forEach(event.getRegistry()::register);

        registerTile(TileEntityShadowrootChest.class, "tile_shadowroot_chest");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(itemBlocks(
                SHADOWROOT_LOG, SHADOWROOT_LEAVES, SHADOWROOT_PLANKS,
                DEAD_WOOD_LOG, DEAD_WOOD_PLANKS,
                DARK_WILLOW_LOG, DARK_WILLOW_LEAVES, DARK_WILLOW_PLANKS,
                NIGHTSTONE, NIGHTSTONE_BRICKS, CHISELED_NIGHTSTONE_BRICKS,
                DARK_PEARL_ORE, DARK_PEARL_BLOCK,
                SHADOWROOT_CRAFTING_TABLE,
                SHADOWROOT_CHEST,
                MIDNIGHT_FURNACE,
                MIDNIGHT_DIRT, MIDNIGHT_GRASS,
                TALL_MIDNIGHT_GRASS, DOUBLE_MIDNIGHT_GRASS,
                LUMEN_BUD, DOUBLE_LUMEN_BUD,
                SHADOWROOT_SAPLING, DARK_WILLOW_SAPLING,
                SHADOWROOT_TRAPDOOR, DARK_WILLOW_TRAPDOOR, DEAD_WOOD_TRAPDOOR,
                CRYSTAL, CRYSTAL_ROCK,
                MIASMA_SURFACE, MIASMA
        ));
    }

    public static void onInit() {
        OreDictionary.registerOre("logWood", SHADOWROOT_LOG);
        OreDictionary.registerOre("logWood", DARK_WILLOW_LOG);
        OreDictionary.registerOre("logWood", DEAD_WOOD_LOG);

        OreDictionary.registerOre("plankWood", SHADOWROOT_PLANKS);
        OreDictionary.registerOre("plankWood", DARK_WILLOW_PLANKS);
        OreDictionary.registerOre("plankWood", DEAD_WOOD_PLANKS);

        OreDictionary.registerOre("treeLeaves", SHADOWROOT_LEAVES);
        OreDictionary.registerOre("treeLeaves", DARK_WILLOW_LEAVES);

        /*OreDictionary.registerOre("oreEbonys", EBONYS_ORE);
        OreDictionary.registerOre("blockEbonys", EBONYS_BLOCK);

        OreDictionary.registerOre("oreNagrilite", NAGRILITE_ORE);
        OreDictionary.registerOre("blockNagrilite", NAGRILITE_BLOCK);

        OreDictionary.registerOre("oreTenebrum", TENEBRUM_ORE);
        OreDictionary.registerOre("blockTenebrum", TENEBRUM_BLOCK);*/

        OreDictionary.registerOre("chest", SHADOWROOT_CHEST);
        OreDictionary.registerOre("chestWood", SHADOWROOT_CHEST);
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
