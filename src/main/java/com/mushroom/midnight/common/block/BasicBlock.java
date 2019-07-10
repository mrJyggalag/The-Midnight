package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("FieldCanBeLocal")
public class BasicBlock extends Block {
    private boolean glowing;

    public BasicBlock(Properties properties) {
        super(properties);
    }

    public BasicBlock withGlow() {
        this.glowing = true;
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader source, BlockPos pos) {
        if (glowing) {
            int skyLight = 15;
            int blockLight = 15;
            return skyLight << 20 | blockLight << 4;
        }
        return super.getPackedLightmapCoords(state, source, pos);
    }
}
