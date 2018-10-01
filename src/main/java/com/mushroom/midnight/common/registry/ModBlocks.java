package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.blocks.*;
import com.mushroom.midnight.common.blocks.BlockEbonysBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

@GameRegistry.ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class ModBlocks {

    static List<Block> blocks;

    public static final Block SHADOWROOT_LOG = Blocks.AIR;
    public static final Block SHADOWROOT_LEAF = Blocks.AIR;
    public static final Block SHADOWROOT_PLANK = Blocks.AIR;
    public static final Block DEAD_WOOD_LOG = Blocks.AIR;
    public static final Block DEAD_WOOD_PLANK = Blocks.AIR;
    public static final Block DARK_WILLOW_LOG = Blocks.AIR;
    public static final Block DARK_WILLOW_PLANK = Blocks.AIR;
    public static final Block NIGHTSTONE = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_CHISELED = Blocks.AIR;
    public static final Block DARK_PEARL_ORE = Blocks.AIR;
    public static final Block DARK_PEARL_BLOCK = Blocks.AIR;
    public static final Block EBONYS_ORE = Blocks.AIR;
    public static final Block EBONYS_BLOCK = Blocks.AIR;
    public static final Block NAGRILITE_ORE = Blocks.AIR;
    public static final Block NAGRILITE_BLOCK = Blocks.AIR;
    public static final Block TENEBRUM_ORE = Blocks.AIR;
    public static final Block TENEBRUM_BLOCK = Blocks.AIR;
    public static final Block RIFT_BLOCK = Blocks.AIR;

    public static final Block SHADOWROOT_CRAFTING_TABLE = Blocks.AIR;
    public static final Block SHADOWROOT_CHEST = Blocks.AIR;
    public static final Block MIDNIGHT_FURNACE = Blocks.AIR;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        blocks = Lists.newArrayList(
                new BlockShadowroot("shadowroot_log"),
                new BlockShadowrootLeaf("shadowroot_leaf"),
                new BlockShadowrootPlank("shadowroot_plank"),
                new BlockDeadWood("dead_wood_log"),
                new BlockDeadPlank("dead_wood_plank"),
                new BlockDarkWillowLog("dark_willow_log"),
                new BlockDarkWillowPlank("dark_willow_plank"),
                new BlockNightstone("nightstone"),
                new BlockNightstoneBrick("nightstone_brick"),
                new BlockChiseledNightstoneBrick("nightstone_brick_chiseled"),
                new BlockDarkPearlOre("dark_pearl_ore"),
                new BlockDarkPearlBlock("dark_pearl_block"),
                new BlockEbonysOre("ebonys_ore"),
                new BlockEbonysBlock("ebonys_block"),
                new BlockNagriliteOre("nagrilite_ore"),
                new BlockNagriliteBlock("nagrilite_block"),
                new BlockTenebrumOre("tenebrum_ore"),
                new BlockTenebrumBlock("tenebrum_block"),
                new BlockShadowrootCraftingTable("shadowroot_crafting_table"),
                new BlockShadowrootChest("shadowroot_chest"),
                new BlockMidnightFurnace("midnight_furnace"),
                new BlockRiftBlock("rift_block")
        );

        //registerTile(TileStatue.class, "statue");

        event.getRegistry().registerAll(blocks.toArray(new Block[0]));
    }

    private static void registerTile(Class classs, String registryName) {
        GameRegistry.registerTileEntity(classs, Midnight.MODID + ":tile_" + registryName);
    }

}