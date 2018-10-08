package com.mushroom.midnight.common.item;

import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;

public class ItemMidnightDoor extends ItemDoor implements IModelProvider {
    public ItemMidnightDoor(Block block) {
        super(block);
    }
}
