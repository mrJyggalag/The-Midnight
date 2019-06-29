package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightSounds;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;

public class DeceitfulMudBlock extends Block {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0);

    public DeceitfulMudBlock() {
        super(Material.GROUND, MaterialColor.GRAY_TERRACOTTA);
        this.setHardness(0.5F);
        this.setSoundType(MidnightSounds.MUD);
        this.setCreativeTab(MidnightItemGroups.BUILDING);
        this.setHarvestLevel("shovel", 0);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) {
        return true;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        return BOUNDS;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.setMotion(entity.getMotion().mul(0.6d, 1d, 0.6d));
    }
}
