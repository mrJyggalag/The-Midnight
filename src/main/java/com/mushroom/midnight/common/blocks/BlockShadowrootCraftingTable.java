package com.mushroom.midnight.common.blocks;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockShadowrootCraftingTable extends Block implements IModelProvider {

    public BlockShadowrootCraftingTable() {
        super(Material.WOOD);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
    }
}
