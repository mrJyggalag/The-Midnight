package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;

public class MidnightLadderBlock extends BlockLadder {

    public MidnightLadderBlock() {
        super();
        setHardness(0.4F);
        setSoundType(SoundType.LADDER);
        setCreativeTab(MidnightItemGroups.DECORATION);
    }
}
