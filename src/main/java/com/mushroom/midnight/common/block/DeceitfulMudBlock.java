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

@SuppressWarnings("deprecation")
public class DeceitfulMudBlock extends SoilBlock {
    private static final VoxelShape COLLISION_SHAPE = makeCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public DeceitfulMudBlock() {
        super(Properties.create(Material.ORGANIC, MaterialColor.GRAY_TERRACOTTA).hardnessAndResistance(0.5f, 0f).sound(MidnightSounds.MUD), false);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.setMotion(entity.getMotion().mul(0.6d, 1d, 0.6d));
    }
}
