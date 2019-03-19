package com.mushroom.midnight.common.compatibility;

import blusunrize.immersiveengineering.api.tool.ExternalHeaterHandler;
import com.mushroom.midnight.common.block.BlockMidnightFurnace;
import com.mushroom.midnight.common.tile.base.TileEntityMidnightFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

public class CompatibilityImmersiveEngineering {
    public static final CompatibilityImmersiveEngineering instance = new CompatibilityImmersiveEngineering();

    private CompatibilityImmersiveEngineering() {
    }

    public void register() {
        ExternalHeaterHandler.registerHeatableAdapter(TileEntityMidnightFurnace.class, new ExternalHeaterHandler.DefaultFurnaceAdapter() {
            @Override
            public void updateFurnace(TileEntity tileEntity, boolean active) {
                if (tileEntity.getBlockType() instanceof BlockMidnightFurnace) {
                    BlockMidnightFurnace.setState(active, tileEntity.getWorld(), tileEntity.getPos());
                    IBlockState state = tileEntity.getWorld().getBlockState(tileEntity.getPos());
                    tileEntity.getWorld().notifyBlockUpdate(tileEntity.getPos(), state, state, 3);
                }
            }
        });
    }
}
