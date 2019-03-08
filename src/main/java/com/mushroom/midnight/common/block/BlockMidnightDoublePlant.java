package com.mushroom.midnight.common.block;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.Random;

public class BlockMidnightDoublePlant extends BlockMidnightPlant {
    protected static final PropertyEnum<BlockDoublePlant.EnumBlockHalf> HALF = BlockDoublePlant.HALF;

    public BlockMidnightDoublePlant(PlantBehaviorType behaviorType, boolean glowing) {
        super(behaviorType, glowing);
        setDefaultState(this.blockState.getBaseState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER));
    }

    public BlockMidnightDoublePlant(boolean glowing) {
        this(PlantBehaviorType.FLOWER, glowing);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return canSustainBush(world.getBlockState(pos.down())) && world.isAirBlock(pos.up()) && world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }

    @Override
    protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
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
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        if (state.getBlock() != this) {
            return canSustainBush(world.getBlockState(pos.down()));
        }
        BlockPos otherPos = this.getOtherPos(state, pos);
        IBlockState otherState = world.getBlockState(otherPos);
        if (otherState.getBlock() != this) {
            return false;
        }
        return this.isUpper(state) || canSustainBush(world.getBlockState(pos.down()));
    }

    private BlockPos getUpperPos(IBlockState state, BlockPos pos) {
        return this.isUpper(state) ? pos : pos.up();
    }

    private BlockPos getLowerPos(IBlockState state, BlockPos pos) {
        return this.isLower(state) ? pos : pos.down();
    }

    private BlockPos getOtherPos(IBlockState state, BlockPos pos) {
        return this.isUpper(state) ? pos.down() : pos.up();
    }

    private boolean isUpper(IBlockState state) {
        return state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER;
    }

    private boolean isLower(IBlockState state) {
        return state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.LOWER;
    }

    private void breakHalf(World world, BlockPos pos, int flags) {
        if (world.getBlockState(pos).getBlock() == this) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), flags);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.UPPER), 2);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        BlockPos otherPos = this.getOtherPos(state, pos);
        if (this.isUpper(state)) {
            if (player.capabilities.isCreativeMode) {
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
    public int getMetaFromState(IBlockState state) {
        return this.isUpper(state) ? 1 : 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        BlockDoublePlant.EnumBlockHalf half = meta == 1 ? BlockDoublePlant.EnumBlockHalf.UPPER : BlockDoublePlant.EnumBlockHalf.LOWER;
        return this.getDefaultState().withProperty(HALF, half);
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER) {
            return Items.AIR;
        }
        return super.getItemDropped(state, rand, fortune);
    }
}
