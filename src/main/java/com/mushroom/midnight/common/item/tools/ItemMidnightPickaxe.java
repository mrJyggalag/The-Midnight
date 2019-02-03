package com.mushroom.midnight.common.item.tools;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.item.ItemPickaxe;

public class ItemMidnightPickaxe extends ItemPickaxe implements IModelProvider {
    public ItemMidnightPickaxe(ToolMaterial material) {
        super(material);
        this.setCreativeTab(ModTabs.MIDNIGHT_TOOLS);
    }
}
