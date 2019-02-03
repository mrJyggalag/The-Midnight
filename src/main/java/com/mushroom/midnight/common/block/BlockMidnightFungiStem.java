package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockMidnightFungiStem extends Block implements IModelProvider {
    public BlockMidnightFungiStem() {
        super(Material.WOOD);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(ModTabs.BUILDING_TAB);
    }
}
