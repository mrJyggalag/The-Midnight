package com.mushroom.midnight.common.item.tools;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.item.ItemSpade;

public class ItemMidnightShovel extends ItemSpade implements IModelProvider {
    public ItemMidnightShovel(ToolMaterial material) {
        super(material);
        this.setCreativeTab(Midnight.MIDNIGHT_TOOLS);
    }
}
