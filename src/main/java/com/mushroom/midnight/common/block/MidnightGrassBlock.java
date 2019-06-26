package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.capability.CavernousBiomeStore;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class MidnightGrassBlock extends Block implements IGrowable {
    public MidnightGrassBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 0;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.SHOVEL;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isRemote || !world.isAreaLoaded(pos, 3)) {
            return;
        }

        BlockPos abovePos = pos.up();
        if (!Helper.isMidnightDimension(world) || world.getBlockState(abovePos).getLightOpacity(world, abovePos) > 2) {
            world.setBlockState(pos, MidnightBlocks.MIDNIGHT_DIRT.getDefaultState());
            return;
        }

        for (int i = 0; i < 4; ++i) {
            BlockPos spreadPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
            if (spreadPos.getY() >= 0 && spreadPos.getY() < 256 && !world.isBlockLoaded(spreadPos)) {
                return;
            }

            BlockState surfaceState = world.getBlockState(spreadPos);
            if (surfaceState.getBlock() == MidnightBlocks.MIDNIGHT_DIRT) {
                BlockState coverState = world.getBlockState(spreadPos.up());
                if (coverState.getLightOpacity(world, spreadPos.up()) <= 2) {
                    world.setBlockState(spreadPos, this.getDefaultState());
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockAccess world, BlockPos pos, Direction direction, IPlantable plantable) {
        return true;
    }

    @Override
    public void onPlantGrow(BlockState state, World world, BlockPos pos, BlockPos source) {
        world.setBlockState(pos, MidnightBlocks.MIDNIGHT_DIRT.getDefaultState(), 2);
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(MidnightBlocks.MIDNIGHT_DIRT);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, BlockState state, boolean isClient) {
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
