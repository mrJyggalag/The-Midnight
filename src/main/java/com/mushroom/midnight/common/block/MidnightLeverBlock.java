package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;

import net.minecraft.block.BlockLever;
import net.minecraft.block.SoundType;

public class MidnightLeverBlock extends BlockLever {
    
    public MidnightLeverBlock() {
        super();
        this.setCreativeTab(MidnightItemGroups.DECORATION);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.WOOD);
    }
    
}
