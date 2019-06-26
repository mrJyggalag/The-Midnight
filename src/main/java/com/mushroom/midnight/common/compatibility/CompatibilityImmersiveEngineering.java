package com.mushroom.midnight.common.compatibility;

import blusunrize.immersiveengineering.api.tool.ExternalHeaterHandler;
import com.mushroom.midnight.common.block.MidnightFurnaceBlock;
import com.mushroom.midnight.common.tile.base.TileEntityMidnightFurnace;
import net.minecraft.block.state.BlockState;
import net.minecraft.tileentity.TileEntity;

public class CompatibilityImmersiveEngineering {
    public static final CompatibilityImmersiveEngineering instance = new CompatibilityImmersiveEngineering();

    private CompatibilityImmersiveEngineering() {
    }

    public void register() {
        ExternalHeaterHandler.registerHeatableAdapter(TileEntityMidnightFurnace.class, new ExternalHeaterHandler.DefaultFurnaceAdapter() {
            @Override
            public void updateFurnace(TileEntity tileEntity, boolean active) {
                if (tileEntity.getBlockType() instanceof MidnightFurnaceBlock) {
                    MidnightFurnaceBlock.setState(active, tileEntity.getWorld(), tileEntity.getPos());
                    BlockState state = tileEntity.getWorld().getBlockState(tileEntity.getPos());
                    tileEntity.getWorld().notifyBlockUpdate(tileEntity.getPos(), state, state, 3);
                }
            }
        });
    }
}
