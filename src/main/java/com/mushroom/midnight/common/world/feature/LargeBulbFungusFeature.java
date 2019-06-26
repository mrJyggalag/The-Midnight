package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.ResourceLocation;

public class LargeBulbFungusFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/bulbfungus_1"),
            new ResourceLocation(Midnight.MODID, "mushroom/bulbfungus_2")
    };

    public LargeBulbFungusFeature(BlockState log, BlockState leaves) {
        super(TEMPLATES, log, leaves);
    }

    public LargeBulbFungusFeature() {
        this(MidnightBlocks.BULB_FUNGUS_STEM.getDefaultState(), MidnightBlocks.BULB_FUNGUS_HAT.getDefaultState());
    }
}
