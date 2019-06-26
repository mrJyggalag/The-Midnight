package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;

public final class GlowingBlock extends Block {
    public GlowingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
        int skyLight = 15;
        int blockLight = 15;
        return skyLight << 20 | blockLight << 4;
    }
}
