package com.mushroom.midnight.common.world.feature.tree;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.function.Function;

public class SmallNightshroomFeature extends TemplateFungiFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/nightshroom_small")
    };

    public SmallNightshroomFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize, TEMPLATES, MidnightBlocks.NIGHTSHROOM_STEM.getDefaultState(), MidnightBlocks.NIGHTSHROOM_HAT.getDefaultState());
    }
}
