package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockDeceitfulAlgae extends BlockBush implements IModelProvider {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.09375, 0.9375);

    public BlockDeceitfulAlgae() {
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDS;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.WATER;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        IBlockState ground = world.getBlockState(pos.down());
        Material material = ground.getMaterial();
        return material == Material.WATER && ground.getValue(BlockLiquid.LEVEL) == 0 || material == Material.ICE;
    }
}
