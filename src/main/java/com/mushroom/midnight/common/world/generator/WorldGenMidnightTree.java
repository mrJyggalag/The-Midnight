package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.common.block.BlockMidnightLeaves;
import com.mushroom.midnight.common.block.BlockMidnightLog;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.IntFunction;

public abstract class WorldGenMidnightTree extends WorldGenAbstractTree {
    protected final IBlockState log;
    protected final IBlockState leaves;

    public WorldGenMidnightTree(IBlockState log, IBlockState leaves) {
        super(true);
        this.log = log;
        this.leaves = leaves;
    }

    protected boolean canFit(World world, BlockPos pos, int height, IntFunction<Integer> widthSupplier) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);

        for (int localY = 0; localY < height; localY++) {
            int width = widthSupplier.apply(localY);
            for (int localZ = -width; localZ <= width; localZ++) {
                for (int localX = -width; localX <= width; localX++) {
                    mutablePos.setPos(pos.getX() + localX, pos.getY() + localY, pos.getZ() + localZ);
                    if (!this.isReplaceable(world, mutablePos)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    protected boolean canGrow(World world, BlockPos pos) {
        BlockPos groundPos = pos.down();
        IBlockState groundState = world.getBlockState(groundPos);
        Block groundBlock = groundState.getBlock();
        if (groundBlock.canSustainPlant(groundState, world, groundPos, EnumFacing.UP, (IPlantable) ModBlocks.SHADOWROOT_SAPLING)) {
            groundBlock.onPlantGrow(groundState, world, groundPos, pos);
            return true;
        } else {
            return false;
        }
    }

    protected void placeLog(World world, BlockPos pos) {
        this.placeLog(world, pos, BlockLog.EnumAxis.Y);
    }

    protected void placeLog(World world, BlockPos pos, BlockLog.EnumAxis axis) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getMaterial() == Material.VINE) {
            this.setBlockAndNotifyAdequately(world, pos, this.log.withProperty(BlockMidnightLog.LOG_AXIS, axis));
        }
    }

    protected void placeLeaves(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getMaterial() == Material.VINE) {
            this.setBlockAndNotifyAdequately(world, pos, this.leaves);
        }
    }

    protected Set<BlockPos> produceBlob(BlockPos origin, double radius) {
        return this.produceBlob(origin, radius, radius);
    }

    protected Set<BlockPos> produceBlob(BlockPos origin, double horizontalRadius, double verticalRadius) {
        Set<BlockPos> positions = new HashSet<>();

        int verticalRadiusCeil = MathHelper.ceil(verticalRadius);
        int horizontalRadiusCeil = MathHelper.ceil(horizontalRadius);

        BlockPos minPos = origin.add(-horizontalRadiusCeil, -verticalRadiusCeil, -horizontalRadiusCeil);
        BlockPos maxPos = origin.add(horizontalRadiusCeil, verticalRadiusCeil, horizontalRadiusCeil);
        for (BlockPos pos : BlockPos.getAllInBoxMutable(minPos, maxPos)) {
            double deltaX = (pos.getX() - origin.getX()) / horizontalRadius;
            double deltaY = (pos.getY() - origin.getY()) / verticalRadius;
            double deltaZ = (pos.getZ() - origin.getZ()) / horizontalRadius;
            double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
            if (distanceSquared <= 1.0) {
                positions.add(pos.toImmutable());
            }
        }

        return positions;
    }

    @Override
    protected boolean canGrowInto(Block blockType) {
        if (super.canGrowInto(blockType)) {
            return true;
        }
        Material material = blockType.getDefaultState().getMaterial();
        return material == Material.AIR || material == Material.LEAVES || material == Material.VINE
                || blockType instanceof BlockMidnightLog || blockType instanceof BlockMidnightLeaves
                || blockType == ModBlocks.MIDNIGHT_DIRT || blockType == ModBlocks.MIDNIGHT_GRASS;
    }
}
