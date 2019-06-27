package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class CrystalotusBlock extends Block  {

    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.8, 0.9);

    public CrystalotusBlock() {
        super(Material.PLANTS);

        this.setHardness(2.0F);
        this.setSoundType(SoundType.GLASS);
        this.setHarvestLevel("pickaxe", 1);
        this.setTickRandomly(true);
        this.setCreativeTab(MidnightItemGroups.DECORATION);
        this.setLightLevel(0.2F);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
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
