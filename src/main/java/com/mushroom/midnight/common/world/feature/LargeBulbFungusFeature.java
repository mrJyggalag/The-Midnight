package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class LargeBulbFungusFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/bulbfungus")
    };

    public LargeBulbFungusFeature(IBlockState log, IBlockState leaves) {
        super(TEMPLATES, log, leaves);
    }

    public LargeBulbFungusFeature() {
        this(ModBlocks.BULB_FUNGUS_STEM.getDefaultState(), ModBlocks.BULB_FUNGUS_HAT.getDefaultState());
    }
}
