package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MidnightTrapDoorBlock extends BlockTrapDoor {
    public MidnightTrapDoorBlock() {
        this(false);
    }

    public MidnightTrapDoorBlock(boolean metallic) {
        super(metallic ? Material.IRON : Material.WOOD);
        this.setHardness(metallic ? 5.0F : 3.0F);
        this.setSoundType(metallic ? SoundType.METAL : SoundType.WOOD);
        this.setCreativeTab(MidnightItemGroups.DECORATION);
    }
}
