package com.mushroom.midnight.common.item.tools;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.item.ItemHoe;

public class ItemMidnightHoe extends ItemHoe implements IModelProvider {
    public ItemMidnightHoe(ToolMaterial material) {
        super(material);
        this.setCreativeTab(Midnight.MIDNIGHT_TOOLS);
    }
}
