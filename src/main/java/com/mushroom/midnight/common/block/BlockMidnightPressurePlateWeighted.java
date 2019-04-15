package com.mushroom.midnight.common.block;

import java.util.List;

import javax.annotation.Nullable;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;

import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMidnightPressurePlateWeighted extends BlockPressurePlateWeighted implements IModelProvider {
    private final boolean heavyWeighted;
    
    public BlockMidnightPressurePlateWeighted(Material materialIn, boolean heavyWeighted, MapColor mapColorIn, float hardness) {
        super(materialIn, heavyWeighted ? 150 : 15, mapColorIn);
        this.heavyWeighted = heavyWeighted;
        this.setCreativeTab(ModTabs.DECORATION_TAB);
        this.setHardness(hardness);
        this.setSoundType(SoundType.METAL);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format(this.heavyWeighted ? "tooltip.midnight.pressure_plate.heavy_weighted" :"tooltip.midnight.pressure_plate.light_weighted"));
    }
    
}
