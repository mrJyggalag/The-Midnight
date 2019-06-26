package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightSounds;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;

public class DeceitfulMudBlock extends Block {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0);

    public DeceitfulMudBlock() {
        super(Material.GROUND, MapColor.GRAY_STAINED_HARDENED_CLAY);
        this.setHardness(0.5F);
        this.setSoundType(MidnightSounds.MUD);
        this.setCreativeTab(MidnightItemGroups.BUILDING);
        this.setHarvestLevel("shovel", 0);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockAccess world, BlockPos pos, Direction direction, IPlantable plantable) {
        return true;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        return BOUNDS;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {
        entity.motionX *= 0.6;
        entity.motionZ *= 0.6;
    }
}
