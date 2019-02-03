package com.mushroom.midnight.common.item.tools;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.item.ItemSword;

public class ItemMidnightSword extends ItemSword implements IModelProvider {
    public ItemMidnightSword(ToolMaterial material) {
        super(material);
        this.setCreativeTab(ModTabs.MIDNIGHT_COMBAT);
    }
}
