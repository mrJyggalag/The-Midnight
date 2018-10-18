package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.block.BlockMidnightFungiShelf;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

// Messy 'cleanup' of `WorldGenBigMushroom`
// TODO: Rewrite
public class LargeFungiFeature extends MidnightAbstractFeature {
    private static final Block[] SHELF_BLOCKS = new Block[] { ModBlocks.NIGHTSHROOM_SHELF, ModBlocks.DEWSHROOM_SHELF, ModBlocks.VIRIDSHROOM_SHELF };

    private final IBlockState stem;
    private final IBlockState cap;

    public LargeFungiFeature(IBlockState stem, IBlockState cap) {
        this.stem = stem;
        this.cap = cap;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos origin) {
        int height = rand.nextInt(6) + 4;
        FungiShape shape = rand.nextBoolean() ? FungiShape.DOME : FungiShape.FLAT;

        if (!this.canGenerate(world, origin, height)) {
            return false;
        }

        Block groundBlock = world.getBlockState(origin.down()).getBlock();
        if (groundBlock != ModBlocks.MIDNIGHT_DIRT && groundBlock != ModBlocks.MIDNIGHT_GRASS) {
            return false;
        }

        this.generateHat(world, rand, origin, height, shape);
        this.generateStem(world, origin, height);

        return true;
    }

    private void generateStem(World world, BlockPos pos, int height) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        mutablePos.setPos(pos);

        for (int currentY = 0; currentY < height; currentY++) {
            mutablePos.setY(pos.getY() + currentY);

            IBlockState state = world.getBlockState(mutablePos);
            if (state.getBlock().canBeReplacedByLeaves(state, world, mutablePos)) {
                this.setBlockAndNotifyAdequately(world, mutablePos, this.stem);
            }
        }
    }

    private void generateHat(World world, Random rand, BlockPos pos, int height, FungiShape shape) {
        int maxY = pos.getY() + height;

        int minHatY = shape == FungiShape.DOME ? maxY - 3 : maxY;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int currentY = minHatY; currentY <= maxY; currentY++) {
            int radius = 1;
            if (currentY < maxY) {
                radius++;
            }

            if (shape == FungiShape.FLAT) {
                radius = 3;
            }

            int minX = pos.getX() - radius;
            int maxX = pos.getX() + radius;
            int minZ = pos.getZ() - radius;
            int maxZ = pos.getZ() + radius;

            for (int currentZ = minZ; currentZ <= maxZ; currentZ++) {
                for (int currentX = minX; currentX <= maxX; currentX++) {
                    BlockHugeMushroom.EnumType edgeType = this.computeEdge(minX, minZ, maxX, maxZ, currentX, currentZ);

                    if (shape == FungiShape.FLAT || currentY < maxY) {
                        if ((currentX == minX || currentX == maxX) && (currentZ == minZ || currentZ == maxZ)) {
                            continue;
                        }

                        edgeType = this.computeRoundEdge(pos, radius, minX, minZ, maxX, maxZ, currentX, currentZ, edgeType);
                    }

                    if (edgeType == BlockHugeMushroom.EnumType.CENTER && currentY < maxY) {
                        edgeType = BlockHugeMushroom.EnumType.ALL_INSIDE;
                    }

                    if (pos.getY() >= maxY - 1 || edgeType != BlockHugeMushroom.EnumType.ALL_INSIDE) {
                        mutablePos.setPos(currentX, currentY, currentZ);
                        this.generateShelf(world, rand, mutablePos, edgeType);

                        mutablePos.setPos(currentX, currentY, currentZ);
                        IBlockState currentState = world.getBlockState(mutablePos);
                        if (currentState.getBlock().canBeReplacedByLeaves(currentState, world, mutablePos)) {
                            this.setBlockAndNotifyAdequately(world, mutablePos, this.cap.withProperty(BlockHugeMushroom.VARIANT, edgeType));
                        }
                    }
                }
            }
        }
    }

    private BlockHugeMushroom.EnumType computeRoundEdge(BlockPos pos, int radius, int minX, int minZ, int maxX, int maxZ, int currentX, int currentZ, BlockHugeMushroom.EnumType edgeType) {
        if (currentX == pos.getX() - (radius - 1) && currentZ == minZ) {
            return BlockHugeMushroom.EnumType.NORTH_WEST;
        } else if (currentX == minX && currentZ == pos.getZ() - (radius - 1)) {
            return BlockHugeMushroom.EnumType.NORTH_WEST;
        } else if (currentX == pos.getX() + (radius - 1) && currentZ == minZ) {
            return BlockHugeMushroom.EnumType.NORTH_EAST;
        } else if (currentX == maxX && currentZ == pos.getZ() - (radius - 1)) {
            return BlockHugeMushroom.EnumType.NORTH_EAST;
        } else if (currentX == pos.getX() - (radius - 1) && currentZ == maxZ) {
            return BlockHugeMushroom.EnumType.SOUTH_WEST;
        } else if (currentX == minX && currentZ == pos.getZ() + (radius - 1)) {
            return BlockHugeMushroom.EnumType.SOUTH_WEST;
        } else if (currentX == pos.getX() + (radius - 1) && currentZ == maxZ) {
            return BlockHugeMushroom.EnumType.SOUTH_EAST;
        } else if (currentX == maxX && currentZ == pos.getZ() + (radius - 1)) {
            return BlockHugeMushroom.EnumType.SOUTH_EAST;
        }

        return edgeType;
    }

    private void generateShelf(World world, Random rand, BlockPos.MutableBlockPos mutablePos, BlockHugeMushroom.EnumType edgeType) {
        EnumFacing[] horizontalOffsets = this.getHorizontalOffsets(edgeType);
        if (horizontalOffsets.length > 0) {
            if (rand.nextInt(4) == 0) {
                EnumFacing horizontalOffset = horizontalOffsets[rand.nextInt(horizontalOffsets.length)];
                mutablePos.move(horizontalOffset);
                this.tryPlaceState(world, mutablePos, this.getShelfState(rand, horizontalOffset));
            }
        } else if (rand.nextInt(8) == 0) {
            mutablePos.move(EnumFacing.UP);
            this.tryPlaceState(world, mutablePos, this.getShelfState(rand, EnumFacing.UP));
        }
    }

    private IBlockState getShelfState(Random rand, EnumFacing facing) {
        return SHELF_BLOCKS[rand.nextInt(SHELF_BLOCKS.length)].getDefaultState().withProperty(BlockMidnightFungiShelf.FACING, facing);
    }

    private EnumFacing[] getHorizontalOffsets(BlockHugeMushroom.EnumType edgeType) {
        switch (edgeType) {
            case NORTH:
                return new EnumFacing[] { EnumFacing.NORTH };
            case NORTH_WEST:
                return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.WEST };
            case NORTH_EAST:
                return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST };
            case EAST:
                return new EnumFacing[] { EnumFacing.EAST };
            case SOUTH_EAST:
                return new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.EAST };
            case SOUTH:
                return new EnumFacing[] { EnumFacing.SOUTH };
            case SOUTH_WEST:
                return new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.WEST };
            case WEST:
                return new EnumFacing[] { EnumFacing.WEST };
            case ALL_OUTSIDE:
                return EnumFacing.HORIZONTALS;
        }
        return new EnumFacing[0];
    }

    private BlockHugeMushroom.EnumType computeEdge(int minX, int minZ, int maxX, int maxZ, int x, int z) {
        boolean west = x == minX;
        boolean north = z == minZ;
        boolean east = x == maxX;
        boolean south = z == maxZ;

        int meta = 5;
        if (west) {
            meta--;
        } else if (east) {
            meta++;
        }

        if (north) {
            meta -= 3;
        } else if (south) {
            meta += 3;
        }

        return BlockHugeMushroom.EnumType.byMetadata(meta);
    }

    private boolean canGenerate(World world, BlockPos pos, int height) {
        if (pos.getY() < 1 || pos.getY() + height + 1 >= 256) {
            return false;
        }

        for (int currentY = pos.getY(); currentY <= pos.getY() + 1 + height; currentY++) {
            int radius = 3;
            if (currentY <= pos.getY() + 3) {
                radius = 0;
            }

            BlockPos minPos = pos.add(-radius, currentY, -radius);
            BlockPos maxPos = pos.add(radius, currentY, radius);
            for (BlockPos localPos : BlockPos.getAllInBoxMutable(minPos, maxPos)) {
                IBlockState state = world.getBlockState(localPos);
                if (!state.getBlock().isAir(state, world, localPos) && !state.getBlock().isLeaves(state, world, localPos)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void tryPlaceState(World world, BlockPos pos, IBlockState state) {
        if (world.isAirBlock(pos)) {
            this.setBlockAndNotifyAdequately(world, pos, state);
        }
    }

    private enum FungiShape {
        DOME,
        FLAT
    }
}
