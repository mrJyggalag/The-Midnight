package com.mushroom.midnight.common.item.tools;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.item.ItemPickaxe;

public class ItemMidnightPickaxe extends ItemPickaxe implements IModelProvider {
    public ItemMidnightPickaxe(ToolMaterial material) {
        super(material);
        this.setCreativeTab(Midnight.MIDNIGHT_TOOLS);
    }
}
