package com.mushroom.midnight.common.item.tools;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.item.ItemSword;

public class ItemMidnightSword extends ItemSword implements IModelProvider {
    public ItemMidnightSword(ToolMaterial material) {
        super(material);
        this.setCreativeTab(Midnight.MIDNIGHT_COMBAT);
    }
}
