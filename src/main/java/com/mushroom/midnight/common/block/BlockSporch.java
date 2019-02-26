package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSporch extends BlockTorch implements IModelProvider {

    public BlockSporch() {
        super();
        setCreativeTab(ModTabs.DECORATION_TAB);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        EnumFacing enumfacing = state.getValue(FACING);
        double d0 = (double) pos.getX() + 0.5d;
        double d1 = (double) pos.getY() + 0.7d;
        double d2 = (double) pos.getZ() + 0.5d;
        double d3 = 0.22d;
        double d4 = 0.27d;
        if (enumfacing.getAxis().isHorizontal()) {
            EnumFacing facing = enumfacing.getOpposite();
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * (double) facing.getXOffset(), d1 + d3, d2 + d4 * (double) facing.getZOffset(), 0d, 0d, 0d);
            world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4 * (double) facing.getXOffset(), d1 + d3, d2 + d4 * (double) facing.getZOffset(), 0d, 0d, 0d);
        } else {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0d, 0d, 0d);
            world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0d, 0d, 0d);
        }
    }
}
