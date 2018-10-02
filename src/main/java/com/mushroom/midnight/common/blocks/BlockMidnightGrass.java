package com.mushroom.midnight.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMidnightGrass extends BlockBasic {

  public BlockMidnightGrass(Material material) {
    super(material);
  }

  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer()
  {
    return BlockRenderLayer.CUTOUT_MIPPED;
  }
}
