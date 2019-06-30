package com.mushroom.midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;
import java.util.function.Supplier;

public class BulbFungusBlock extends MidnightPlantBlock implements IGrowable {
    private final Supplier<Feature<?>> genSupplier;

    public BulbFungusBlock(Properties properties, Supplier<Feature<?>> genSupplier) {
        super(properties, true);
        this.genSupplier = genSupplier;
    }

    // quantityDropped 1 BULB_FUNGUS_HAND (+ SilkTouch + Shearable)

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return world.rand.nextFloat() < 0.75f;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        if (!ForgeEventFactory.saplingGrowTree(world, rand, pos)) {
            Feature<?> generator = this.genSupplier.get();
            // TODO Feature @Gegy
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            if (!generator.generate(world, rand, pos)) {
                world.setBlockState(pos, state, 4);
            }
        }
    }
}
