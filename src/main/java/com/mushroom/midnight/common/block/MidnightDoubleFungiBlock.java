package com.mushroom.midnight.common.block;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;
import java.util.function.Supplier;

public class MidnightDoubleFungiBlock extends MidnightDoublePlantBlock implements IGrowable {
    private final Supplier<WorldGenerator> generatorSupplier;

    public MidnightDoubleFungiBlock(Supplier<WorldGenerator> generatorSupplier) {
        super(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSapling.STAGE, 0));
        this.generatorSupplier = generatorSupplier;
    }

    @Override
    protected boolean canSustainBush(BlockState state) {
        return state.isBlockNormalCube();
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, BlockState state, boolean isClient) {
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
    public int damageDropped(BlockState state) {
        return 0;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, BlockSapling.STAGE);
    }
}
