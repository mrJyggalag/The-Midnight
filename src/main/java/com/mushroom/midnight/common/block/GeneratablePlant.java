package com.mushroom.midnight.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public interface GeneratablePlant extends IPlantable {
    default boolean canGeneratePlant(World world, BlockPos pos, IBlockState state) {
        IBlockState soil = world.getBlockState(pos.down());
        return soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
    }

    static boolean canGenerate(World world, BlockPos pos, IBlockState state) {
        if (state.getBlock() instanceof GeneratablePlant) {
            return ((GeneratablePlant) state.getBlock()).canGeneratePlant(world, pos, state);
        }
        return false;
    }
}
