package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightFluids;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MiasmaFluidBlock extends MixableFluidBlock {
    public MiasmaFluidBlock() {
        super(MidnightFluids.MIASMA, Material.LAVA);
        this.setRenderLayer(BlockRenderLayer.SOLID);
    }

    @Override
    public Vec3d getFogColor(World world, BlockPos pos, BlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
        return new Vec3d(0.6, 0.6, 1.0);
    }

    @Override
    public int getLightValue(@Nonnull BlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return 15;
    }

    @Nullable
    @Override
    protected BlockState getMixState(BlockState otherState) {
        if (otherState.getMaterial() == Material.WATER) {
            return MidnightBlocks.NIGHTSTONE.getDefaultState();
        } else if (otherState.getBlock() == Blocks.LAVA || otherState.getBlock() == Blocks.FLOWING_LAVA) {
            return MidnightBlocks.MIASMA_SURFACE.getDefaultState();
        }
        return null;
    }

    @Override
    @Nullable
    public PathNodeType getAiPathNodeType(BlockState state, IBlockAccess world, BlockPos pos) {
        return PathNodeType.LAVA;
    }

    @Override
    @SuppressWarnings("deprecation")
    public MapColor getMapColor(BlockState state, IBlockAccess world, BlockPos pos) {
        return MapColor.ICE;
    }
}
