package com.mushroom.midnight.common.item.armors;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ItemMidnightArmor extends ItemArmor implements IModelProvider {
    public ItemMidnightArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        this.setCreativeTab(ModTabs.MIDNIGHT_COMBAT);
    }
}
