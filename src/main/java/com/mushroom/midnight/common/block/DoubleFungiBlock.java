package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class DoubleFungiBlock extends MidnightDoublePlantBlock {
    private final Supplier<IWorldGenerator> generatorSupplier;

    public DoubleFungiBlock(Properties properties, @Nullable Supplier<IWorldGenerator> generatorSupplier) {
        super(properties, true);
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
        return this.generatorSupplier != null && world.rand.nextFloat() < 0.75F;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        if (state.get(BlockStateProperties.STAGE_0_1) == 0) {
            world.setBlockState(pos, state.cycle(BlockStateProperties.STAGE_0_1), 4);
        } else if (!ForgeEventFactory.saplingGrowTree(world, rand, pos)) {
            IWorldGenerator generator = this.generatorSupplier.get();
            // TODO Feature @Gegy
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            if (!generator.generate(world, rand, pos)) {
                world.setBlockState(pos, state, 4);
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        if (this.generatorSupplier != null) {
            builder.add(BlockStateProperties.STAGE_0_1);
        }
    }
}
