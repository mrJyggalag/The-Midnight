package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

@SuppressWarnings("FieldCanBeLocal")
public class BasicBlock extends Block {
    private ToolType toolType;
    private int toolLevel;
    private boolean glowing;

    public BasicBlock(Properties properties) {
        this(properties, ToolType.PICKAXE, 0);
    }

    public BasicBlock(Properties properties, ToolType toolType, int toolLevel) {
        super(properties);
        this.toolType = toolType;
        this.toolLevel = toolLevel;
    }

    public BasicBlock withGlow() {
        this.glowing = true;
        return this;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return this.toolLevel;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return this.toolType;
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
