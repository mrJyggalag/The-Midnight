package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DeceitfulMudBlock extends SoilBlock {
    private static final VoxelShape BOUNDS = makeCuboidShape(0.0, 0.0, 0.0, 1.0, 0.875, 1.0);

    public DeceitfulMudBlock() {
        super(Properties.create(Material.ORGANIC, MaterialColor.GRAY_TERRACOTTA).hardnessAndResistance(0.5f, 0f).sound(MidnightSounds.MUD), false);
        //setCreativeTab(MidnightItemGroups.BUILDING);
    }

    @Override
    @Nullable
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BOUNDS;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.setMotion(entity.getMotion().mul(0.6d, 1d, 0.6d));
    }
}
