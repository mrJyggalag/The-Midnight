package com.mushroom.midnight.common.item;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.item.Item;

public class ItemBasic extends Item implements IModelProvider {
    public ItemBasic() {
        super();
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
    }
}
