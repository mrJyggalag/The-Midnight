package com.mushroom.midnight.common.compatibility;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nonnull;

public enum SupportMods implements IStringSerializable {
    THAUMCRAFT("thaumcraft");

    private final String modid;
    private final boolean loaded;

    SupportMods(String modid, boolean test) {
        this.modid = modid;
        this.loaded = Loader.isModLoaded(modid) && test;
    }

    SupportMods(String modid) {
        this(modid, true);
    }

    public boolean isLoaded() {
        return loaded;
    }

    @Override
    @Nonnull
    public String getName() {
        return modid;
    }
}
