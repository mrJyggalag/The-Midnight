package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.BladeshroomBlock;
import com.mushroom.midnight.common.block.BloomCrystalBlock;
import com.mushroom.midnight.common.block.BogweedBlock;
import com.mushroom.midnight.common.block.BulbFungusBlock;
import com.mushroom.midnight.common.block.BulbFungusHatBlock;
import com.mushroom.midnight.common.block.BulbFungusStemBlock;
import com.mushroom.midnight.common.block.CrystalBlock;
import com.mushroom.midnight.common.block.CrystalotusBlock;
import com.mushroom.midnight.common.block.DarkWaterBlock;
import com.mushroom.midnight.common.block.DeceitfulAlgaeBlock;
import com.mushroom.midnight.common.block.DeceitfulMossBlock;
import com.mushroom.midnight.common.block.DeceitfulMudBlock;
import com.mushroom.midnight.common.block.DragonNestBlock;
import com.mushroom.midnight.common.block.FingeredGrassBlock;
import com.mushroom.midnight.common.block.GhostPlantBlock;
import com.mushroom.midnight.common.block.LumenBudBlock;
import com.mushroom.midnight.common.block.MiasmaFluidBlock;
import com.mushroom.midnight.common.block.MiasmaSurfaceBlock;
import com.mushroom.midnight.common.block.MidnightChestBlock;
import com.mushroom.midnight.common.block.MidnightChestBlock.ChestModel;
import com.mushroom.midnight.common.block.MidnightDirtBlock;
import com.mushroom.midnight.common.block.MidnightDoubleFungiBlock;
import com.mushroom.midnight.common.block.MidnightDoublePlantBlock;
import com.mushroom.midnight.common.block.MidnightFenceBlock;
import com.mushroom.midnight.common.block.MidnightFenceGateBlock;
import com.mushroom.midnight.common.block.MidnightFungiBlock;
import com.mushroom.midnight.common.block.MidnightFungiHatBlock;
import com.mushroom.midnight.common.block.MidnightFungiShelfBlock;
import com.mushroom.midnight.common.block.MidnightFungiStemBlock;
import com.mushroom.midnight.common.block.MidnightFurnaceBlock;
import com.mushroom.midnight.common.block.MidnightGemBlock;
import com.mushroom.midnight.common.block.MidnightGlassBlock;
import com.mushroom.midnight.common.block.MidnightGlassPaneBlock;
import com.mushroom.midnight.common.block.MidnightGrassBlock;
import com.mushroom.midnight.common.block.MidnightMyceliumBlock;
import com.mushroom.midnight.common.block.MidnightOreBlock;
import com.mushroom.midnight.common.block.MidnightPlantBlock;
import com.mushroom.midnight.common.block.MidnightTallGrassBlock;
import com.mushroom.midnight.common.block.MidnightWoodPlankBlock;
import com.mushroom.midnight.common.block.MushroomInsideBlock;
import com.mushroom.midnight.common.block.NightstoneBlock;
import com.mushroom.midnight.common.block.RockshroomBlock;
import com.mushroom.midnight.common.block.ShadowrootCraftingTableBlock;
import com.mushroom.midnight.common.block.SporchBlock;
import com.mushroom.midnight.common.block.StingerEggBlock;
import com.mushroom.midnight.common.block.SuavisBlock;
import com.mushroom.midnight.common.block.TendrilweedBlock;
import com.mushroom.midnight.common.block.UnstableBushBlock;
import com.mushroom.midnight.common.block.UnstableBushBloomedBlock;
import com.mushroom.midnight.common.block.VioleafBlock;
import com.mushroom.midnight.common.item.DeceitfulAlgaeItem;
import com.mushroom.midnight.common.tile.base.TileEntityMidnightChest;
import com.mushroom.midnight.common.tile.base.TileEntityMidnightFurnace;
import com.mushroom.midnight.common.world.feature.DarkWillowTreeFeature;
import com.mushroom.midnight.common.world.feature.LargeBogshroomFeature;
import com.mushroom.midnight.common.world.feature.LargeFungiFeature;
import com.mushroom.midnight.common.world.feature.ShadowrootTreeFeature;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("WeakerAccess")
public class MidnightBlocks {

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
    public static final Block TRENCHSTONE = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICKS = Blocks.AIR;

    public static final Block DARK_PEARL_ORE = Blocks.AIR;
    public static final Block DARK_PEARL_BLOCK = Blocks.AIR;
    public static final Block TENEBRUM_ORE = Blocks.AIR;
    public static final Block TENEBRUM_BLOCK = Blocks.AIR;
    public static final Block NAGRILITE_ORE = Blocks.AIR;
    public static final Block NAGRILITE_BLOCK = Blocks.AIR;
    public static final Block EBONYS_ORE = Blocks.AIR;
    public static final Block EBONYS_BLOCK = Blocks.AIR;
    public static final Block ARCHAIC_ORE = Blocks.AIR;

    public static final Block SHADOWROOT_CRAFTING_TABLE = Blocks.AIR;
    public static final Block SHADOWROOT_CHEST = Blocks.AIR;
    public static final Block NIGHTSTONE_FURNACE = Blocks.AIR;

    public static final Block MIDNIGHT_COARSE_DIRT = Blocks.AIR;
    public static final Block MIDNIGHT_DIRT = Blocks.AIR;
    public static final Block MIDNIGHT_GRASS = Blocks.AIR;
    public static final Block MIDNIGHT_MYCELIUM = Blocks.AIR;

    public static final Block TALL_MIDNIGHT_GRASS = Blocks.AIR;
    public static final Block DOUBLE_MIDNIGHT_GRASS = Blocks.AIR;

    public static final Block NIGHTSHROOM = Blocks.AIR;
    public static final Block DOUBLE_NIGHTSHROOM = Blocks.AIR;
    public static final Block NIGHTSHROOM_SHELF = Blocks.AIR;

    public static final Block DEWSHROOM = Blocks.AIR;
    public static final Block DOUBLE_DEWSHROOM = Blocks.AIR;
    public static final Block DEWSHROOM_SHELF = Blocks.AIR;

    public static final Block DEWSHROOM_PLANKS = Blocks.AIR;

    public static final Block VIRIDSHROOM = Blocks.AIR;
    public static final Block DOUBLE_VIRIDSHROOM = Blocks.AIR;
    public static final Block VIRIDSHROOM_SHELF = Blocks.AIR;

    public static final Block VIRIDSHROOM_PLANKS = Blocks.AIR;

    public static final Block VIRIDSHROOM_STEM = Blocks.AIR;
    public static final Block VIRIDSHROOM_HAT = Blocks.AIR;

    public static final Block NIGHTSHROOM_STEM = Blocks.AIR;
    public static final Block NIGHTSHROOM_HAT = Blocks.AIR;

    public static final Block NIGHTSHROOM_PLANKS = Blocks.AIR;

    public static final Block DEWSHROOM_STEM = Blocks.AIR;
    public static final Block DEWSHROOM_HAT = Blocks.AIR;

    public static final Block BOGSHROOM = Blocks.AIR;
    public static final Block DOUBLE_BOGSHROOM = Blocks.AIR;
    public static final Block BOGSHROOM_SHELF = Blocks.AIR;
    public static final Block BOGSHROOM_STEM = Blocks.AIR;
    public static final Block BOGSHROOM_HAT = Blocks.AIR;

    public static final Block BULB_FUNGUS = Blocks.AIR;
    public static final Block BULB_FUNGUS_STEM = Blocks.AIR;
    public static final Block BULB_FUNGUS_HAT = Blocks.AIR;

    public static final Block ROCKSHROOM = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS = Blocks.AIR;

    public static final Block LUMEN_BUD = Blocks.AIR;
    public static final Block DOUBLE_LUMEN_BUD = Blocks.AIR;

    public static final Block BLADESHROOM = Blocks.AIR;
    public static final Block BOGWEED = Blocks.AIR;
    public static final Block GHOST_PLANT = Blocks.AIR;
    public static final Block FINGERED_GRASS = Blocks.AIR;
    public static final Block TENDRILWEED = Blocks.AIR;
    public static final Block RUNEBUSH = Blocks.AIR;
    public static final Block DRAGON_NEST = Blocks.AIR;
    public static final Block VIOLEAF = Blocks.AIR;

    public static final Block CRYSTAL_FLOWER = Blocks.AIR;

    public static final Block SHADOWROOT_SAPLING = Blocks.AIR;
    public static final Block DARK_WILLOW_SAPLING = Blocks.AIR;

    public static final Block SHADOWROOT_DOOR = Blocks.AIR;
    public static final Block DEAD_WOOD_DOOR = Blocks.AIR;
    public static final Block DARK_WILLOW_DOOR = Blocks.AIR;

    public static final Block TENEBRUM_DOOR = Blocks.AIR;

    public static final Block NIGHTSHROOM_DOOR = Blocks.AIR;
    public static final Block DEWSHROOM_DOOR = Blocks.AIR;
    public static final Block VIRIDSHROOM_DOOR = Blocks.AIR;

    public static final Block SHADOWROOT_TRAPDOOR = Blocks.AIR;
    public static final Block DEAD_WOOD_TRAPDOOR = Blocks.AIR;
    public static final Block DARK_WILLOW_TRAPDOOR = Blocks.AIR;

    public static final Block TENEBRUM_TRAPDOOR = Blocks.AIR;

    public static final Block NIGHTSHROOM_TRAPDOOR = Blocks.AIR;
    public static final Block DEWSHROOM_TRAPDOOR = Blocks.AIR;
    public static final Block VIRIDSHROOM_TRAPDOOR = Blocks.AIR;

    public static final Block BLOOMCRYSTAL = Blocks.AIR;
    public static final Block BLOOMCRYSTAL_ROCK = Blocks.AIR;

    public static final Block ROUXE = Blocks.AIR;
    public static final Block ROUXE_ROCK = Blocks.AIR;

    public static final Block ARCHAIC_GLASS = Blocks.AIR;
    public static final Block ARCHAIC_GLASS_PANE = Blocks.AIR;

    public static final Block MIASMA_SURFACE = Blocks.AIR;
    public static final Block MIASMA = Blocks.AIR;
    public static final Block DARK_WATER = Blocks.AIR;

    public static final Block MUSHROOM_INSIDE = Blocks.AIR;

    public static final Block DECEITFUL_PEAT = Blocks.AIR;
    public static final Block DECEITFUL_MUD = Blocks.AIR;
    public static final Block DECEITFUL_ALGAE = Blocks.AIR;
    public static final Block DECEITFUL_MOSS = Blocks.AIR;

    public static final Block SHADOWROOT_SLAB = Blocks.AIR;
    public static final Block DEAD_WOOD_SLAB = Blocks.AIR;
    public static final Block DARK_WILLOW_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_SLAB = Blocks.AIR;
    public static final Block DEWSHROOM_SLAB = Blocks.AIR;
    public static final Block VIRIDSHROOM_SLAB = Blocks.AIR;
    public static final Block NIGHTSHROOM_SLAB = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_SLAB = Blocks.AIR;

    public static final Block SHADOWROOT_DOUBLE_SLAB = Blocks.AIR;
    public static final Block DEAD_WOOD_DOUBLE_SLAB = Blocks.AIR;
    public static final Block DARK_WILLOW_DOUBLE_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_DOUBLE_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_DOUBLE_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_DOUBLE_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_DOUBLE_SLAB = Blocks.AIR;
    public static final Block DEWSHROOM_DOUBLE_SLAB = Blocks.AIR;
    public static final Block VIRIDSHROOM_DOUBLE_SLAB = Blocks.AIR;
    public static final Block NIGHTSHROOM_DOUBLE_SLAB = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_DOUBLE_SLAB = Blocks.AIR;

    public static final Block SHADOWROOT_STAIRS = Blocks.AIR;
    public static final Block DEAD_WOOD_STAIRS = Blocks.AIR;
    public static final Block DARK_WILLOW_STAIRS = Blocks.AIR;
    public static final Block NIGHTSTONE_STAIRS = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_STAIRS = Blocks.AIR;
    public static final Block TRENCHSTONE_STAIRS = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_STAIRS = Blocks.AIR;
    public static final Block DEWSHROOM_STAIRS = Blocks.AIR;
    public static final Block VIRIDSHROOM_STAIRS = Blocks.AIR;
    public static final Block NIGHTSHROOM_STAIRS = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_STAIRS = Blocks.AIR;

    public static final Block SHADOWROOT_FENCE = Blocks.AIR;
    public static final Block DEAD_WOOD_FENCE = Blocks.AIR;
    public static final Block DARK_WILLOW_FENCE = Blocks.AIR;
    public static final Block NIGHTSTONE_WALL = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_WALL = Blocks.AIR;
    public static final Block TRENCHSTONE_WALL = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_WALL = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_WALL = Blocks.AIR;
    public static final Block DEWSHROOM_FENCE = Blocks.AIR;
    public static final Block VIRIDSHROOM_FENCE = Blocks.AIR;
    public static final Block NIGHTSHROOM_FENCE = Blocks.AIR;

    public static final Block SHADOWROOT_FENCE_GATE = Blocks.AIR;
    public static final Block DEAD_WOOD_FENCE_GATE = Blocks.AIR;
    public static final Block DARK_WILLOW_FENCE_GATE = Blocks.AIR;
    public static final Block DEWSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block VIRIDSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block NIGHTSHROOM_FENCE_GATE = Blocks.AIR;

    public static final Block SUAVIS = Blocks.AIR;

    public static final Block SHADOWROOT_LADDER = Blocks.AIR;
    public static final Block DEAD_WOOD_LADDER = Blocks.AIR;
    public static final Block DARK_WILLOW_LADDER = Blocks.AIR;
    public static final Block DEWSHROOM_LADDER = Blocks.AIR;
    public static final Block VIRIDSHROOM_LADDER = Blocks.AIR;
    public static final Block NIGHTSHROOM_LADDER = Blocks.AIR;

    public static final Block STINGER_EGG = Blocks.AIR;
    public static final Block UNSTABLE_BUSH = Blocks.AIR;
    public static final Block UNSTABLE_BUSH_BLUE_BLOOMED = Blocks.AIR;
    public static final Block UNSTABLE_BUSH_GREEN_BLOOMED = Blocks.AIR;
    public static final Block UNSTABLE_BUSH_LIME_BLOOMED = Blocks.AIR;

    public static final Block BOGSHROOM_SPORCH = Blocks.AIR;
    public static final Block NIGHTSHROOM_SPORCH = Blocks.AIR;
    public static final Block DEWSHROOM_SPORCH = Blocks.AIR;
    public static final Block VIRIDSHROOM_SPORCH = Blocks.AIR;

    public static final Block CRYSTALOTUS = Blocks.AIR;

    public static final Block SHADOWROOT_BUTTON = Blocks.AIR;
    public static final Block DEAD_WOOD_BUTTON = Blocks.AIR;
    public static final Block DARK_WILLOW_BUTTON = Blocks.AIR;
    public static final Block DEWSHROOM_BUTTON = Blocks.AIR;
    public static final Block VIRIDSHROOM_BUTTON = Blocks.AIR;
    public static final Block NIGHTSHROOM_BUTTON = Blocks.AIR;
    public static final Block NIGHTSTONE_BUTTON = Blocks.AIR;
    public static final Block TRENCHSTONE_BUTTON = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_BUTTON = Blocks.AIR;

    public static final Block SHADOWROOT_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DEAD_WOOD_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DARK_WILLOW_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DEWSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block VIRIDSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NIGHTSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NIGHTSTONE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block TRENCHSTONE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NAGRILITE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block TENEBRUM_PRESSURE_PLATE = Blocks.AIR;

    public static final Block MIDNIGHT_LEVER = Blocks.AIR;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
//        event.getRegistry().registerAll(
//                RegUtil.withName(new MidnightSeedItem(() -> MidnightBlocks.BLADESHROOM.getDefaultState()), "bladeshroom_spores"), // TODO
//                RegUtil.withName(new MidnightSeedItem(() -> MidnightBlocks.UNSTABLE_BUSH.getDefaultState()), "unstable_seeds"), // TODO
//        );

        RegUtil.blocks(event.getRegistry())
                .add("midnight_grass", new MidnightGrassBlock(
                        Block.Properties.create(Material.ORGANIC, MaterialColor.MAGENTA_TERRACOTTA)
                                .hardnessAndResistance(0.6F)
                                .sound(SoundType.PLANT)
                                .tickRandomly()
                ))
                .add("midnight_dirt", new MidnightDirtBlock(
                        Block.Properties.create(Material.EARTH, MaterialColor.BLUE_TERRACOTTA)
                                .hardnessAndResistance(0.5F)
                                .sound(SoundType.GROUND)
                ))
                .add("midnight_coarse_dirt", new MidnightDirtBlock(
                        Block.Properties.create(Material.EARTH, MaterialColor.BLUE_TERRACOTTA)
                                .hardnessAndResistance(0.5F)
                                .sound(SoundType.GROUND)
                ))
                .add("midnight_mycelium", new MidnightMyceliumBlock(
                        Block.Properties.create(Material.ROCK, MaterialColor.PINK)
                                .hardnessAndResistance(1.5F, 10.0F)
                                .sound(SoundType.STONE)
                ));

        RegUtil.blocks(event.getRegistry())
                .withProperties(() ->
                        Block.Properties.create(Material.WOOD, MaterialColor.BLUE_TERRACOTTA)
                                .hardnessAndResistance(2.0F)
                                .sound(SoundType.WOOD)
                )
                .add("shadowroot_log", props -> new LogBlock(MaterialColor.BROWN, props))
                .add("dead_wood_log", props -> new LogBlock(MaterialColor.BROWN, props))
                .add("dark_willow_log", props -> new LogBlock(MaterialColor.BROWN, props));

        RegUtil.blocks(event.getRegistry())
                .withProperties(() ->
                        Block.Properties.create(Material.ROCK)
                                .hardnessAndResistance(3.5f)
                                .lightValue(13)
                                .tickRandomly()
                )
                .add("nighstone_furnace", MidnightFurnaceBlock::new);

        // TODO: Port all registration

        blocks.addAll(Lists.newArrayList(
                RegUtil.withName(new BlockMidnightLog(), "shadowroot_log"),
                RegUtil.withName(new MidnightLeavesBlock(() -> SHADOWROOT_SAPLING), "shadowroot_leaves"),
                RegUtil.withName(new MidnightLeavesBlock(() -> DARK_WILLOW_SAPLING), "dark_willow_leaves"),
                RegUtil.withName(new MidnightFungiStemBlock(), "nightshroom_stem"),
                RegUtil.withName(new MidnightFungiHatBlock(() -> NIGHTSHROOM, () -> MidnightItems.NIGHTSHROOM_POWDER, MapColor.CYAN), "nightshroom_hat"),
                RegUtil.withName(new MidnightFungiStemBlock(), "dewshroom_stem"),
                RegUtil.withName(new MidnightFungiHatBlock(() -> DEWSHROOM, () -> MidnightItems.DEWSHROOM_POWDER, MapColor.PURPLE), "dewshroom_hat"),
                RegUtil.withName(new MidnightFungiStemBlock(), "viridshroom_stem"),
                RegUtil.withName(new MidnightFungiHatBlock(() -> VIRIDSHROOM, () -> MidnightItems.VIRIDSHROOM_POWDER, MapColor.EMERALD), "viridshroom_hat"),
                RegUtil.withName(new BulbFungusStemBlock(), "bulb_fungus_stem"),
                RegUtil.withName(new BulbFungusHatBlock(MapColor.MAGENTA), "bulb_fungus_hat"),
                RegUtil.withName(new RockshroomBlock(), "rockshroom"),
                RegUtil.withName(new MidnightTallGrassBlock(), "tall_midnight_grass"),
                RegUtil.withName(new MidnightDoublePlantBlock(PlantBehaviorType.BUSH, false), "double_midnight_grass"),
                RegUtil.withName(new MidnightFungiBlock(() -> DOUBLE_NIGHTSHROOM), "nightshroom"),
                RegUtil.withName(new MidnightDoubleFungiBlock(() -> new LargeFungiFeature(
                        MidnightBlocks.NIGHTSHROOM_STEM.getDefaultState(),
                        MidnightBlocks.NIGHTSHROOM_HAT.getDefaultState()
                )), "double_nightshroom"),
                RegUtil.withName(new MidnightFungiBlock(() -> DOUBLE_DEWSHROOM), "dewshroom"),
                RegUtil.withName(new MidnightDoubleFungiBlock(() -> new LargeFungiFeature(
                        MidnightBlocks.DEWSHROOM_STEM.getDefaultState(),
                        MidnightBlocks.DEWSHROOM_HAT.getDefaultState()
                )), "double_dewshroom"),
                RegUtil.withName(new MidnightFungiBlock(() -> DOUBLE_VIRIDSHROOM), "viridshroom"),
                RegUtil.withName(new MidnightDoubleFungiBlock(() -> new LargeFungiFeature(
                        MidnightBlocks.VIRIDSHROOM_STEM.getDefaultState(),
                        MidnightBlocks.VIRIDSHROOM_HAT.getDefaultState()
                )), "double_viridshroom"),
                RegUtil.withName(new MidnightFungiBlock(() -> DOUBLE_BOGSHROOM), "bogshroom"),
                RegUtil.withName(new MidnightDoubleFungiBlock(LargeBogshroomFeature::new), "double_bogshroom"),
                RegUtil.withName(new MidnightFungiShelfBlock(), "bogshroom_shelf"),
                RegUtil.withName(new MidnightFungiStemBlock(), "bogshroom_stem"),
                RegUtil.withName(new MidnightFungiHatBlock(() -> BOGSHROOM, () -> MidnightItems.BOGSHROOM_POWDER, MapColor.ADOBE), "bogshroom_hat"),
                RegUtil.withName(new BulbFungusBlock(LargeBulbFungusFeature::new), "bulb_fungus"),
                RegUtil.withName(new MidnightFungiShelfBlock(), "nightshroom_shelf"),
                RegUtil.withName(new MidnightFungiShelfBlock(), "dewshroom_shelf"),
                RegUtil.withName(new MidnightFungiShelfBlock(), "viridshroom_shelf"),
                RegUtil.withName(new LumenBudBlock(), "lumen_bud"),
                RegUtil.withName(new MidnightDoublePlantBlock(true), "double_lumen_bud"),
                RegUtil.withName(new BladeshroomBlock(), "bladeshroom"),
                RegUtil.withName(new BogweedBlock(), "bogweed"),
                RegUtil.withName(new GhostPlantBlock(), "ghost_plant"),
                RegUtil.withName(new FingeredGrassBlock(), "fingered_grass"),
                RegUtil.withName(new TendrilweedBlock(), "tendrilweed"),
                RegUtil.withName(new MidnightPlantBlock(false), "runebush"),
                RegUtil.withName(new DragonNestBlock(), "dragon_nest"),
                RegUtil.withName(new VioleafBlock(), "violeaf"),
                RegUtil.withName(new MidnightPlantBlock(true), "crystal_flower"),
                RegUtil.withName(new MidnightSaplingBlock(ShadowrootTreeFeature::new), "shadowroot_sapling"),
                RegUtil.withName(new MidnightSaplingBlock(DarkWillowTreeFeature::new), "dark_willow_sapling"),
                RegUtil.withName(new DarkWaterBlock(), "dark_water"),
                RegUtil.withName(new MushroomInsideBlock(), "mushroom_inside"),
                RegUtil.withName(new DeceitfulMudBlock(), "deceitful_mud"),
                RegUtil.withName(new MidnightDirtBlock(), "deceitful_peat"),
                RegUtil.withName(new DeceitfulAlgaeBlock(), "deceitful_algae"),
                RegUtil.withName(new DeceitfulMossBlock(), "deceitful_moss"),
                RegUtil.withName(new StingerEggBlock(), "stinger_egg"),
                RegUtil.withName(new UnstableBushBlock(), "unstable_bush"),
                RegUtil.withName(new UnstableBushBloomedBlock(() -> MidnightItems.UNSTABLE_FRUIT_BLUE), "unstable_bush_blue_bloomed"),
                RegUtil.withName(new UnstableBushBloomedBlock(() -> MidnightItems.UNSTABLE_FRUIT_GREEN), "unstable_bush_green_bloomed"),
                RegUtil.withName(new UnstableBushBloomedBlock(() -> MidnightItems.UNSTABLE_FRUIT_LIME), "unstable_bush_lime_bloomed"),
                RegUtil.withName(new CrystalotusBlock(), "crystalotus")
        ));

        blocks.addAll(Lists.newArrayList(
                RegUtil.withName(new NightstoneBlock(), "nightstone"), // BLUE_STAINED_HARDENED_CLAY
                RegUtil.withName(new BlockBasic(Material.ROCK), "trenchstone")
                        .withHarvestLevel("pickaxe", 2)
                        .setHardness(5.0F)
                        .setResistance(200.0F),
                RegUtil.withName(new BloomCrystalBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).hardnessAndResistance(2.0F).sound(SoundType.GLASS).lightValue(15).tickRandomly()), "bloomcrystal"),
                RegUtil.withName(new BlockBasic(Material.ROCK, MapColor.PINK), "bloomcrystal_rock")
                        .withSoundType(SoundType.GLASS)
                        .withHarvestLevel("pickaxe", 1)
                        .setLightLevel(1.0F)
                        .setHardness(4.0F),
                RegUtil.withName(new CrystalBlock(Block.Properties.create(Material.ROCK, MaterialColor.RED).hardnessAndResistance(2.0F).sound(SoundType.GLASS).lightValue(3)), "rouxe"),
                RegUtil.withName(new Block(Material.ROCK, MapColor.RED), "rouxe_rock")
                        .withSoundType(SoundType.GLASS)
                        .withHarvestLevel("pickaxe", 1)
                        .withGlow()
                        .setLightLevel(0.2F)
                        .setHardness(4.0F),
                RegUtil.withName(new MiasmaSurfaceBlock(), "miasma_surface"),
                RegUtil.withName(new MiasmaFluidBlock(), "miasma"),
                RegUtil.withName(new MidnightGemBlock(() -> MidnightItems.GEODE, 0), "dark_pearl_ore"),
                RegUtil.withName(new MidnightOreBlock(2), "tenebrum_ore"),
                RegUtil.withName(new MidnightOreBlock(2), "nagrilite_ore"),
                RegUtil.withName(new MidnightGemBlock(() -> MidnightItems.EBONYS, 1), "ebonys_ore"),
                RegUtil.withName(new MidnightGemBlock(() -> MidnightItems.ARCHAIC_SHARD, 0), "archaic_ore")
        ));

        blocks.addAll(Lists.newArrayList(
                RegUtil.withName(new BlockBasic(Material.ROCK), "nightstone_bricks")
                        .setHardness(1.5F)
                        .setResistance(10.0F),
                RegUtil.withName(new BlockBasic(Material.ROCK), "trenchstone_bricks")
                        .withHarvestLevel("pickaxe", 2)
                        .setHardness(5.0F)
                        .setResistance(200.0F),
                RegUtil.withName(new BlockBasic(Material.ROCK), "chiseled_nightstone_bricks")
                        .setHardness(1.5F)
                        .setResistance(10.0F),
                RegUtil.withName(new BlockBasic(Material.ROCK), "rockshroom_bricks")
                        .setHardness(1.5F)
                        .setResistance(10.0F),
                RegUtil.withName(new BlockBasic(Material.IRON), "dark_pearl_block")
                        .withSoundType(SoundType.METAL)
                        .setHardness(3.0F),
                RegUtil.withName(new BlockBasic(Material.IRON), "tenebrum_block")
                        .withSoundType(SoundType.METAL)
                        .setHardness(3.0F),
                RegUtil.withName(new BlockBasic(Material.IRON), "nagrilite_block")
                        .withSoundType(SoundType.METAL)
                        .setHardness(3.0F),
                RegUtil.withName(new BlockBasic(Material.IRON), "ebonys_block")
                        .withSoundType(SoundType.METAL)
                        .setHardness(3.0F),
                RegUtil.withName(new MidnightGlassBlock(), "archaic_glass"),
                RegUtil.withName(new MidnightGlassPaneBlock(), "archaic_glass_pane"),
                RegUtil.withName(new MidnightWoodPlankBlock(), "shadowroot_planks"),
                RegUtil.withName(new MidnightWoodPlankBlock(), "dead_wood_planks"),
                RegUtil.withName(new MidnightWoodPlankBlock(), "dark_willow_planks"),
                RegUtil.withName(new MidnightWoodPlankBlock(), "nightshroom_planks"),
                RegUtil.withName(new MidnightWoodPlankBlock(), "dewshroom_planks"),
                RegUtil.withName(new MidnightWoodPlankBlock(), "viridshroom_planks"),
                RegUtil.withName(new BlockMidnightDoor(() -> MidnightItems.SHADOWROOT_DOOR), "shadowroot_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> MidnightItems.DARK_WILLOW_DOOR), "dark_willow_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> MidnightItems.DEAD_WOOD_DOOR), "dead_wood_door"),
                RegUtil.withName(new BlockMidnightDoor(true, () -> MidnightItems.TENEBRUM_DOOR), "tenebrum_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> MidnightItems.NIGHTSHROOM_DOOR), "nightshroom_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> MidnightItems.DEWSHROOM_DOOR), "dewshroom_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> MidnightItems.VIRIDSHROOM_DOOR), "viridshroom_door"),
                RegUtil.withName(new MidnightTrapDoorBlock(), "shadowroot_trapdoor"),
                RegUtil.withName(new MidnightTrapDoorBlock(), "dark_willow_trapdoor"),
                RegUtil.withName(new MidnightTrapDoorBlock(), "dead_wood_trapdoor"),
                RegUtil.withName(new MidnightTrapDoorBlock(true), "tenebrum_trapdoor"),
                RegUtil.withName(new MidnightTrapDoorBlock(), "nightshroom_trapdoor"),
                RegUtil.withName(new MidnightTrapDoorBlock(), "dewshroom_trapdoor"),
                RegUtil.withName(new MidnightTrapDoorBlock(), "viridshroom_trapdoor"),
                RegUtil.withName(new ShadowrootCraftingTableBlock(), "shadowroot_crafting_table"),
                RegUtil.withName(new MidnightChestBlock(ChestModel.SHADOWROOT), "shadowroot_chest"),
                RegUtil.withName(new MidnightSlabBlock(() -> SHADOWROOT_PLANKS.getDefaultState()), "shadowroot_slab"),
                RegUtil.withName(new MidnightSlabBlock(() -> DEAD_WOOD_PLANKS.getDefaultState()), "dead_wood_slab"),
                RegUtil.withName(new MidnightSlabBlock(() -> DARK_WILLOW_PLANKS.getDefaultState()), "dark_willow_slab"),
                RegUtil.withName(new MidnightSlabBlock(() -> NIGHTSTONE.getDefaultState()), "nightstone_slab"),
                RegUtil.withName(new MidnightSlabBlock(() -> NIGHTSTONE_BRICKS.getDefaultState()), "nightstone_brick_slab"),
                RegUtil.withName(new MidnightSlabBlock(() -> TRENCHSTONE.getDefaultState()), "trenchstone_slab"),
                RegUtil.withName(new MidnightSlabBlock(() -> TRENCHSTONE_BRICKS.getDefaultState()), "trenchstone_brick_slab"),
                RegUtil.withName(new MidnightSlabBlock(() -> DEWSHROOM_PLANKS.getDefaultState()), "dewshroom_slab"),
                RegUtil.withName(new MidnightSlabBlock(() -> VIRIDSHROOM_PLANKS.getDefaultState()), "viridshroom_slab"),
                RegUtil.withName(new MidnightSlabBlock(() -> NIGHTSHROOM_PLANKS.getDefaultState()), "nightshroom_slab"),
                RegUtil.withName(new MidnightSlabBlock(() -> ROCKSHROOM_BRICKS.getDefaultState()), "rockshroom_bricks_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> SHADOWROOT_SLAB), "shadowroot_double_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> DEAD_WOOD_SLAB), "dead_wood_double_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> DARK_WILLOW_SLAB), "dark_willow_double_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> NIGHTSTONE_SLAB), "nightstone_double_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> NIGHTSTONE_BRICK_SLAB), "nightstone_brick_double_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> TRENCHSTONE_SLAB), "trenchstone_double_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> TRENCHSTONE_BRICK_SLAB), "trenchstone_brick_double_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> DEWSHROOM_SLAB), "dewshroom_double_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> VIRIDSHROOM_SLAB), "viridshroom_double_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> NIGHTSHROOM_SLAB), "nightshroom_double_slab"),
                RegUtil.withName(new MidnightDoubleSlabBlock(() -> ROCKSHROOM_BRICKS_SLAB), "rockshroom_bricks_double_slab"),
                RegUtil.withName(new MidnightStairsBlock(() -> SHADOWROOT_PLANKS.getDefaultState()), "shadowroot_stairs"),
                RegUtil.withName(new MidnightStairsBlock(() -> DEAD_WOOD_PLANKS.getDefaultState()), "dead_wood_stairs"),
                RegUtil.withName(new MidnightStairsBlock(() -> DARK_WILLOW_PLANKS.getDefaultState()), "dark_willow_stairs"),
                RegUtil.withName(new MidnightStairsBlock(() -> NIGHTSTONE.getDefaultState()), "nightstone_stairs"),
                RegUtil.withName(new MidnightStairsBlock(() -> NIGHTSTONE_BRICKS.getDefaultState()), "nightstone_brick_stairs"),
                RegUtil.withName(new MidnightStairsBlock(() -> TRENCHSTONE.getDefaultState()), "trenchstone_stairs"),
                RegUtil.withName(new MidnightStairsBlock(() -> TRENCHSTONE_BRICKS.getDefaultState()), "trenchstone_brick_stairs"),
                RegUtil.withName(new MidnightStairsBlock(() -> DEWSHROOM_PLANKS.getDefaultState()), "dewshroom_stairs"),
                RegUtil.withName(new MidnightStairsBlock(() -> VIRIDSHROOM_PLANKS.getDefaultState()), "viridshroom_stairs"),
                RegUtil.withName(new MidnightStairsBlock(() -> NIGHTSHROOM_PLANKS.getDefaultState()), "nightshroom_stairs"),
                RegUtil.withName(new MidnightStairsBlock(() -> ROCKSHROOM_BRICKS.getDefaultState()), "rockshroom_bricks_stairs"),
                RegUtil.withName(new MidnightFenceBlock(() -> SHADOWROOT_PLANKS.getDefaultState()), "shadowroot_fence"),
                RegUtil.withName(new MidnightFenceBlock(() -> DEAD_WOOD_PLANKS.getDefaultState()), "dead_wood_fence"),
                RegUtil.withName(new MidnightFenceBlock(() -> DARK_WILLOW_PLANKS.getDefaultState()), "dark_willow_fence"),
                RegUtil.withName(new MidnightWallBlock(() -> NIGHTSTONE.getDefaultState()), "nightstone_wall"),
                RegUtil.withName(new MidnightWallBlock(() -> NIGHTSTONE_BRICKS.getDefaultState()), "nightstone_brick_wall"),
                RegUtil.withName(new MidnightWallBlock(() -> TRENCHSTONE.getDefaultState()), "trenchstone_wall"),
                RegUtil.withName(new MidnightWallBlock(() -> TRENCHSTONE_BRICKS.getDefaultState()), "trenchstone_brick_wall"),
                RegUtil.withName(new MidnightWallBlock(() -> ROCKSHROOM_BRICKS.getDefaultState()), "rockshroom_bricks_wall"),
                RegUtil.withName(new MidnightFenceBlock(() -> DEWSHROOM_PLANKS.getDefaultState()), "dewshroom_fence"),
                RegUtil.withName(new MidnightFenceBlock(() -> VIRIDSHROOM_PLANKS.getDefaultState()), "viridshroom_fence"),
                RegUtil.withName(new MidnightFenceBlock(() -> NIGHTSHROOM_PLANKS.getDefaultState()), "nightshroom_fence"),
                RegUtil.withName(new MidnightFenceGateBlock(() -> SHADOWROOT_PLANKS.getDefaultState()), "shadowroot_fence_gate"),
                RegUtil.withName(new MidnightFenceGateBlock(() -> DEAD_WOOD_PLANKS.getDefaultState()), "dead_wood_fence_gate"),
                RegUtil.withName(new MidnightFenceGateBlock(() -> DARK_WILLOW_PLANKS.getDefaultState()), "dark_willow_fence_gate"),
                RegUtil.withName(new MidnightFenceGateBlock(() -> DEWSHROOM_PLANKS.getDefaultState()), "dewshroom_fence_gate"),
                RegUtil.withName(new MidnightFenceGateBlock(() -> VIRIDSHROOM_PLANKS.getDefaultState()), "viridshroom_fence_gate"),
                RegUtil.withName(new MidnightFenceGateBlock(() -> NIGHTSHROOM_PLANKS.getDefaultState()), "nightshroom_fence_gate"),
                RegUtil.withName(new SuavisBlock(), "suavis"),
                RegUtil.withName(new MidnightLadderBlock(), "shadowroot_ladder"),
                RegUtil.withName(new MidnightLadderBlock(), "dead_wood_ladder"),
                RegUtil.withName(new MidnightLadderBlock(), "dark_willow_ladder"),
                RegUtil.withName(new MidnightLadderBlock(), "dewshroom_ladder"),
                RegUtil.withName(new MidnightLadderBlock(), "viridshroom_ladder"),
                RegUtil.withName(new MidnightLadderBlock(), "nightshroom_ladder"),
                RegUtil.withName(new SporchBlock(SporchBlock.SporchType.BOGSHROOM), "bogshroom_sporch"),
                RegUtil.withName(new SporchBlock(SporchBlock.SporchType.NIGHTSHROOM), "nightshroom_sporch"),
                RegUtil.withName(new SporchBlock(SporchBlock.SporchType.DEWSHROOM), "dewshroom_sporch"),
                RegUtil.withName(new SporchBlock(SporchBlock.SporchType.VIRIDSHROOM), "viridshroom_sporch"),
                RegUtil.withName(new MidnightButtonBlock(true, 0.75F), "shadowroot_button"),
                RegUtil.withName(new MidnightButtonBlock(true, 0.75F), "dead_wood_button"),
                RegUtil.withName(new MidnightButtonBlock(true, 0.75F), "dark_willow_button"),
                RegUtil.withName(new MidnightButtonBlock(true, 0.75F), "dewshroom_button"),
                RegUtil.withName(new MidnightButtonBlock(true, 0.75F), "viridshroom_button"),
                RegUtil.withName(new MidnightButtonBlock(true, 0.75F), "nightshroom_button"),
                RegUtil.withName(new MidnightButtonBlock(false, 1.0F), "nightstone_button"),
                RegUtil.withName(new MidnightButtonBlock(false, 3.75F), "trenchstone_button"),
                RegUtil.withName(new MidnightButtonBlock(false, 1.0F), "rockshroom_bricks_button"),
                RegUtil.withName(new MidnightPressurePlateBlock(true, 0.75F), "shadowroot_pressure_plate"),
                RegUtil.withName(new MidnightPressurePlateBlock(true, 0.75F), "dead_wood_pressure_plate"),
                RegUtil.withName(new MidnightPressurePlateBlock(true, 0.75F), "dark_willow_pressure_plate"),
                RegUtil.withName(new MidnightPressurePlateBlock(true, 0.75F), "dewshroom_pressure_plate"),
                RegUtil.withName(new MidnightPressurePlateBlock(true, 0.75F), "viridshroom_pressure_plate"),
                RegUtil.withName(new MidnightPressurePlateBlock(true, 0.75F), "nightshroom_pressure_plate"),
                RegUtil.withName(new MidnightPressurePlateBlock(false, 1.0F), "nightstone_pressure_plate"),
                RegUtil.withName(new MidnightPressurePlateBlock(false, 3.75F), "trenchstone_pressure_plate"),
                RegUtil.withName(new MidnightPressurePlateBlock(false, 1.0F), "rockshroom_bricks_pressure_plate"),
                RegUtil.withName(new MidnightPressurePlateWeightedBlock(Material.IRON, true, Material.IRON.getMaterialMapColor(), 2.0F), "nagrilite_pressure_plate"),
                RegUtil.withName(new MidnightPressurePlateWeightedBlock(Material.IRON, false, Material.IRON.getMaterialMapColor(), 2.0F), "tenebrum_pressure_plate"),
                RegUtil.withName(new MidnightLeverBlock(), "midnight_lever")
        ));

        registerTile(TileEntityMidnightChest.class, "tile_shadowroot_chest");
        registerTile(TileEntityMidnightFurnace.class, "tile_midnight_furnace");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        // TODO (and tabs)
        RegUtil.items(event.getRegistry())
                .withProperties(() -> new Item.Properties().group(MidnightItemGroups.BUILDING))
                .add(SHADOWROOT_LOG, BlockItem::new)
                .add(SHADOWROOT_LEAVES, BlockItem::new)
                .add(SHADOWROOT_PLANKS, BlockItem::new)
                .add(DEAD_WOOD_LOG, BlockItem::new);

        registry.registerAll(itemBlocks(
                SHADOWROOT_LOG, SHADOWROOT_LEAVES, SHADOWROOT_PLANKS,
                DEAD_WOOD_LOG, DEAD_WOOD_PLANKS,
                DARK_WILLOW_LOG, DARK_WILLOW_LEAVES, DARK_WILLOW_PLANKS,
                NIGHTSTONE, NIGHTSTONE_BRICKS, CHISELED_NIGHTSTONE_BRICKS,
                TRENCHSTONE, TRENCHSTONE_BRICKS,
                DARK_PEARL_ORE, DARK_PEARL_BLOCK,
                TENEBRUM_ORE, TENEBRUM_BLOCK,
                NAGRILITE_ORE, NAGRILITE_BLOCK,
                EBONYS_ORE, EBONYS_BLOCK,
                ARCHAIC_ORE,
                SHADOWROOT_CRAFTING_TABLE,
                SHADOWROOT_CHEST,
                NIGHTSTONE_FURNACE,
                MIDNIGHT_COARSE_DIRT, MIDNIGHT_DIRT, MIDNIGHT_GRASS, MIDNIGHT_MYCELIUM,
                TALL_MIDNIGHT_GRASS, DOUBLE_MIDNIGHT_GRASS,
                NIGHTSHROOM, DOUBLE_NIGHTSHROOM, NIGHTSHROOM_SHELF,
                DEWSHROOM, DOUBLE_DEWSHROOM, DEWSHROOM_SHELF,
                VIRIDSHROOM, DOUBLE_VIRIDSHROOM, VIRIDSHROOM_SHELF,
                BOGSHROOM, DOUBLE_BOGSHROOM, BOGSHROOM_SHELF, BOGSHROOM_HAT, BOGSHROOM_STEM,
                NIGHTSHROOM_STEM, NIGHTSHROOM_HAT,
                DEWSHROOM_STEM, DEWSHROOM_HAT,
                VIRIDSHROOM_STEM, VIRIDSHROOM_HAT,
                BULB_FUNGUS, BULB_FUNGUS_STEM, BULB_FUNGUS_HAT,
                NIGHTSHROOM_PLANKS, DEWSHROOM_PLANKS, VIRIDSHROOM_PLANKS,
                ROCKSHROOM, ROCKSHROOM_BRICKS,
                LUMEN_BUD, DOUBLE_LUMEN_BUD,
                BOGWEED, GHOST_PLANT, FINGERED_GRASS, TENDRILWEED, RUNEBUSH, DRAGON_NEST,
                VIOLEAF, CRYSTAL_FLOWER,
                SHADOWROOT_SAPLING, DARK_WILLOW_SAPLING,
                SHADOWROOT_TRAPDOOR, DARK_WILLOW_TRAPDOOR, DEAD_WOOD_TRAPDOOR, TENEBRUM_TRAPDOOR,
                NIGHTSHROOM_TRAPDOOR, DEWSHROOM_TRAPDOOR, VIRIDSHROOM_TRAPDOOR,
                BLOOMCRYSTAL, BLOOMCRYSTAL_ROCK,
                ROUXE, ROUXE_ROCK,
                ARCHAIC_GLASS, ARCHAIC_GLASS_PANE,
                MIASMA_SURFACE, MIASMA,
                DARK_WATER,
                DECEITFUL_PEAT, DECEITFUL_MUD, DECEITFUL_MOSS,
                SHADOWROOT_STAIRS, DEAD_WOOD_STAIRS, DARK_WILLOW_STAIRS,
                NIGHTSTONE_STAIRS, NIGHTSTONE_BRICK_STAIRS,
                TRENCHSTONE_STAIRS, TRENCHSTONE_BRICK_STAIRS,
                DEWSHROOM_STAIRS, VIRIDSHROOM_STAIRS, NIGHTSHROOM_STAIRS, ROCKSHROOM_BRICKS_STAIRS,
                NIGHTSTONE_WALL, NIGHTSTONE_BRICK_WALL,
                TRENCHSTONE_WALL, TRENCHSTONE_BRICK_WALL, ROCKSHROOM_BRICKS_WALL,
                SHADOWROOT_FENCE, DEAD_WOOD_FENCE, DARK_WILLOW_FENCE,
                DEWSHROOM_FENCE, VIRIDSHROOM_FENCE, NIGHTSHROOM_FENCE,
                SHADOWROOT_FENCE_GATE, DEAD_WOOD_FENCE_GATE, DARK_WILLOW_FENCE_GATE,
                DEWSHROOM_FENCE_GATE, VIRIDSHROOM_FENCE_GATE, NIGHTSHROOM_FENCE_GATE,
                SUAVIS, SHADOWROOT_LADDER, DEAD_WOOD_LADDER, DARK_WILLOW_LADDER,
                DEWSHROOM_LADDER, VIRIDSHROOM_LADDER, NIGHTSHROOM_LADDER,
                BOGSHROOM_SPORCH, NIGHTSHROOM_SPORCH, DEWSHROOM_SPORCH, VIRIDSHROOM_SPORCH,
                STINGER_EGG, CRYSTALOTUS,
                SHADOWROOT_BUTTON, DEAD_WOOD_BUTTON, DARK_WILLOW_BUTTON, DEWSHROOM_BUTTON, VIRIDSHROOM_BUTTON, NIGHTSHROOM_BUTTON, NIGHTSTONE_BUTTON, TRENCHSTONE_BUTTON, ROCKSHROOM_BRICKS_BUTTON,
                SHADOWROOT_PRESSURE_PLATE, DEAD_WOOD_PRESSURE_PLATE, DARK_WILLOW_PRESSURE_PLATE, DEWSHROOM_PRESSURE_PLATE, VIRIDSHROOM_PRESSURE_PLATE, NIGHTSHROOM_PRESSURE_PLATE, NIGHTSTONE_PRESSURE_PLATE, TRENCHSTONE_PRESSURE_PLATE, ROCKSHROOM_BRICKS_PRESSURE_PLATE, NAGRILITE_PRESSURE_PLATE, TENEBRUM_PRESSURE_PLATE,
                MIDNIGHT_LEVER
        ));

        registry.register(itemBlock(DECEITFUL_ALGAE, DeceitfulAlgaeItem::new));

        registry.register(itemBlock(SHADOWROOT_SLAB, b -> new MidnightSlabItem(b, SHADOWROOT_DOUBLE_SLAB)));
        registry.register(itemBlock(DEAD_WOOD_SLAB, b -> new MidnightSlabItem(b, DEAD_WOOD_DOUBLE_SLAB)));
        registry.register(itemBlock(DARK_WILLOW_SLAB, b -> new MidnightSlabItem(b, DARK_WILLOW_DOUBLE_SLAB)));
        registry.register(itemBlock(NIGHTSTONE_SLAB, b -> new MidnightSlabItem(b, NIGHTSTONE_DOUBLE_SLAB)));
        registry.register(itemBlock(NIGHTSTONE_BRICK_SLAB, b -> new MidnightSlabItem(b, NIGHTSTONE_BRICK_DOUBLE_SLAB)));
        registry.register(itemBlock(TRENCHSTONE_SLAB, b -> new MidnightSlabItem(b, TRENCHSTONE_DOUBLE_SLAB)));
        registry.register(itemBlock(TRENCHSTONE_BRICK_SLAB, b -> new MidnightSlabItem(b, TRENCHSTONE_BRICK_DOUBLE_SLAB)));
        registry.register(itemBlock(DEWSHROOM_SLAB, b -> new MidnightSlabItem(b, DEWSHROOM_DOUBLE_SLAB)));
        registry.register(itemBlock(VIRIDSHROOM_SLAB, b -> new MidnightSlabItem(b, VIRIDSHROOM_DOUBLE_SLAB)));
        registry.register(itemBlock(NIGHTSHROOM_SLAB, b -> new MidnightSlabItem(b, NIGHTSHROOM_DOUBLE_SLAB)));
        registry.register(itemBlock(ROCKSHROOM_BRICKS_SLAB, b -> new MidnightSlabItem(b, ROCKSHROOM_BRICKS_DOUBLE_SLAB)));
    }
}
