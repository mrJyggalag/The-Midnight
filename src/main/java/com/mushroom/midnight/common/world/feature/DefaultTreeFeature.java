package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.block.BlockMidnightLeaves;
import com.mushroom.midnight.common.block.BlockMidnightLog;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class DefaultTreeFeature extends WorldGenTrees implements IMidnightFeature {
    public DefaultTreeFeature(Block log, Block leaves, int height) {
        super(false, height, log.getDefaultState(), leaves.getDefaultState(), false);
    }

    @Override
    protected boolean canGrowInto(Block blockType) {
        Material material = blockType.getDefaultState().getMaterial();
        return blockType instanceof BlockMidnightLog || blockType instanceof BlockMidnightLeaves
                || blockType == ModBlocks.MIDNIGHT_DIRT || blockType == ModBlocks.MIDNIGHT_GRASS
                || material == Material.VINE;
    }
}
