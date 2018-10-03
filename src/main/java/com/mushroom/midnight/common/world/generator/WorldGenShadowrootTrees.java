package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.common.registry.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class WorldGenShadowrootTrees extends WorldGenTrees {

  public WorldGenShadowrootTrees() {
    super(true, 4, ModBlocks.SHADOWROOT_LOG.getDefaultState(), ModBlocks.SHADOWROOT_LEAVES.getDefaultState(), false);
  }

  @Override
  protected boolean canGrowInto(Block blockType) {
    Material material = blockType.getDefaultState().getMaterial();
    return material == Material.AIR || material == Material.LEAVES || blockType == Blocks.GRASS || blockType == Blocks.DIRT || blockType == Blocks.LOG ||
        blockType == Blocks.LOG2 || blockType == Blocks.SAPLING || blockType == Blocks.VINE || blockType == ModBlocks.MIDNIGHT_DIRT || blockType == ModBlocks.MIDNIGHT_GRASS;

  }
}
