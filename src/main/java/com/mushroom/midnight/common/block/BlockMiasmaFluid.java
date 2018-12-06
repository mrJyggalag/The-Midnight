package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockMiasmaFluid extends BlockMixableFluid implements IModelProvider {
    public BlockMiasmaFluid() {
        super(ModFluids.MIASMA, Material.LAVA);
        this.setRenderLayer(BlockRenderLayer.SOLID);
    }

    @Override
    public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
        return new Vec3d(0.6, 0.6, 1.0);
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return 15;
    }

    @Nullable
    @Override
    protected IBlockState getMixState(IBlockState otherState) {
        if (otherState.getMaterial() == Material.WATER) {
            return ModBlocks.NIGHTSTONE.getDefaultState();
        } else if (otherState.getBlock() == Blocks.LAVA) {
            return ModBlocks.MIASMA_SURFACE.getDefaultState();
        }
        return null;
    }
}
