package com.mushroom.midnight.common.item;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.item.Item;

public class ItemBasic extends Item implements IModelProvider {
    public ItemBasic() {
        super();
        this.setCreativeTab(ModTabs.MIDNIGHT_ITEMS);
    }
}
