package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.capability.CavernousBiomeStore;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModTabs;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class BlockNightstone extends Block implements IGrowable, IModelProvider {
    public BlockNightstone() {
        super(Material.ROCK, MapColor.BLUE_STAINED_HARDENED_CLAY);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(ModTabs.BUILDING_TAB);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return true;
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
