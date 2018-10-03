package com.mushroom.midnight.common.blocks;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public final class BlockBasic extends Block implements IModelProvider {
    public BlockBasic(Material material) {
        super(material);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }
}
