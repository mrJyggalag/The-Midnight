package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import com.mushroom.midnight.common.util.DirectionalBounds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;

public class BlockMidnightFungiShelf extends Block implements IModelProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", f -> f != EnumFacing.DOWN);

    private static final DirectionalBounds BOUNDS = new DirectionalBounds(0.0625, 0.3, 1.0, 0.9375, 0.7, 0.6);
    private static final AxisAlignedBB VERTICAL_BOUNDS = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.4, 0.9375);

    public BlockMidnightFungiShelf() {
        super(Material.PLANTS);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(ModTabs.DECORATION_TAB);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
        return canAttachTo(world, pos, side);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return Arrays.stream(EnumFacing.VALUES).anyMatch(f -> canAttachTo(world, pos, f));
    }

    private static boolean canAttachTo(World world, BlockPos pos, EnumFacing facing) {
        if (facing == EnumFacing.DOWN) {
            return false;
        }
        BlockPos surfacePos = pos.offset(facing.getOpposite());
        IBlockState surfaceState = world.getBlockState(surfacePos);
        return surfaceState.isSideSolid(world, surfacePos, facing);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, canAttachTo(world, pos, facing) ? facing : EnumFacing.UP);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (this.tryDrop(world, pos, state) && !canAttachTo(world, pos, state.getValue(FACING))) {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    private boolean tryDrop(World world, BlockPos pos, IBlockState state) {
        if (this.canPlaceBlockAt(world, pos)) {
            return true;
        }
        this.dropBlockAsItem(world, pos, state, 0);
        world.setBlockToAir(pos);
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        if (facing == EnumFacing.UP) {
            return VERTICAL_BOUNDS;
        }
        return BOUNDS.get(facing);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.byIndex(meta);
        if (facing == EnumFacing.DOWN) {
            return this.getDefaultState();
        }
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror) {
        return state.withRotation(mirror.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }
}
