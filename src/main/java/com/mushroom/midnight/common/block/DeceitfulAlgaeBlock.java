package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;

public class DeceitfulAlgaeBlock extends BushBlock {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

    public DeceitfulAlgaeBlock() {
        super(Properties.create(Material.PLANTS).hardnessAndResistance(0f).sound(SoundType.PLANT));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        IFluidState fluidState = world.getFluidState(pos);
        return fluidState.getFluid().isIn(FluidTags.WATER);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.Water;
    }
}
