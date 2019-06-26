package com.mushroom.midnight.common.compatibility;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

import static com.mushroom.midnight.Midnight.MODID;

public class CompatibilityThaumcraft {
    public static final CompatibilityThaumcraft instance = new CompatibilityThaumcraft();

    private CompatibilityThaumcraft() {
    }

    @SubscribeEvent
    public void register(AspectRegistryEvent event) {
        // based on vanilla values set by Thaumcraft (need to be tweaked then)

        // register blocks
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 20).add(Aspect.DARKNESS, 5), MidnightBlocks.SHADOWROOT_LOG, MidnightBlocks.DEAD_WOOD_LOG, MidnightBlocks.DARK_WILLOW_LOG);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.DARKNESS, 3), MidnightBlocks.SHADOWROOT_LEAVES, MidnightBlocks.DARK_WILLOW_LEAVES, MidnightBlocks.DECEITFUL_MOSS);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 3).add(Aspect.DARKNESS, 1), MidnightBlocks.SHADOWROOT_PLANKS, MidnightBlocks.DEAD_WOOD_PLANKS, MidnightBlocks.DARK_WILLOW_PLANKS, MidnightBlocks.SHADOWROOT_STAIRS, MidnightBlocks.DEAD_WOOD_STAIRS, MidnightBlocks.DARK_WILLOW_STAIRS, MidnightBlocks.SHADOWROOT_FENCE, MidnightBlocks.DARK_WILLOW_FENCE, MidnightBlocks.DEAD_WOOD_FENCE, MidnightBlocks.SHADOWROOT_LADDER, MidnightBlocks.DARK_WILLOW_LADDER, MidnightBlocks.DEAD_WOOD_LADDER);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 1), MidnightBlocks.NIGHTSHROOM_PLANKS, MidnightBlocks.DEWSHROOM_PLANKS, MidnightBlocks.VIRIDSHROOM_PLANKS, MidnightBlocks.NIGHTSHROOM_STAIRS, MidnightBlocks.DEWSHROOM_STAIRS, MidnightBlocks.VIRIDSHROOM_STAIRS, MidnightBlocks.ROCKSHROOM_BRICKS_STAIRS, MidnightBlocks.NIGHTSHROOM_FENCE, MidnightBlocks.DEWSHROOM_FENCE, MidnightBlocks.VIRIDSHROOM_FENCE, MidnightBlocks.NIGHTSHROOM_LADDER, MidnightBlocks.DEWSHROOM_LADDER, MidnightBlocks.VIRIDSHROOM_LADDER);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 3).add(Aspect.DARKNESS, 1), MidnightBlocks.NIGHTSTONE_STAIRS, MidnightBlocks.NIGHTSTONE_BRICK_STAIRS, MidnightBlocks.NIGHTSTONE_WALL, MidnightBlocks.NIGHTSTONE_BRICK_WALL);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 3).add(Aspect.VOID, 1), MidnightBlocks.TRENCHSTONE_STAIRS, MidnightBlocks.TRENCHSTONE_BRICK_STAIRS, MidnightBlocks.TRENCHSTONE_WALL, MidnightBlocks.TRENCHSTONE_BRICK_WALL);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.DARKNESS, 5), MidnightBlocks.NIGHTSTONE);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 10).add(Aspect.DARKNESS, 5), MidnightBlocks.NIGHTSTONE_BRICKS);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 15).add(Aspect.DARKNESS, 10), MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 10).add(Aspect.VOID, 10), MidnightBlocks.TRENCHSTONE);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 15).add(Aspect.VOID, 15), MidnightBlocks.TRENCHSTONE_BRICKS);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.DARKNESS, 15).add(Aspect.FIRE, 10), MidnightBlocks.DARK_PEARL_ORE);
        registerBlocks(event, new AspectList().add(Aspect.DARKNESS, 101).add(Aspect.FIRE, 67), MidnightBlocks.DARK_PEARL_BLOCK);
        registerBlocks(event, new AspectList().add(Aspect.DARKNESS, 101).add(Aspect.METAL, 67), MidnightBlocks.TENEBRUM_BLOCK);
        registerBlocks(event, new AspectList().add(Aspect.PROTECT, 67).add(Aspect.METAL, 67), MidnightBlocks.NAGRILITE_BLOCK);
        registerBlocks(event, new AspectList().add(Aspect.DARKNESS, 67).add(Aspect.PROTECT, 67).add(Aspect.METAL, 67), MidnightBlocks.EBONYS_BLOCK);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 9).add(Aspect.DARKNESS, 9).add(Aspect.CRAFT, 20), MidnightBlocks.SHADOWROOT_CRAFTING_TABLE);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 20).add(Aspect.DARKNESS, 5), MidnightBlocks.SHADOWROOT_CHEST);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 30).add(Aspect.DARKNESS, 10).add(Aspect.FIRE, 10), MidnightBlocks.MIDNIGHT_FURNACE);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.DARKNESS, 5), MidnightBlocks.MIDNIGHT_DIRT);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 2), MidnightBlocks.MIDNIGHT_GRASS);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 2).add(Aspect.ENTROPY, 2), MidnightBlocks.MIDNIGHT_MYCELIUM);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.DARKNESS, 2), MidnightBlocks.TALL_MIDNIGHT_GRASS, MidnightBlocks.DOUBLE_MIDNIGHT_GRASS, MidnightBlocks.BOGWEED);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.ENTROPY, 5), MidnightBlocks.NIGHTSHROOM, MidnightBlocks.DOUBLE_NIGHTSHROOM, MidnightBlocks.DEWSHROOM, MidnightBlocks.DOUBLE_DEWSHROOM, MidnightBlocks.VIRIDSHROOM, MidnightBlocks.DOUBLE_VIRIDSHROOM, MidnightBlocks.BOGSHROOM, MidnightBlocks.DOUBLE_BOGSHROOM, MidnightBlocks.BULB_FUNGUS, MidnightBlocks.NIGHTSHROOM_SHELF, MidnightBlocks.DEWSHROOM_SHELF, MidnightBlocks.VIRIDSHROOM_SHELF, MidnightBlocks.BOGSHROOM_SHELF, MidnightBlocks.BLADESHROOM);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIGHT, 5), MidnightBlocks.GHOST_PLANT, MidnightBlocks.LUMEN_BUD, MidnightBlocks.DOUBLE_LUMEN_BUD, MidnightBlocks.FINGERED_GRASS, MidnightBlocks.TENDRILWEED);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.MAGIC, 10).add(Aspect.FLUX, 10), MidnightBlocks.RUNEBUSH);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.FIRE, 5).add(Aspect.DARKNESS, 5), MidnightBlocks.DRAGON_NEST);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.MAGIC, 5).add(Aspect.DARKNESS, 2), MidnightBlocks.VIOLEAF);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.MAGIC, 5).add(Aspect.CRYSTAL, 5), MidnightBlocks.CRYSTAL_FLOWER);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5), MidnightBlocks.SHADOWROOT_SAPLING, MidnightBlocks.DARK_WILLOW_SAPLING);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 20).add(Aspect.ENTROPY, 5), MidnightBlocks.VIRIDSHROOM_STEM, MidnightBlocks.DEWSHROOM_STEM, MidnightBlocks.NIGHTSHROOM_STEM, MidnightBlocks.BOGSHROOM_STEM, MidnightBlocks.BULB_FUNGUS_STEM);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 20).add(Aspect.ENTROPY, 5), MidnightBlocks.VIRIDSHROOM_HAT, MidnightBlocks.NIGHTSHROOM_HAT, MidnightBlocks.DEWSHROOM_HAT, MidnightBlocks.BOGSHROOM_HAT, MidnightBlocks.BULB_FUNGUS_HAT);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 1).add(Aspect.DARKNESS, 1), MidnightBlocks.SHADOWROOT_SLAB, MidnightBlocks.DARK_WILLOW_SLAB, MidnightBlocks.DEAD_WOOD_SLAB);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 1).add(Aspect.DARKNESS, 1), MidnightBlocks.NIGHTSTONE_SLAB, MidnightBlocks.NIGHTSTONE_BRICK_SLAB);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 1).add(Aspect.VOID, 1), MidnightBlocks.TRENCHSTONE_SLAB, MidnightBlocks.TRENCHSTONE_BRICK_SLAB);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 1).add(Aspect.ENTROPY, 1), MidnightBlocks.DEWSHROOM_SLAB, MidnightBlocks.VIRIDSHROOM_SLAB, MidnightBlocks.NIGHTSHROOM_SLAB, MidnightBlocks.ROCKSHROOM_BRICKS_SLAB);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 5), MidnightBlocks.ROCKSHROOM, MidnightBlocks.ROCKSHROOM_BRICKS_WALL);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 10).add(Aspect.ENTROPY, 5), MidnightBlocks.ROCKSHROOM_BRICKS);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 4).add(Aspect.MECHANISM, 5).add(Aspect.TRAP, 5).add(Aspect.DARKNESS, 2), MidnightBlocks.SHADOWROOT_DOOR, MidnightBlocks.DARK_WILLOW_DOOR, MidnightBlocks.DEAD_WOOD_DOOR);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 4).add(Aspect.MECHANISM, 5).add(Aspect.TRAP, 5).add(Aspect.ENTROPY, 2), MidnightBlocks.NIGHTSHROOM_DOOR, MidnightBlocks.DEWSHROOM_DOOR, MidnightBlocks.VIRIDSHROOM_DOOR);
        registerBlocks(event, new AspectList().add(Aspect.METAL, 22).add(Aspect.MECHANISM, 5).add(Aspect.TRAP, 5).add(Aspect.DARKNESS, 5), MidnightBlocks.TENEBRUM_DOOR);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 2), MidnightBlocks.SHADOWROOT_TRAPDOOR, MidnightBlocks.DARK_WILLOW_TRAPDOOR, MidnightBlocks.DEAD_WOOD_TRAPDOOR);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 2).add(Aspect.ENTROPY, 2), MidnightBlocks.NIGHTSHROOM_TRAPDOOR, MidnightBlocks.DEWSHROOM_TRAPDOOR, MidnightBlocks.VIRIDSHROOM_TRAPDOOR);
        registerBlocks(event, new AspectList().add(Aspect.METAL, 45).add(Aspect.DARKNESS, 15), MidnightBlocks.TENEBRUM_TRAPDOOR);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 10).add(Aspect.MAGIC, 10).add(Aspect.CRYSTAL, 15), MidnightBlocks.BLOOMCRYSTAL, MidnightBlocks.BLOOMCRYSTAL_ROCK, MidnightBlocks.ROUXE, MidnightBlocks.ROUXE_ROCK);
        registerBlocks(event, new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.VOID, 5), MidnightBlocks.ARCHAIC_GLASS);
        registerBlocks(event, new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.VOID, 5), MidnightBlocks.ARCHAIC_GLASS_PANE);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 7).add(Aspect.MECHANISM, 5).add(Aspect.TRAP, 5).add(Aspect.DARKNESS, 5), MidnightBlocks.SHADOWROOT_FENCE_GATE, MidnightBlocks.DARK_WILLOW_FENCE_GATE, MidnightBlocks.DEAD_WOOD_FENCE_GATE);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 7).add(Aspect.MECHANISM, 5).add(Aspect.TRAP, 5).add(Aspect.ENTROPY, 5), MidnightBlocks.NIGHTSHROOM_FENCE_GATE, MidnightBlocks.VIRIDSHROOM_FENCE_GATE, MidnightBlocks.DEWSHROOM_FENCE_GATE);
        registerBlocks(event, new AspectList().add(Aspect.LIGHT, 5).add(Aspect.ENERGY, 1).add(Aspect.FIRE, 1), MidnightBlocks.NIGHTSHROOM_SPORCH, MidnightBlocks.VIRIDSHROOM_SPORCH, MidnightBlocks.DEWSHROOM_SPORCH, MidnightBlocks.BOGSHROOM_SPORCH);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 10).add(Aspect.SENSES, 10).add(Aspect.ENTROPY, 10), MidnightBlocks.SUAVIS);
        registerBlocks(event, new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.ENTROPY, 10), MidnightBlocks.STINGER_EGG);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 10).add(Aspect.FLIGHT, 10).add(Aspect.ENTROPY, 10), MidnightBlocks.UNSTABLE_BUSH, MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED, MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED, MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED);
        registerBlocks(event, new AspectList().add(Aspect.WATER, 20).add(Aspect.DARKNESS, 20), MidnightBlocks.DARK_WATER);
        registerBlocks(event, new AspectList().add(Aspect.FIRE, 15).add(Aspect.DARKNESS, 20).add(Aspect.METAL, 15), MidnightBlocks.MIASMA);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.FIRE, 15).add(Aspect.DARKNESS, 15), MidnightBlocks.MIASMA_SURFACE);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 20).add(Aspect.LIGHT, 10).add(Aspect.ENTROPY, 10), MidnightBlocks.MUSHROOM_INSIDE);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.WATER, 1).add(Aspect.DARKNESS, 1), MidnightBlocks.DECEITFUL_ALGAE);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 3).add(Aspect.WATER, 3).add(Aspect.DARKNESS, 5), MidnightBlocks.DECEITFUL_PEAT, MidnightBlocks.DECEITFUL_MUD);

        // register oredicts
        event.register.registerObjectTag("oreTenebrum", new AspectList().add(Aspect.EARTH, 5).add(Aspect.DARKNESS, 20).add(Aspect.METAL, 15));
        event.register.registerObjectTag("oreNagrilite", new AspectList().add(Aspect.EARTH, 5).add(Aspect.PROTECT, 15).add(Aspect.METAL, 15));
        event.register.registerObjectTag("oreEbonys", new AspectList().add(Aspect.EARTH, 5).add(Aspect.DARKNESS, 10).add(Aspect.PROTECT, 10).add(Aspect.METAL, 10));
        event.register.registerObjectTag("oreArchaic", new AspectList().add(Aspect.EARTH, 5).add(Aspect.VOID, 30));

        // register entities (the api doesn't have method for this)
        registerEntity("rifter", new AspectList().add(Aspect.EARTH, 10).add(Aspect.PLANT, 10).add(Aspect.DARKNESS, 10));
        registerEntity("hunter", new AspectList().add(Aspect.FLIGHT, 10).add(Aspect.DARKNESS, 20).add(Aspect.BEAST, 15));
        registerEntity("nova", new AspectList().add(Aspect.CRYSTAL, 15).add(Aspect.EARTH, 20));
        registerEntity("crystal_bug", new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.FLIGHT, 5).add(Aspect.LIGHT, 5));
        registerEntity("penumbrian", new AspectList().add(Aspect.LIGHT, 20).add(Aspect.ENTROPY, 15).add(Aspect.EARTH, 15));
        registerEntity("stinger", new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.BEAST, 10));
        registerEntity("nightstag", new AspectList().add(Aspect.BEAST, 15).add(Aspect.DARKNESS, 10).add(Aspect.EARTH, 15));
        registerEntity("deceitful_snapper", new AspectList().add(Aspect.WATER, 5).add(Aspect.DARKNESS, 5).add(Aspect.BEAST, 5));
        registerEntity("skulk", new AspectList().add(Aspect.DARKNESS, 10).add(Aspect.BEAST, 10).add(Aspect.EARTH, 5));
    }

    private void registerEntity(String name, AspectList aspects, ThaumcraftApi.EntityTagsNBT... nbt) {
        ThaumcraftApi.registerEntityTag(MODID + "." + name, aspects, nbt);
    }

    private void registerBlocks(AspectRegistryEvent event, AspectList aspects, Block... blocks) {
        for (Block block : blocks) {
            event.register.registerObjectTag(new ItemStack(block), aspects);
        }
    }
}
