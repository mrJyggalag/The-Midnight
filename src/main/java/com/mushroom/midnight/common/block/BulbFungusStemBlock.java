package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IShearable;

import java.util.List;
import java.util.Random;

public class BulbFungusStemBlock extends BlockMidnightLog implements IShearable {

    public BulbFungusStemBlock() {
        super();
        setHardness(0.5f);
    }

    @Override
    public int quantityDropped(Random random) {
        return 4;
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return MidnightItems.BULB_FUNGUS_HAND;
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        NonNullList<ItemStack> list = NonNullList.create();
        list.add(new ItemStack(this));
        return list;
    }
}
