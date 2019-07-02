package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.util.DirectionalBounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DeceitfulMossBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final DirectionalBounds BOUNDS = new DirectionalBounds(0.0, 0.0, 0.875, 1.0, 1.0, 1.0);

    public DeceitfulMossBlock() {
        super(Properties.create(Material.PLANTS, MaterialColor.PURPLE_TERRACOTTA).hardnessAndResistance(0.2f, 0f).sound(SoundType.PLANT));
        setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return BOUNDS.get(state.get(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }

    // TODO after able to load
    /*@Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, Direction side) {
        return canAttachTo(world, pos, side);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return Arrays.stream(Direction.values()).anyMatch(f -> canAttachTo(world, pos, f));
    }

    private static boolean canAttachTo(IWorld world, BlockPos pos, Direction facing) {
        if (facing == Direction.DOWN) {
            return false;
        }
        BlockPos surfacePos = pos.offset(facing.getOpposite());
        BlockState surfaceState = world.getBlockState(surfacePos);
        return surfaceState.isSideSolid(world, surfacePos, facing);
    }

    @Override
    public BlockState getStateForPlacement(BlockState state, Direction facing, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2, Hand hand) {
        return this.getDefaultState().with(FACING, canAttachTo(world, pos1, facing) ? facing : Direction.DOWN);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
        if (this.tryDrop(world, pos, state) && !canAttachTo(world, pos, state.get(FACING))) {
            Helper.spawnItemStack(world, pos, state.getBlock());
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }

    private boolean tryDrop(World world, BlockPos pos, BlockState state) {
        if (this.canPlaceBlockAt(world, pos)) {
            return true;
        }
        Helper.spawnItemStack(world, pos, state.getBlock());
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        return false;
    }*/

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.fillStateContainer(builder);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canBeReplacedByLeaves(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    // TODO check this it's static
    public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction side) {
        if (side == Direction.UP) {
            return true;
        }
        BlockState neighbor = world.getBlockState(pos.offset(side));
        return neighbor.getBlock() != this && super.shouldSideBeRendered(state, world, pos, side);
    }
}
