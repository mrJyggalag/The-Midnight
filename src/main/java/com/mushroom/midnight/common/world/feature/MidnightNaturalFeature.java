package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MidnightNaturalFeature extends MidnightAbstractFeature {
    protected void placeState(World world, BlockPos pos, BlockState state) {
        if (this.canReplace(world, pos)) {
            world.setBlockState(pos, state, 2 | 16);
        }
    }

    protected boolean canReplace(World world, BlockPos pos) {
        if (world.isOutsideBuildHeight(pos)) {
            return false;
        }

        BlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getMaterial() == Material.VINE;
    }
}
