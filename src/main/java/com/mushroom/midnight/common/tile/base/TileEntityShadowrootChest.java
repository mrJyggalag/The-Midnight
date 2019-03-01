package com.mushroom.midnight.common.tile.base;

import com.mushroom.midnight.common.block.BlockShadowrootChest;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TileEntityShadowrootChest extends TileEntityChest {

    @Override
    public String getName() {
        return hasCustomName() ? customName : "tile.midnight.shadowroot_chest.name";
    }

    @Nullable
    protected TileEntityChest getAdjacentChest(EnumFacing side) {
        BlockPos adjacentPos = this.pos.offset(side);

        if (this.isChestAt(adjacentPos)) {
            TileEntity entity = this.world.getTileEntity(adjacentPos);
            if (entity instanceof TileEntityShadowrootChest) {
                TileEntityShadowrootChest chest = (TileEntityShadowrootChest) entity;
                chest.setNeighbor(this, side.getOpposite());
                return chest;
            }
        }

        return null;
    }

    private void setNeighbor(TileEntityShadowrootChest entity, EnumFacing side) {
        if (entity.isInvalid()) {
            this.adjacentChestChecked = false;
            return;
        }

        if (this.adjacentChestChecked) {
            switch (side) {
                case NORTH:
                    if (this.adjacentChestZNeg != entity) {
                        this.adjacentChestChecked = false;
                    }
                    break;
                case SOUTH:
                    if (this.adjacentChestZPos != entity) {
                        this.adjacentChestChecked = false;
                    }
                    break;
                case EAST:
                    if (this.adjacentChestXPos != entity) {
                        this.adjacentChestChecked = false;
                    }
                    break;
                case WEST:
                    if (this.adjacentChestXNeg != entity) {
                        this.adjacentChestChecked = false;
                    }
                    break;
            }
        }
    }

    private boolean isChestAt(BlockPos pos) {
        if (this.world == null) {
            return false;
        }
        Block block = this.world.getBlockState(pos).getBlock();
        return block instanceof BlockShadowrootChest;
    }
}
