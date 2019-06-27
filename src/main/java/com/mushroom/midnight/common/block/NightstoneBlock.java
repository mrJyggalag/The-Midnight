package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.capability.CavernousBiomeStore;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class NightstoneBlock extends Block implements IGrowable {
    public NightstoneBlock() {
        super(Material.ROCK, MapColor.BLUE_STAINED_HARDENED_CLAY);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(MidnightItemGroups.BUILDING);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction direction, IPlantable plantable) {
        return true;
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.up();
        boolean isMidnight = Helper.isMidnightDimension(worldIn);
        label35:
        for (int i = 0; i < 128; ++i) {
            BlockPos blockpos1 = blockpos;
            for (int j = 0; j < i / 16; ++j) {
                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (!Helper.isGroundForBoneMeal(worldIn.getBlockState(blockpos1.down()).getBlock()) || worldIn.getBlockState(blockpos1).isNormalCube()) {
                    continue label35;
                }
            }
            if (worldIn.isAirBlock(blockpos1)) {
                if (rand.nextInt(8) == 0) {
                    if (isMidnight && blockpos1.getY() < MidnightChunkGenerator.MAX_CAVE_HEIGHT) {
                        CavernousBiomeStore.getBiome(worldIn, blockpos1.getX(), blockpos1.getZ()).plantFlower(worldIn, rand, blockpos1);
                    } else {
                        worldIn.getBiome(blockpos1).plantFlower(worldIn, rand, blockpos1);
                    }
                } else {
                    BlockState tallGrassState = MidnightBlocks.TALL_MIDNIGHT_GRASS.getDefaultState();
                    if (GeneratablePlant.canGenerate(worldIn, blockpos1, tallGrassState)) {
                        worldIn.setBlockState(blockpos1, tallGrassState, 3);
                    }
                }
            }
        }
    }
}
