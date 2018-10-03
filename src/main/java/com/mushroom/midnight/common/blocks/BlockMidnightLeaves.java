package com.mushroom.midnight.common.blocks;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;

import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockMidnightLeaves extends BlockOldLeaf implements IModelProvider {

    public BlockMidnightLeaves() {
        super();
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
    }

    @Override public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos){ return true; }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.OAK;
    }
}
