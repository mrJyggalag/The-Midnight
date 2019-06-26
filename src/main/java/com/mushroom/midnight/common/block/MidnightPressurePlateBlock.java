package com.mushroom.midnight.common.block;

import java.util.List;

import javax.annotation.Nullable;

import com.mushroom.midnight.common.registry.MidnightItemGroups;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

public class MidnightPressurePlateBlock extends BlockPressurePlate {
    private final boolean wooden;
    
    public MidnightPressurePlateBlock(boolean wooden, float hardness) {
        super(wooden ? Material.WOOD : Material.ROCK, wooden ? BlockPressurePlate.Sensitivity.EVERYTHING : BlockPressurePlate.Sensitivity.MOBS);
        this.setCreativeTab(MidnightItemGroups.DECORATION);
        this.wooden = wooden;
        this.setHardness(hardness);
        this.setSoundType(this.wooden ? SoundType.WOOD : SoundType.STONE);
    }
    
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.midnight.pressure_plate.weightless"));
    }
    
}
