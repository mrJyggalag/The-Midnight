package com.mushroom.midnight.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGhostPlant extends BlockMidnightPlant {
    public BlockGhostPlant() {
        super(true);
        this.setLightLevel(0.8F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        int skyLight = 12;
        int blockLight = 12;
        return skyLight << 20 | blockLight << 4;
    }
}
