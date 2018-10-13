package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockMiasmaFluid extends BlockFluidClassic implements IModelProvider {
    public BlockMiasmaFluid() {
        super(ModFluids.MIASMA, Material.LAVA);
        this.setLightLevel(1.0F);
    }
}
