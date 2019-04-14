package com.mushroom.midnight.common.block;

import java.util.List;

import javax.annotation.Nullable;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
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

public class BlockMidnightPressurePlate extends BlockPressurePlate implements IModelProvider {
    private final boolean wooden;
    
    public BlockMidnightPressurePlate(boolean wooden, float hardness) {
        super(wooden ? Material.WOOD : Material.ROCK, wooden ? BlockPressurePlate.Sensitivity.EVERYTHING : BlockPressurePlate.Sensitivity.MOBS);
        this.setCreativeTab(ModTabs.DECORATION_TAB);
        this.wooden = wooden;
        this.setHardness(hardness);
        this.setSoundType(this.wooden ? SoundType.WOOD : SoundType.STONE);
    }

    @Override
    protected void playClickOnSound(World worldIn, BlockPos pos) {
        if (this.wooden) {
            worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.8F);
        } else {
            worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }
    }

    @Override
    protected void playClickOffSound(World worldIn, BlockPos pos) {
        if (this.wooden) {
            worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.8F);
        } else {
            worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.translateToLocal("tooltip.midnight.pressure_plate.weightless"));
    }
    
}
