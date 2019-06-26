package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

public class MidnightGlassBlock extends BlockGlass {
    public MidnightGlassBlock() {
        super(Material.GLASS, false);
        this.setHardness(0.3F);
        this.setSoundType(SoundType.GLASS);
        this.setCreativeTab(MidnightItemGroups.BUILDING);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
