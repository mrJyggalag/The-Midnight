package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class MidnightWoodPlankBlock extends Block {
    public MidnightWoodPlankBlock() {
        super(Material.WOOD);
        setCreativeTab(MidnightItemGroups.BUILDING);
        setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(5.0F);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, Direction face) {
        return 20;
    }
}
