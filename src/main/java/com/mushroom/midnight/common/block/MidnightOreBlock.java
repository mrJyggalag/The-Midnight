package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MidnightOreBlock extends Block {
    public MidnightOreBlock(int harvestLevel) {
        super(Material.ROCK);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(MidnightItemGroups.BUILDING);
        this.setHarvestLevel("pickaxe", harvestLevel);
    }
}
