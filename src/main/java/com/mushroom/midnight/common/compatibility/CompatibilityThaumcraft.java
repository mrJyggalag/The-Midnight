package com.mushroom.midnight.common.compatibility;

import com.mushroom.midnight.common.registry.ModItems;
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
        // TODO fill the vis values
        // items
        //event.register.registerObjectTag(new ItemStack(ModItems.DARK_PEARL), new AspectList().add(Aspect.DARKNESS, 10));

        // register ores
        /*event.register.registerObjectTag("oreEbonys", new AspectList().add(Aspect.DARKNESS, 5));
        event.register.registerObjectTag("oreArchaic", new AspectList().add(Aspect.DARKNESS, 5));
        event.register.registerObjectTag("oreNagrilite", new AspectList().add(Aspect.DARKNESS, 5));
        event.register.registerObjectTag("oreTenebrum", new AspectList().add(Aspect.DARKNESS, 5));
        event.register.registerObjectTag("logWood", new AspectList().add(Aspect.DARKNESS, 5));*/

        // entities (the api doesn't have method for this)
        /*registerEntity("rifter", new AspectList().add(Aspect.CRYSTAL, 1));
        registerEntity("hunter", new AspectList().add(Aspect., 1));
        registerEntity("nova", new AspectList().add(Aspect.CRYSTAL, 1));
        registerEntity("crystal_bug", new AspectList().add(Aspect.CRYSTAL, 1));
        registerEntity("penumbrian", new AspectList().add(Aspect.CRYSTAL, 1));
        registerEntity("stinger", new AspectList().add(Aspect.CRYSTAL, 1));
        registerEntity("nightstag", new AspectList().add(Aspect.CRYSTAL, 1));
        registerEntity("deceitful_snapper", new AspectList().add(Aspect.CRYSTAL, 1));
        registerEntity("skulk", new AspectList().add(Aspect.CRYSTAL, 1));*/
    }

    private void registerEntity(String name, AspectList aspects, ThaumcraftApi.EntityTagsNBT... nbt) {
        ThaumcraftApi.registerEntityTag(MODID + "." + name, aspects, nbt);
    }
}
