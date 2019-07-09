package com.mushroom.midnight.common.world.feature.tree;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.IPlantable;

import java.util.function.Function;

public class DarkWillowTreeFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "trees/dark_willow_1"),
            new ResourceLocation(Midnight.MODID, "trees/dark_willow_2"),
            new ResourceLocation(Midnight.MODID, "trees/dark_willow_3")
    };

    private static final BlockState LOG = MidnightBlocks.DARK_WILLOW_LOG.getDefaultState();
    private static final BlockState LEAVES = MidnightBlocks.DARK_WILLOW_LEAVES.getDefaultState();

    public DarkWillowTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize, TEMPLATES, LOG, LEAVES);
        this.setSapling((IPlantable) MidnightBlocks.DARK_WILLOW_SAPLING);
    }
}
