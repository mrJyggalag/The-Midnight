package com.mushroom.midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GhostPlantBlock extends MidnightPlantBlock {
    public GhostPlantBlock() {
        super(true);
        this.setLightLevel(0.8F);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
        int skyLight = 12;
        int blockLight = 12;
        return skyLight << 20 | blockLight << 4;
    }
}
