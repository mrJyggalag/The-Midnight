package com.mushroom.midnight.common.block;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockMidnightLeaves extends BlockLeaves implements IModelProvider {
    public BlockMidnightLeaves() {
        super();
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
        this.setDefaultState(this.blockState.getBaseState().withProperty(DECAYABLE, true).withProperty(CHECK_DECAY, true));
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return Lists.newArrayList(new ItemStack(this));
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.OAK;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return Blocks.LEAVES.getRenderLayer();
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess access, BlockPos pos, EnumFacing side) {
        BlockPos neighborPos = pos.offset(side);
        if (this.isOpaqueCube(state) && access.getBlockState(neighborPos).getBlock() == this) {
            return false;
        }
        return !access.getBlockState(neighborPos).doesSideBlockRendering(access, neighborPos, side.getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
            .withProperty(DECAYABLE, (meta & 1) != 0)
            .withProperty(CHECK_DECAY, ((meta >> 1) & 1) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(DECAYABLE) ? 1 : 0) | (state.getValue(CHECK_DECAY) ? 1 : 0) << 1;
    }
}
