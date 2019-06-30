package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class MidnightOreBlock extends Block {
    private final int harvestLevel;
    public MidnightOreBlock(int harvestLevel) {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3f, 5f).sound(SoundType.STONE));
        this.harvestLevel = harvestLevel;
        //setCreativeTab(MidnightItemGroups.BUILDING);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return this.harvestLevel;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }
}
