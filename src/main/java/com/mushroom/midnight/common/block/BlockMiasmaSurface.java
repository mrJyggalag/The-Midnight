package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.BlockMagma;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMiasmaSurface extends BlockMagma implements IModelProvider {
    public BlockMiasmaSurface() {
        super();
        this.setHardness(0.5F);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.BLUE_STAINED_HARDENED_CLAY;
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP;
    }
}
