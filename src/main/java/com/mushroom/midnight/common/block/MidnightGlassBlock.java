package com.mushroom.midnight.common.block;

import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MidnightGlassBlock extends GlassBlock {
    public MidnightGlassBlock() {
        super(Properties.create(Material.GLASS).hardnessAndResistance(0.3f, 0f).sound(SoundType.GLASS));
        //setCreativeTab(MidnightItemGroups.BUILDING);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
