package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.capability.CavernousBiomeStore;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModTabs;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockMidnightGrass extends Block implements IGrowable, IModelProvider {
    public BlockMidnightGrass() {
        super(Material.GRASS, MapColor.MAGENTA_STAINED_HARDENED_CLAY);
        this.setHardness(0.6F);
        this.setSoundType(SoundType.PLANT);
        this.setTickRandomly(true);
        this.setCreativeTab(ModTabs.BUILDING_TAB);
        this.setHarvestLevel("shovel", 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote || !world.isAreaLoaded(pos, 3)) {
            return;
        }

        BlockPos abovePos = pos.up();
        if (!Helper.isMidnightDimension(world) || world.getBlockState(abovePos).getLightOpacity(world, abovePos) > 2) {
            world.setBlockState(pos, ModBlocks.MIDNIGHT_DIRT.getDefaultState());
            return;
        }

        for (int i = 0; i < 4; ++i) {
            BlockPos spreadPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
            if (spreadPos.getY() >= 0 && spreadPos.getY() < 256 && !world.isBlockLoaded(spreadPos)) {
                return;
            }

            IBlockState surfaceState = world.getBlockState(spreadPos);
            if (surfaceState.getBlock() == ModBlocks.MIDNIGHT_DIRT) {
                IBlockState coverState = world.getBlockState(spreadPos.up());
                if (coverState.getLightOpacity(world, spreadPos.up()) <= 2) {
                    world.setBlockState(spreadPos, this.getDefaultState());
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return true;
    }

    @Override
    public void onPlantGrow(IBlockState state, World world, BlockPos pos, BlockPos source) {
        world.setBlockState(pos, ModBlocks.MIDNIGHT_DIRT.getDefaultState(), 2);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.MIDNIGHT_DIRT);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        BlockPos blockpos = pos.up();
        label35:
        for (int i = 0; i < 128; ++i) {
            BlockPos blockpos1 = blockpos;
            for (int j = 0; j < i / 16; ++j) {
                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                Block groundBlock = worldIn.getBlockState(blockpos1.down()).getBlock();
                if ((groundBlock != ModBlocks.MIDNIGHT_GRASS && groundBlock != ModBlocks.NIGHTSTONE && groundBlock != ModBlocks.MIDNIGHT_MYCELIUM) || worldIn.getBlockState(blockpos1).isNormalCube()) {
                    continue label35;
                }
            }
            if (worldIn.isAirBlock(blockpos1)) {
                if (rand.nextInt(8) == 0) {
                    if (blockpos1.getY() < MidnightChunkGenerator.MAX_CAVE_HEIGHT) {
                        CavernousBiomeStore.getBiome(worldIn, blockpos1.getX(), blockpos1.getZ()).plantFlower(worldIn, rand, blockpos1);
                    } else {
                        worldIn.getBiome(blockpos1).plantFlower(worldIn, rand, blockpos1);
                    }
                } else {
                    IBlockState tallGrassState = ModBlocks.TALL_MIDNIGHT_GRASS.getDefaultState();
                    if (GeneratablePlant.canGenerate(worldIn, blockpos1, tallGrassState)) {
                        worldIn.setBlockState(blockpos1, tallGrassState, 3);
                    }
                }
            }
        }
    }
}
