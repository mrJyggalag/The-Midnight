package com.mushroom.midnight.common.block;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;
import java.util.function.Supplier;

public class BlockBulbFungus extends BlockMidnightPlant implements IGrowable {
    private final Supplier<WorldGenerator> genSupplier;

    public BlockBulbFungus() {
        this(null);
    }

    public BlockBulbFungus(Supplier<WorldGenerator> genSupplier) {
        super(true);
        this.genSupplier = genSupplier;
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return false; //world.rand.nextFloat() < 0.75f;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        /*if (TerrainGen.saplingGrowTree(world, rand, pos)) {
            WorldGenerator generator = this.genSupplier.get();
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            if (!generator.generate(world, rand, pos)) {
                world.setBlockState(pos, state, 4);
            }
        }*/
    }
}
