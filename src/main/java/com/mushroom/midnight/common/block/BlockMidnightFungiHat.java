package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.util.Random;
import java.util.function.Supplier;

public class BlockMidnightFungiHat extends BlockHugeMushroom implements IModelProvider {
    private final Supplier<Block> saplingSupplier;

    public BlockMidnightFungiHat(Supplier<Block> saplingSupplier) {
        super(Material.WOOD, MapColor.LIGHT_BLUE, Blocks.AIR);
        this.saplingSupplier = saplingSupplier;
        this.setHardness(1.0F);
        this.setLightLevel(1.0F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(5) == 0 ? 1 : 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this.saplingSupplier.get());
    }
}
