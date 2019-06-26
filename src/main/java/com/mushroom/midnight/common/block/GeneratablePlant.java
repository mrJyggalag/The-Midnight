package com.mushroom.midnight.common.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public interface GeneratablePlant extends IPlantable {
    default boolean canGeneratePlant(World world, BlockPos pos, BlockState state) {
        BlockState soil = world.getBlockState(pos.down());
        return soil.getBlock().canSustainPlant(soil, world, pos.down(), Direction.UP, this);
    }

    static boolean canGenerate(World world, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof GeneratablePlant) {
            return ((GeneratablePlant) state.getBlock()).canGeneratePlant(world, pos, state);
        }
        return false;
    }
}
