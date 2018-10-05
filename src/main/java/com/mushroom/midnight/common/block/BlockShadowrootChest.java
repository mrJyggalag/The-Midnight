package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockShadowrootChest extends Block implements IModelProvider {

    public BlockShadowrootChest() {
        super(Material.WOOD);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
    }
}
