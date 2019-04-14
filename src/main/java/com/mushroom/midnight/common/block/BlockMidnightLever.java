package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;

import net.minecraft.block.BlockLever;
import net.minecraft.block.SoundType;

public class BlockMidnightLever extends BlockLever implements IModelProvider {
    
    public BlockMidnightLever() {
        super();
        this.setCreativeTab(ModTabs.DECORATION_TAB);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.WOOD);
    }
    
}
