package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import javax.annotation.Nullable;

public class DeceitfulAlgaeBlock extends BushBlock {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.09375, 0.9375);

    public DeceitfulAlgaeBlock() {
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(MidnightItemGroups.DECORATION);
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDS;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

    @Override
    protected boolean canSustainBush(BlockState state) {
        Material material = state.getMaterial();
        return material == Material.WATER && state.get(BlockLiquid.LEVEL) == 0 || material == Material.ICE;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return this.canSustainBush(world.getBlockState(pos.down()));
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, BlockState state) {
        return this.canSustainBush(world.getBlockState(pos.down()));
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Water;
    }
}
