package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockMidnightTrapDoor extends BlockTrapDoor implements IModelProvider {
    public BlockMidnightTrapDoor() {
        this(false);
    }

    public BlockMidnightTrapDoor(boolean metallic) {
        super(metallic ? Material.IRON : Material.WOOD);
        this.setHardness(metallic ? 5.0F : 3.0F);
        this.setSoundType(metallic ? SoundType.METAL : SoundType.WOOD);
        this.setCreativeTab(ModTabs.DECORATION_TAB);
    }
}
