package com.mushroom.midnight.common.block;

import java.util.List;

import javax.annotation.Nullable;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
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

    @Override
    protected void playClickOnSound(World worldIn, BlockPos pos) {
        worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_METAL_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.90000004F);
    }

    @Override
    protected void playClickOffSound(World worldIn, BlockPos pos) {
        worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_METAL_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.75F);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(this.heavyWeighted ? I18n.translateToLocal("tooltip.midnight.pressure_plate.heavy_weighted") : I18n.translateToLocal("tooltip.midnight.pressure_plate.light_weighted"));
    }
    
}
