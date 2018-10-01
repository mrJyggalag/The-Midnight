package com.mushroom.midnight.common.blocks;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.world.MidnightTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockRiftBlock extends Block implements IModelProvider {

    public BlockRiftBlock() {
        super(Material.PORTAL);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos) {
        return NULL_AABB;
    }

    // TODO: This behaviour will change
    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (!world.isRemote && this.canTransport(entity)) {
            entity.changeDimension(this.getTransportDimension(world).getId(), MidnightTeleporter.INSTANCE);
        }
    }

    private boolean canTransport(Entity entity) {
        return !entity.isRiding() && !entity.isBeingRidden();
    }

    private DimensionType getTransportDimension(World world) {
        if (world.provider.getDimensionType() == ModDimensions.MIDNIGHT) {
            return DimensionType.OVERWORLD;
        } else {
            return ModDimensions.MIDNIGHT;
        }
    }
}
