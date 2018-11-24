package com.mushroom.midnight.common.item.tools;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.item.ItemAxe;

public class ItemMidnightAxe extends ItemAxe implements IModelProvider {
    public ItemMidnightAxe(ToolMaterial material, float attackDamage, float attackSpeed) {
        super(material, attackDamage, attackSpeed);
        this.setCreativeTab(Midnight.MIDNIGHT_TOOLS);
    }
}
