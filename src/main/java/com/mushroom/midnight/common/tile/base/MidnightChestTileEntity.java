package com.mushroom.midnight.common.tile.base;

import com.mushroom.midnight.common.registry.MidnightTileEntities;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class MidnightChestTileEntity extends ChestTileEntity {
    protected MidnightChestTileEntity(TileEntityType<?> entityType) {
        super(entityType);
    }

    public MidnightChestTileEntity() {
        this(MidnightTileEntities.MIDNIGHT_CHEST);
    }
}
