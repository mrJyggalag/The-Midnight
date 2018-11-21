package com.mushroom.midnight.common.item;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.item.ItemFood;

public class ItemFoodBasic extends ItemFood implements IModelProvider {
    public ItemFoodBasic(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setCreativeTab(Midnight.MIDNIGHT_ITEMS);
    }
}
