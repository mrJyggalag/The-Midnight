package com.mushroom.midnight.common.block;

import javax.annotation.Nullable;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;

import net.minecraft.block.BlockButton;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMidnightButton extends BlockButton implements IModelProvider {
    private final boolean is_wood;
    
    public BlockMidnightButton(boolean isWood, float hardness) {
        super(isWood);
        this.setCreativeTab(ModTabs.DECORATION_TAB);
        this.is_wood = isWood;
        this.setHardness(hardness);
        this.setSoundType(this.is_wood ? SoundType.WOOD : SoundType.STONE);
    }

    @Override
    protected void playClickSound(@Nullable EntityPlayer player, World worldIn, BlockPos pos) {
        if (this.is_wood) {
            worldIn.playSound(player, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        } else {
            worldIn.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }
    }

    @Override
    protected void playReleaseSound(World worldIn, BlockPos pos) {
        if (this.is_wood) {
            worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.6F);
        } else {
            worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }
    }

}
