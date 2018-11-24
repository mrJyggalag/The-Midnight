package com.mushroom.midnight.common.item.tools;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;

public class ItemMidnightPickaxe extends net.minecraft.item.ItemPickaxe implements IModelProvider {
    public ItemMidnightPickaxe(ToolMaterial material) {
        super(material);
        this.setCreativeTab(Midnight.MIDNIGHT_ITEMS);
    }
}
