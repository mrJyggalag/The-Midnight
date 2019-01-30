package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ModArmorMaterials {
    /*public static final ArmorMaterial MATERIAL_NAME = EnumHelper.addArmorMaterial("armor_material_" + "material name",
             Midnight.MODID + ":materialname"(path:"textures/models/armor/" + "materialname" + "_layer_1.png" and "_layer_2.png"), durability,
             reduceamount{boots, leggings, chest, helmet}, enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, toughness);*/
    public static final ArmorMaterial ROCKSHROOM = EnumHelper.addArmorMaterial(
            "armor_material_rockshroom", Midnight.MODID + ":rockshroom",
            15, new int[] { 2, 4, 3, 1 }, 5,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            1.0F
    );
    public static final ArmorMaterial TENEBRUM = EnumHelper.addArmorMaterial(
            "armor_material_tenebrum", Midnight.MODID + ":tenebrum",
            30, new int[] { 3, 6, 8, 3 }, 9,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            2.0F
    );
}
