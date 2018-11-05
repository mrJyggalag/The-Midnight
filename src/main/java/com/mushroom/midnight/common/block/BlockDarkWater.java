package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockDarkWater extends BlockFluidClassic implements IModelProvider {
    public BlockDarkWater() {
        super(ModFluids.DARK_WATER, Material.WATER);
    }
}
