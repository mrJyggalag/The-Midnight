package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSuavis extends BlockGlowingPlant{
    public BlockSuavis() {
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.RAW_SUAVIS;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(worldIn, pos.getX(), pos.getY(), pos.getZ());
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setRadiusOnUse(-0.5F);
        entityareaeffectcloud.setWaitTime(10);
        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
        entityareaeffectcloud.setPotion(PotionTypes.EMPTY);
        entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.NAUSEA, 20*30, 0, false, true));

//        NBTTagCompound nbttagcompound = new PotionEffect(MobEffects.NAUSEA, 20*30, 0, false, true).getTagCompound();
//
//        if (nbttagcompound != null && nbttagcompound.hasKey("CustomPotionColor", 99))
//        {
//            entityareaeffectcloud.setColor(nbttagcompound.getInteger("CustomPotionColor"));
//        }

        worldIn.spawnEntity(entityareaeffectcloud);
    }
}
