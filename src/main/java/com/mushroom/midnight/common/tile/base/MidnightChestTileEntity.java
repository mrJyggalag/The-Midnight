package com.mushroom.midnight.common.tile.base;

import com.mushroom.midnight.common.block.MidnightChestBlock;
import com.mushroom.midnight.common.registry.MidnightTileEntities;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class MidnightChestTileEntity extends ChestTileEntity {
    private MidnightChestBlock.MidnightChestModel chestModel = null;

    protected MidnightChestTileEntity(TileEntityType<?> entityType) {
        super(entityType);
    }

    public MidnightChestTileEntity() {
        this(MidnightTileEntities.MIDNIGHT_CHEST);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(
                this.pos.add(-1, 0, -1),
                this.pos.add(2, 2, 2)
        );
    }

    public void setChestModel(MidnightChestBlock.MidnightChestModel chestModel) {
        this.chestModel = chestModel;
    }

    public MidnightChestBlock.MidnightChestModel getChestModel() {
        return this.chestModel != null ? this.chestModel : MidnightChestBlock.MidnightChestModel.getDefault();
    }
}
