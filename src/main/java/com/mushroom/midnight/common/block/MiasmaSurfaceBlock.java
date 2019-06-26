package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.BlockMagma;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MiasmaSurfaceBlock extends BlockMagma {
    public MiasmaSurfaceBlock() {
        super();
        this.setHardness(0.5F);
        this.setCreativeTab(MidnightItemGroups.BUILDING);
    }

    @Override
    public MapColor getMapColor(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.BLUE_STAINED_HARDENED_CLAY;
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, Direction side) {
        return side == Direction.UP;
    }

    @Override
    @Nullable
    public PathNodeType getAiPathNodeType(BlockState state, IBlockAccess world, BlockPos pos) {
        return PathNodeType.DAMAGE_FIRE;
    }
}
