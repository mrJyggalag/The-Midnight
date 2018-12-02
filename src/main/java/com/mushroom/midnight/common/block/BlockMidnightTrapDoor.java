package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockMidnightTrapDoor extends BlockTrapDoor implements IModelProvider {
    public BlockMidnightTrapDoor() {
        this(false);
    }

    public BlockMidnightTrapDoor(boolean isMetal) {
        super(isMetal ? Material.IRON : Material.WOOD);
        this.setHardness(isMetal ? 5f : 3f);
        this.setSoundType(isMetal ? SoundType.METAL : SoundType.WOOD);
        this.setCreativeTab(Midnight.DECORATION_TAB);
    }
}
