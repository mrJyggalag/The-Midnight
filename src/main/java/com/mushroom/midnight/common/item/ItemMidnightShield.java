package com.mushroom.midnight.common.item;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;

public class ItemMidnightShield extends ItemShield implements IModelProvider {
    private final ArmorMaterial material;

    public ItemMidnightShield(ArmorMaterial material) {
        super();
        this.material = material;
        setMaxDamage(material.getDurability(EntityEquipmentSlot.OFFHAND) * 2);
        setCreativeTab(ModTabs.MIDNIGHT_COMBAT);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == material.getRepairItemStack().getItem();
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
        return true;
    }
}
