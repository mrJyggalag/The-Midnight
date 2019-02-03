package com.mushroom.midnight.common.item.tools;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.item.ItemHoe;

public class ItemMidnightHoe extends ItemHoe implements IModelProvider {
    public ItemMidnightHoe(ToolMaterial material) {
        super(material);
        this.setCreativeTab(ModTabs.MIDNIGHT_TOOLS);
    }
}
