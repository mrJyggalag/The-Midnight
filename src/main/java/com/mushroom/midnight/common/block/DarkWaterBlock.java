package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightFluids;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DarkWaterBlock extends MixableFluidBlock {
    public DarkWaterBlock() {
        super(MidnightFluids.DARK_WATER, Material.WATER);
        this.setLightOpacity(3);
    }

    @Override
    public Vec3d getFogColor(World world, BlockPos pos, BlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
        return new Vec3d(0.2, 0.1, 0.5);
    }

    @Nullable
    @Override
    protected BlockState getMixState(BlockState otherState) {
        if (otherState.getMaterial() == Material.LAVA) {
            return MidnightBlocks.TRENCHSTONE.getDefaultState();
        } else if (otherState.getBlock() != this && otherState.getMaterial() == Material.WATER) {
            return Blocks.AIR.getDefaultState();
        }
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public MaterialColor getMapColor(BlockState state, IBlockAccess world, BlockPos pos) {
        return MaterialColor.BLUE;
    }
}
