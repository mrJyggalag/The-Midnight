package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SoilBlock extends Block implements IGrowable {
    private final boolean applyBonemeal;

    public SoilBlock(Block.Properties properties, boolean applyBonemeal) {
        super(properties);
        this.applyBonemeal = applyBonemeal;
    }

    @Override
    public boolean isSolid(BlockState state) {
        return true;
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
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return true;
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.up()).isAir();
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return this.applyBonemeal;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        if (!canUseBonemeal(world, rand, pos, state)) {
            return;
        }
        BlockPos blockpos = pos.up();
        BlockState grassState = MidnightBlocks.GRASS.getDefaultState();
        for (int i = 0; i < 128; ++i) {
            BlockPos blockpos1 = blockpos;
            int j = 0;
            while (true) {
                if (j >= i / 16) {
                    BlockState upperState = world.getBlockState(blockpos1);
                    // grow grass to tallgrass
                    if (upperState.getBlock() == grassState.getBlock() && rand.nextInt(10) == 0) {
                        ((IGrowable) grassState.getBlock()).grow(world, rand, blockpos1, upperState);
                    }
                    if (!upperState.isAir()) { // !canGrow()
                        break;
                    }
                    BlockState plantState;
                    // get a flower or the default grass
                    if (rand.nextInt(8) == 0) {
                        List<ConfiguredFeature<?>> list = world.getBiome(blockpos1).getFlowers();
                        if (list.isEmpty()) {
                            break;
                        }
                        // TODO integrate cavern biomes
                        plantState = ((FlowersFeature) ((DecoratedFeatureConfig) (list.get(0)).config).feature.feature).getRandomFlower(rand, blockpos1);
                    } else {
                        plantState = grassState;
                    }
                    if (plantState.isValidPosition(world, blockpos1)) {
                        world.setBlockState(blockpos1, grassState, 3);
                    }
                    break;
                }
                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (!world.getBlockState(blockpos1.down()).getBlock().isIn(MidnightTags.Blocks.BONEMEAL_GROUNDS) || isOpaque(world.getBlockState(blockpos1).getCollisionShape(world, blockpos1))) {
                    break;
                }
                ++j;
            }
        }
    }
}
