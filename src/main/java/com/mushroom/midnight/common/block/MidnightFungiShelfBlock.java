package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import com.mushroom.midnight.common.util.DirectionalBounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;

public class MidnightFungiShelfBlock extends Block {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", f -> f != Direction.DOWN);

    private static final DirectionalBounds BOUNDS = new DirectionalBounds(0.0625, 0.3, 1.0, 0.9375, 0.7, 0.6);
    private static final AxisAlignedBB VERTICAL_BOUNDS = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.4, 0.9375);

    public MidnightFungiShelfBlock() {
        super(Material.PLANTS, MapColor.PURPLE_STAINED_HARDENED_CLAY);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(MidnightItemGroups.DECORATION);
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, Direction side) {
        return canAttachTo(world, pos, side);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return Arrays.stream(Direction.VALUES).anyMatch(f -> canAttachTo(world, pos, f));
    }

    private static boolean canAttachTo(World world, BlockPos pos, Direction facing) {
        if (facing == Direction.DOWN) {
            return false;
        }
        BlockPos surfacePos = pos.offset(facing.getOpposite());
        BlockState surfaceState = world.getBlockState(surfacePos);
        return surfaceState.isSideSolid(world, surfacePos, facing);
    }

    @Override
    public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer) {
        return this.getDefaultState().with(FACING, canAttachTo(world, pos, facing) ? facing : Direction.UP);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (this.tryDrop(world, pos, state) && !canAttachTo(world, pos, state.get(FACING))) {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    private boolean tryDrop(World world, BlockPos pos, BlockState state) {
        if (this.canPlaceBlockAt(world, pos)) {
            return true;
        }
        this.dropBlockAsItem(world, pos, state, 0);
        world.setBlockToAir(pos);
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
        Direction facing = state.get(FACING);
        if (facing == Direction.UP) {
            return VERTICAL_BOUNDS;
        }
        return BOUNDS.get(facing);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        Direction facing = Direction.byIndex(meta);
        if (facing == Direction.DOWN) {
            return this.getDefaultState();
        }
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.get(FACING).getIndex();
    }

    @Override
    public BlockState withRotation(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState withMirror(BlockState state, Mirror mirror) {
        return state.withRotation(mirror.toRotation(state.get(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canBeReplacedByLeaves(BlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }
}
