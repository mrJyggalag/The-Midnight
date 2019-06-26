package com.mushroom.midnight.common.compatibility;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.ModList;

public enum SupportMods implements IStringSerializable {
    NO_MOD("no_mod");

    private final String modid;
    private final boolean loaded;

    SupportMods(String modid, boolean test) {
        this.modid = modid;
        this.loaded = ModList.get().getModContainerById(modid).isPresent() && test;
    }

    SupportMods(String modid) {
        this.modid = modid;
        this.loaded = ModList.get().getModContainerById(modid).isPresent();
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    @Override
    public String getName() {
        return this.modid;
    }
}
