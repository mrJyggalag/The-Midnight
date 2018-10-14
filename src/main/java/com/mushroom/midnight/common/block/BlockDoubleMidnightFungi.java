package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;

public class BlockDoubleMidnightFungi extends BlockDoubleMidnightPlant implements IMidnightFungi {
    public BlockDoubleMidnightFungi() {
        super();
        this.setLightLevel(0.8F);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return super.canSustainBush(state) || state.getBlock() == ModBlocks.NIGHTSTONE;
    }
}
