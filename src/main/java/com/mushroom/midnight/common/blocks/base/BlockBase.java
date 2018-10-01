package com.mushroom.midnight.common.blocks.base;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IMidnightBlock, IModelProvider {

    protected String registryName;

    public BlockBase(Material material, String registryName) {
        super(material);

        this.registryName = registryName;

        setUnlocalizedName(Midnight.MODID + "." + registryName);
        setRegistryName(registryName);
    }

    @Override
    public ItemBlock getItem() {
        return new ItemBlock(this);
    }
}