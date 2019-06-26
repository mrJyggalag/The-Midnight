package com.mushroom.midnight.common.item.tool;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

import javax.annotation.Nullable;

public class MidnightShieldItem extends ShieldItem {
    private final IArmorMaterial material;

    public MidnightShieldItem(IArmorMaterial material, Item.Properties properties) {
        super(properties);
        this.material = material;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return this.material.getRepairMaterial().test(repair);
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return true;
    }
}
