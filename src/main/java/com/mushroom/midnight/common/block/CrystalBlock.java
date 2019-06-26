package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class CrystalBlock extends Block {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.8, 0.9);

    public CrystalBlock(MapColor mapColor) {
        super(Material.ROCK, mapColor);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.GLASS);
        this.setHarvestLevel("pickaxe", 1);
        this.setTickRandomly(true);
        this.setCreativeTab(MidnightItemGroups.DECORATION);
        this.setLightLevel(0.2F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDS;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

    @Override
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
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
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        BlockPos lowerPos = pos.down();
        BlockState lowerState = world.getBlockState(lowerPos);
        return lowerState.isSideSolid(world, pos, Direction.UP)
                || lowerState.getBlockFaceShape(world, lowerPos, Direction.UP) == BlockFaceShape.SOLID;
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        super.neighborChanged(state, world, pos, block, fromPos);
        this.tryBreakBlock(world, pos, state);
    }

    @Override
    public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
        this.tryBreakBlock(world, pos, state);
    }

    private void tryBreakBlock(World world, BlockPos pos, BlockState state) {
        if (!this.canPlaceBlockAt(world, pos)) {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getPackedLightmapCoords(BlockState state, IBlockAccess source, BlockPos pos) {
        int skyLight = 15;
        int blockLight = 15;
        return skyLight << 20 | blockLight << 4;
    }
}
