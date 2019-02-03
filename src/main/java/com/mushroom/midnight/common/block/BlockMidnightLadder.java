package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;

public class BlockMidnightLadder extends BlockLadder implements IModelProvider {

    public BlockMidnightLadder() {
        super();
        setHardness(0.4F);
        setSoundType(SoundType.LADDER);
        setCreativeTab(ModTabs.DECORATION_TAB);
    }
}
