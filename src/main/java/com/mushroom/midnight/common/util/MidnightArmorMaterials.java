package com.mushroom.midnight.common.util;

import com.mushroom.midnight.Midnight;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MidnightArmorMaterials {
    /*public static final ArmorMaterial MATERIAL_NAME = EnumHelper.addArmorMaterial("armor_material_" + "material name", Midnight.MODID + ":materialname", durability,
              reduceamount{boots, chestplate, leggings, helmet}, enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, toughness);*/
    public static final ArmorMaterial ROCKSHROOM = EnumHelper.addArmorMaterial("armor_material_rockshroom", Midnight.MODID + ":rockshroom", 1,
            new int[]{1,2,3,4}, 1, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F);
    public static final ArmorMaterial TENEBRUM = EnumHelper.addArmorMaterial("armor_material_tenebrum", Midnight.MODID + ":tenebrum", 1,
            new int[]{1,2,3,4}, 1, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F);
}
