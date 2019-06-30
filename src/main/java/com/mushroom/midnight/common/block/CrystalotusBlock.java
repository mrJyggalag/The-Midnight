package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrystalotusBlock extends Block  {

    private static final VoxelShape BOUNDS = makeCuboidShape(0.1, 0.0, 0.1, 0.9, 0.8, 0.9);

    public CrystalotusBlock() {
        super(Properties.create(Material.PLANTS).hardnessAndResistance(2f, 0f).sound(SoundType.GLASS).tickRandomly().lightValue(3));
    }

    @Override
    public boolean isSolid(BlockState state) {
        return true;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 1;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return BOUNDS;
    }

    @Override
    public boolean isFullCube(@Nonnull BlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(@Nonnull BlockState state) {
        return false;
    }
}
