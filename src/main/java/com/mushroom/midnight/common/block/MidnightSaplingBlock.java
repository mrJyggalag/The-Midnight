package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;
import java.util.function.Supplier;

public class MidnightSaplingBlock extends BushBlock implements IGrowable {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.8, 0.9);

    private final Supplier<WorldGenerator> generatorSupplier;

    public MidnightSaplingBlock(Supplier<WorldGenerator> generatorSupplier) {
        this.setSoundType(SoundType.PLANT);

        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSapling.STAGE, 0));
        this.setCreativeTab(MidnightItemGroups.DECORATION);

        this.generatorSupplier = generatorSupplier;
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDS;
    }

    @Override
    public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
        if (!world.isRemote) {
            super.updateTick(world, pos, state, rand);

            if (!world.isBlockLoaded(pos)) {
                return;
            }

            if (world.getLightFromNeighbors(pos.up()) < 9 && rand.nextInt(7) == 0) {
                this.grow(world, rand, pos, state);
            }
        }
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return world.rand.nextFloat() < 0.45;
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
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }

    @Override
    public int damageDropped(BlockState state) {
        return 0;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockSapling.STAGE, meta & 1);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.get(BlockSapling.STAGE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockSapling.STAGE);
    }
}
