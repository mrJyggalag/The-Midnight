package com.mushroom.midnight.common.item.tools;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.item.ItemSpade;

public class ItemMidnightShovel extends ItemSpade implements IModelProvider {
    public ItemMidnightShovel(ToolMaterial material) {
        super(material);
        this.setCreativeTab(ModTabs.MIDNIGHT_TOOLS);
    }
}
