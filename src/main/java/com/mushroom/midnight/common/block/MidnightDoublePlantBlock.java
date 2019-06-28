package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.Random;

public class MidnightDoublePlantBlock extends MidnightPlantBlock {
    protected static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public MidnightDoublePlantBlock(PlantBehaviorType behaviorType, boolean glowing) {
        super(behaviorType, glowing);
        setDefaultState(this.getStateContainer().getBaseState().with(HALF, DoubleBlockHalf.LOWER));
    }

    public MidnightDoublePlantBlock(boolean glowing) {
        this(PlantBehaviorType.FLOWER, glowing);
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return canSustainBush(world.getBlockState(pos.down())) && world.isAirBlock(pos.up()) && world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }

    @Override
    protected void checkAndDropBlock(World world, BlockPos pos, BlockState state) {
        if (!this.canBlockStay(world, pos, state)) {
            BlockPos upperPos = this.getUpperPos(state, pos);
            BlockPos lowerPos = this.getLowerPos(state, pos);

            if (this.isLower(state)) {
                this.dropBlockAsItem(world, pos, state, 0);
            }

            this.breakHalf(world, upperPos, 2);
            this.breakHalf(world, lowerPos, 3);
        }
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, BlockState state) {
        if (state.getBlock() != this) {
            return canSustainBush(world.getBlockState(pos.down()));
        }
        BlockPos otherPos = this.getOtherPos(state, pos);
        BlockState otherState = world.getBlockState(otherPos);
        if (otherState.getBlock() != this) {
            return false;
        }
        return this.isUpper(state) || canSustainBush(world.getBlockState(pos.down()));
    }

    private BlockPos getUpperPos(BlockState state, BlockPos pos) {
        return this.isUpper(state) ? pos : pos.up();
    }

    private BlockPos getLowerPos(BlockState state, BlockPos pos) {
        return this.isLower(state) ? pos : pos.down();
    }

    private BlockPos getOtherPos(BlockState state, BlockPos pos) {
        return this.isUpper(state) ? pos.down() : pos.up();
    }

    private boolean isUpper(BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.UPPER;
    }

    private boolean isLower(BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER;
    }

    private void breakHalf(World world, BlockPos pos, int flags) {
        if (world.getBlockState(pos).getBlock() == this) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), flags);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), 2);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos otherPos = this.getOtherPos(state, pos);
        if (this.isUpper(state)) {
            if (player.abilities.isCreativeMode) {
                this.breakHalf(world, otherPos, 3);
            } else if (world.isRemote) {
                this.breakHalf(world, otherPos, 3);
            } else {
                world.destroyBlock(otherPos, true);
            }
        } else {
            this.breakHalf(world, otherPos, 2);
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return this.isUpper(state) ? 1 : 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateFromMeta(int meta) {
        DoubleBlockHalf half = meta == 1 ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER;
        return this.getDefaultState().with(HALF, half);
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return Items.AIR;
        }
        return super.getItemDropped(state, rand, fortune);
    }
}
