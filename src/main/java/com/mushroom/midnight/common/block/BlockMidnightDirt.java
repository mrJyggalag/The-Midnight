package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;

public class BlockMidnightDirt extends Block implements IModelProvider {
    public BlockMidnightDirt() {
        super(Material.GROUND, MapColor.BLUE_STAINED_HARDENED_CLAY);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GROUND);
        this.setCreativeTab(ModTabs.BUILDING_TAB);
        this.setHarvestLevel("shovel", 0);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return true;
    }
}
