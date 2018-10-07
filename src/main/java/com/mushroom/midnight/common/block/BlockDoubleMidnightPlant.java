package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoubleMidnightPlant extends BlockBush implements IModelProvider {
    private static final PropertyEnum<BlockDoublePlant.EnumBlockHalf> HALF = PropertyEnum.create("half", BlockDoublePlant.EnumBlockHalf.class);

    public BlockDoubleMidnightPlant() {
        super(Material.VINE);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == ModBlocks.MIDNIGHT_DIRT || state.getBlock() == ModBlocks.MIDNIGHT_GRASS;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && world.isAirBlock(pos.up());
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
            return super.canBlockStay(world, pos, state);
        }
        BlockPos otherPos = this.getOtherPos(state, pos);
        IBlockState otherState = world.getBlockState(otherPos);
        if (otherState.getBlock() != this) {
            return false;
        }
        return this.isUpper(state) || super.canBlockStay(world, pos, otherState);
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
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return this.isUpper(state) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        BlockDoublePlant.EnumBlockHalf half = meta == 1 ? BlockDoublePlant.EnumBlockHalf.UPPER : BlockDoublePlant.EnumBlockHalf.LOWER;
        return this.getDefaultState().withProperty(HALF, half);
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }
}
