package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class MidnightDoubleFungiBlock extends MidnightDoublePlantBlock implements IGrowable {
    private final Supplier<WorldGenerator> generatorSupplier;

    public MidnightDoubleFungiBlock(Supplier<WorldGenerator> generatorSupplier) {
        super(true);
        this.setDefaultState(this.getStateContainer().getBaseState().with(BlockStateProperties.STAGE_0_1, 0));
        this.generatorSupplier = generatorSupplier;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isNormalCube(world, pos);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return world.rand.nextFloat() < 0.75F;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        if (state.get(BlockSapling.STAGE) == 0) {
            world.setBlockState(pos, state.cycleProperty(BlockSapling.STAGE), 4);
        } else if (TerrainGen.saplingGrowTree(world, rand, pos)) {
            WorldGenerator generator = this.generatorSupplier.get();
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            if (!generator.generate(world, rand, pos)) {
                world.setBlockState(pos, state, 4);
            }
        }
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HALF, BlockStateProperties.STAGE_0_1);
    }
}
