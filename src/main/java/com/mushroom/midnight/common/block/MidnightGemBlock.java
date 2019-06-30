package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MidnightGemBlock extends Block {
    private final Supplier<Item> gemSupplier;
    private final int harvestLevel;

    public MidnightGemBlock(Supplier<Item> gemSupplier, int harvestLevel) {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3f, 5f).sound(SoundType.STONE));
        this.harvestLevel = harvestLevel;
        this.gemSupplier = gemSupplier;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return this.harvestLevel;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    /*@Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return this.gemSupplier.get();
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0) {
            int quantity = random.nextInt(fortune + 2) - 1;
            return Math.max(quantity, 0) + 1;
        }
        return 1;
    }

    @Override
    public int getExpDrop(BlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        return MathHelper.getInt(rand, 3, 7);
    }*/
}
