package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class MidnightGemBlock extends Block {
    private final Supplier<Item> gemSupplier;

    public MidnightGemBlock(Supplier<Item> gemSupplier, int harvestLevel) {
        super(Material.ROCK);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(MidnightItemGroups.BUILDING);
        this.setHarvestLevel("pickaxe", harvestLevel);
        this.gemSupplier = gemSupplier;
    }

    @Override
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
    }
}
