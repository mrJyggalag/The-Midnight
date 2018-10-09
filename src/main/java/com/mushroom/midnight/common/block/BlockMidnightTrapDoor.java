package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockMidnightTrapDoor extends BlockTrapDoor implements IModelProvider {
    public BlockMidnightTrapDoor() {
        super(Material.WOOD);
        this.setHardness(3.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
    }
}
