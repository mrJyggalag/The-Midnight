package com.mushroom.midnight.common.compatibility;

import com.mushroom.midnight.common.registry.ModBlocks;
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
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 20).add(Aspect.DARKNESS, 5), ModBlocks.SHADOWROOT_LOG, ModBlocks.DEAD_WOOD_LOG, ModBlocks.DARK_WILLOW_LOG);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.DARKNESS, 3), ModBlocks.SHADOWROOT_LEAVES, ModBlocks.DARK_WILLOW_LEAVES, ModBlocks.DECEITFUL_MOSS);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 3).add(Aspect.DARKNESS, 1), ModBlocks.SHADOWROOT_PLANKS, ModBlocks.DEAD_WOOD_PLANKS, ModBlocks.DARK_WILLOW_PLANKS, ModBlocks.SHADOWROOT_STAIRS, ModBlocks.DEAD_WOOD_STAIRS, ModBlocks.DARK_WILLOW_STAIRS, ModBlocks.SHADOWROOT_FENCE, ModBlocks.DARK_WILLOW_FENCE, ModBlocks.DEAD_WOOD_FENCE, ModBlocks.SHADOWROOT_LADDER, ModBlocks.DARK_WILLOW_LADDER, ModBlocks.DEAD_WOOD_LADDER);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 1), ModBlocks.NIGHTSHROOM_PLANKS, ModBlocks.DEWSHROOM_PLANKS, ModBlocks.VIRIDSHROOM_PLANKS, ModBlocks.NIGHTSHROOM_STAIRS, ModBlocks.DEWSHROOM_STAIRS, ModBlocks.VIRIDSHROOM_STAIRS, ModBlocks.ROCKSHROOM_BRICKS_STAIRS, ModBlocks.NIGHTSHROOM_FENCE, ModBlocks.DEWSHROOM_FENCE, ModBlocks.VIRIDSHROOM_FENCE, ModBlocks.NIGHTSHROOM_LADDER, ModBlocks.DEWSHROOM_LADDER, ModBlocks.VIRIDSHROOM_LADDER);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 3).add(Aspect.DARKNESS, 1), ModBlocks.NIGHTSTONE_STAIRS, ModBlocks.NIGHTSTONE_BRICK_STAIRS, ModBlocks.NIGHTSTONE_WALL, ModBlocks.NIGHTSTONE_BRICK_WALL);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 3).add(Aspect.VOID, 1), ModBlocks.TRENCHSTONE_STAIRS, ModBlocks.TRENCHSTONE_BRICK_STAIRS, ModBlocks.TRENCHSTONE_WALL, ModBlocks.TRENCHSTONE_BRICK_WALL);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.DARKNESS, 5), ModBlocks.NIGHTSTONE);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 10).add(Aspect.DARKNESS, 5), ModBlocks.NIGHTSTONE_BRICKS);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 15).add(Aspect.DARKNESS, 10), ModBlocks.CHISELED_NIGHTSTONE_BRICKS);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 10).add(Aspect.VOID, 10), ModBlocks.TRENCHSTONE);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 15).add(Aspect.VOID, 15), ModBlocks.TRENCHSTONE_BRICKS);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.DARKNESS, 15).add(Aspect.FIRE, 10), ModBlocks.DARK_PEARL_ORE);
        registerBlocks(event, new AspectList().add(Aspect.DARKNESS, 101).add(Aspect.FIRE, 67), ModBlocks.DARK_PEARL_BLOCK);
        registerBlocks(event, new AspectList().add(Aspect.DARKNESS, 101).add(Aspect.METAL, 67), ModBlocks.TENEBRUM_BLOCK);
        registerBlocks(event, new AspectList().add(Aspect.PROTECT, 67).add(Aspect.METAL, 67), ModBlocks.NAGRILITE_BLOCK);
        registerBlocks(event, new AspectList().add(Aspect.DARKNESS, 67).add(Aspect.PROTECT, 67).add(Aspect.METAL, 67), ModBlocks.EBONYS_BLOCK);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 9).add(Aspect.DARKNESS, 9).add(Aspect.CRAFT, 20), ModBlocks.SHADOWROOT_CRAFTING_TABLE);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 20).add(Aspect.DARKNESS, 5), ModBlocks.SHADOWROOT_CHEST);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 30).add(Aspect.DARKNESS, 10).add(Aspect.FIRE, 10), ModBlocks.MIDNIGHT_FURNACE);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.DARKNESS, 5), ModBlocks.MIDNIGHT_DIRT);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 2), ModBlocks.MIDNIGHT_GRASS);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 2).add(Aspect.ENTROPY, 2), ModBlocks.MIDNIGHT_MYCELIUM);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.DARKNESS, 2), ModBlocks.TALL_MIDNIGHT_GRASS, ModBlocks.DOUBLE_MIDNIGHT_GRASS, ModBlocks.BOGWEED);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.ENTROPY, 5), ModBlocks.NIGHTSHROOM, ModBlocks.DOUBLE_NIGHTSHROOM, ModBlocks.DEWSHROOM, ModBlocks.DOUBLE_DEWSHROOM, ModBlocks.VIRIDSHROOM, ModBlocks.DOUBLE_VIRIDSHROOM, ModBlocks.BOGSHROOM, ModBlocks.DOUBLE_BOGSHROOM, ModBlocks.BULB_FUNGUS, ModBlocks.NIGHTSHROOM_SHELF, ModBlocks.DEWSHROOM_SHELF, ModBlocks.VIRIDSHROOM_SHELF, ModBlocks.BOGSHROOM_SHELF, ModBlocks.BLADESHROOM);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIGHT, 5), ModBlocks.GHOST_PLANT, ModBlocks.LUMEN_BUD, ModBlocks.DOUBLE_LUMEN_BUD, ModBlocks.FINGERED_GRASS, ModBlocks.TENDRILWEED);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.MAGIC, 10).add(Aspect.FLUX, 10), ModBlocks.RUNEBUSH);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.FIRE, 5).add(Aspect.DARKNESS, 5), ModBlocks.DRAGON_NEST);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.MAGIC, 5).add(Aspect.DARKNESS, 2), ModBlocks.VIOLEAF);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.MAGIC, 5).add(Aspect.CRYSTAL, 5), ModBlocks.CRYSTAL_FLOWER);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5), ModBlocks.SHADOWROOT_SAPLING, ModBlocks.DARK_WILLOW_SAPLING);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 20).add(Aspect.ENTROPY, 5), ModBlocks.VIRIDSHROOM_STEM, ModBlocks.DEWSHROOM_STEM, ModBlocks.NIGHTSHROOM_STEM, ModBlocks.BOGSHROOM_STEM, ModBlocks.BULB_FUNGUS_STEM);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 20).add(Aspect.ENTROPY, 5), ModBlocks.VIRIDSHROOM_HAT, ModBlocks.NIGHTSHROOM_HAT, ModBlocks.DEWSHROOM_HAT, ModBlocks.BOGSHROOM_HAT, ModBlocks.BULB_FUNGUS_HAT);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 1).add(Aspect.DARKNESS, 1), ModBlocks.SHADOWROOT_SLAB, ModBlocks.DARK_WILLOW_SLAB, ModBlocks.DEAD_WOOD_SLAB);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 1).add(Aspect.DARKNESS, 1), ModBlocks.NIGHTSTONE_SLAB, ModBlocks.NIGHTSTONE_BRICK_SLAB);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 1).add(Aspect.VOID, 1), ModBlocks.TRENCHSTONE_SLAB, ModBlocks.TRENCHSTONE_BRICK_SLAB);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 1).add(Aspect.ENTROPY, 1), ModBlocks.DEWSHROOM_SLAB, ModBlocks.VIRIDSHROOM_SLAB, ModBlocks.NIGHTSHROOM_SLAB, ModBlocks.ROCKSHROOM_BRICKS_SLAB);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 5), ModBlocks.ROCKSHROOM, ModBlocks.ROCKSHROOM_BRICKS_WALL);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 10).add(Aspect.ENTROPY, 5), ModBlocks.ROCKSHROOM_BRICKS);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 4).add(Aspect.MECHANISM, 5).add(Aspect.TRAP, 5).add(Aspect.DARKNESS, 2), ModBlocks.SHADOWROOT_DOOR, ModBlocks.DARK_WILLOW_DOOR, ModBlocks.DEAD_WOOD_DOOR);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 4).add(Aspect.MECHANISM, 5).add(Aspect.TRAP, 5).add(Aspect.ENTROPY, 2), ModBlocks.NIGHTSHROOM_DOOR, ModBlocks.DEWSHROOM_DOOR, ModBlocks.VIRIDSHROOM_DOOR);
        registerBlocks(event, new AspectList().add(Aspect.METAL, 22).add(Aspect.MECHANISM, 5).add(Aspect.TRAP, 5).add(Aspect.DARKNESS, 5), ModBlocks.TENEBRUM_DOOR);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 2), ModBlocks.SHADOWROOT_TRAPDOOR, ModBlocks.DARK_WILLOW_TRAPDOOR, ModBlocks.DEAD_WOOD_TRAPDOOR);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 2).add(Aspect.ENTROPY, 2), ModBlocks.NIGHTSHROOM_TRAPDOOR, ModBlocks.DEWSHROOM_TRAPDOOR, ModBlocks.VIRIDSHROOM_TRAPDOOR);
        registerBlocks(event, new AspectList().add(Aspect.METAL, 45).add(Aspect.DARKNESS, 15), ModBlocks.TENEBRUM_TRAPDOOR);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 10).add(Aspect.MAGIC, 10).add(Aspect.CRYSTAL, 15), ModBlocks.BLOOMCRYSTAL, ModBlocks.BLOOMCRYSTAL_ROCK, ModBlocks.ROUXE, ModBlocks.ROUXE_ROCK);
        registerBlocks(event, new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.VOID, 5), ModBlocks.ARCHAIC_GLASS);
        registerBlocks(event, new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.VOID, 5), ModBlocks.ARCHAIC_GLASS_PANE);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 7).add(Aspect.MECHANISM, 5).add(Aspect.TRAP, 5).add(Aspect.DARKNESS, 5), ModBlocks.SHADOWROOT_FENCE_GATE, ModBlocks.DARK_WILLOW_FENCE_GATE, ModBlocks.DEAD_WOOD_FENCE_GATE);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 7).add(Aspect.MECHANISM, 5).add(Aspect.TRAP, 5).add(Aspect.ENTROPY, 5), ModBlocks.NIGHTSHROOM_FENCE_GATE, ModBlocks.VIRIDSHROOM_FENCE_GATE, ModBlocks.DEWSHROOM_FENCE_GATE);
        registerBlocks(event, new AspectList().add(Aspect.LIGHT, 5).add(Aspect.ENERGY, 1).add(Aspect.FIRE, 1), ModBlocks.NIGHTSHROOM_SPORCH, ModBlocks.VIRIDSHROOM_SPORCH, ModBlocks.DEWSHROOM_SPORCH, ModBlocks.BOGSHROOM_SPORCH);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 10).add(Aspect.SENSES, 10).add(Aspect.ENTROPY, 10), ModBlocks.SUAVIS);
        registerBlocks(event, new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.ENTROPY, 10), ModBlocks.STINGER_EGG);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 10).add(Aspect.FLIGHT, 10).add(Aspect.ENTROPY, 10), ModBlocks.UNSTABLE_BUSH, ModBlocks.UNSTABLE_BUSH_BLUE_BLOOMED, ModBlocks.UNSTABLE_BUSH_GREEN_BLOOMED, ModBlocks.UNSTABLE_BUSH_LIME_BLOOMED);
        registerBlocks(event, new AspectList().add(Aspect.WATER, 20).add(Aspect.DARKNESS, 20), ModBlocks.DARK_WATER);
        registerBlocks(event, new AspectList().add(Aspect.FIRE, 15).add(Aspect.DARKNESS, 20).add(Aspect.METAL, 15), ModBlocks.MIASMA);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 5).add(Aspect.FIRE, 15).add(Aspect.DARKNESS, 15), ModBlocks.MIASMA_SURFACE);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 20).add(Aspect.LIGHT, 10).add(Aspect.ENTROPY, 10), ModBlocks.MUSHROOM_INSIDE);
        registerBlocks(event, new AspectList().add(Aspect.PLANT, 5).add(Aspect.WATER, 1).add(Aspect.DARKNESS, 1), ModBlocks.DECEITFUL_ALGAE);
        registerBlocks(event, new AspectList().add(Aspect.EARTH, 3).add(Aspect.WATER, 3).add(Aspect.DARKNESS, 5), ModBlocks.DECEITFUL_PEAT, ModBlocks.DECEITFUL_MUD);

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
