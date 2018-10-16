package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.IntFunction;

public class WorldGenShadowrootTree extends WorldGenMidnightTree {
    private static final int BRANCH_SPACING = 3;

    public WorldGenShadowrootTree(IBlockState log, IBlockState leaves) {
        super(log, leaves);
    }

    public WorldGenShadowrootTree() {
        this(ModBlocks.SHADOWROOT_LOG.getDefaultState(), ModBlocks.SHADOWROOT_LEAVES.getDefaultState());
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        int height = rand.nextInt(8) + 10;

        IntFunction<Integer> widthSupplier = y -> 1;

        if (!this.canFit(world, pos, height, widthSupplier)) {
            return false;
        }

        if (this.canGrow(world, pos)) {
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);
            for (int localY = 0; localY < height; localY++) {
                mutablePos.setY(pos.getY() + localY);
                this.placeLog(world, mutablePos);
            }

            this.generateRoots(world, rand, pos);

            Set<Branch> branches = this.collectBranches(world, rand, pos, height);
            for (Branch branch : branches) {
                this.placeLog(world, branch.pos, branch.getAxis());
            }

            Set<BlockPos> leafPositions = this.produceBlob(pos.up(height - 2), 1.5, 2.5);
            leafPositions = this.droopLeaves(leafPositions, rand, 4);

            for (Branch branch : branches) {
                leafPositions.addAll(this.collectBranchLeaves(branch, rand));
            }

            for (BlockPos leafPos : leafPositions) {
                this.placeLeaves(world, leafPos);
            }

            return true;
        }

        return false;
    }

    private void generateRoots(World world, Random rand, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        List<EnumFacing> availableSides = new ArrayList<>(4);
        Collections.addAll(availableSides, EnumFacing.HORIZONTALS);

        int count = rand.nextInt(3) + 1;
        for (int i = 0; i < count; i++) {
            EnumFacing side = availableSides.remove(rand.nextInt(availableSides.size()));
            BlockPos rootOrigin = pos.offset(side);

            int height = rand.nextInt(3) + 1;
            for (int localY = 0; localY < height; localY++) {
                mutablePos.setPos(rootOrigin.getX(), rootOrigin.getY() + localY, rootOrigin.getZ());
                this.placeLog(world, mutablePos);
            }
        }
    }

    private Set<BlockPos> droopLeaves(Set<BlockPos> leafPositions, Random rand, int amount) {
        Set<BlockPos> droopedLeaves = new HashSet<>(leafPositions);
        for (BlockPos pos : leafPositions) {
            int droopAmount = rand.nextInt(amount);
            for (int i = 0; i < droopAmount; i++) {
                droopedLeaves.add(pos.down(i));
            }
        }

        return droopedLeaves;
    }

    private Set<Branch> collectBranches(World world, Random rand, BlockPos pos, int height) {
        int minBranchHeight = 3;
        int maxBranchHeight = height - 4;
        int heightRange = maxBranchHeight - minBranchHeight;

        Set<Branch> branches = new HashSet<>();

        int branchCount = heightRange / BRANCH_SPACING;
        double normalizedSpacing = (double) heightRange / branchCount;

        EnumFacing lastDirection = null;

        for (int branch = 0; branch < branchCount; branch++) {
            int y = MathHelper.ceil(minBranchHeight + 1 + branch * normalizedSpacing);

            EnumFacing direction = null;
            while (direction == null || direction == lastDirection) {
                direction = EnumFacing.HORIZONTALS[rand.nextInt(EnumFacing.HORIZONTALS.length)];
            }
            lastDirection = direction;

            BlockPos branchPos = pos.up(y).offset(direction);
            if (!this.isReplaceable(world, branchPos)) {
                continue;
            }

            float outerAngle = direction.getHorizontalAngle();
            float angle = (rand.nextFloat() * 90.0F) + outerAngle - 45.0F;

            branches.add(new Branch(branchPos, direction, angle));
        }

        return branches;
    }

    private Set<BlockPos> collectBranchLeaves(Branch branch, Random rand) {
        Set<BlockPos> branchLeaves = new HashSet<>();

        float theta = (float) Math.toRadians(branch.angle);
        float deltaX = -MathHelper.sin(theta);
        float deltaZ = MathHelper.cos(theta);

        for (int step = 0; step < 3; step++) {
            int droop = -step / 2 + 1;
            BlockPos origin = branch.pos.add(deltaX * step, droop, deltaZ * step);

            Set<BlockPos> stepLeaves = this.produceBlob(origin, 1.0);
            branchLeaves.addAll(this.droopLeaves(stepLeaves, rand, step + 1));
        }

        return branchLeaves;
    }

    private static class Branch {
        final BlockPos pos;
        final EnumFacing direction;
        final float angle;

        private Branch(BlockPos pos, EnumFacing direction, float angle) {
            this.pos = pos;
            this.direction = direction;
            this.angle = angle;
        }

        public BlockLog.EnumAxis getAxis() {
            switch (this.direction.getAxis()) {
                case X:
                    return BlockLog.EnumAxis.X;
                case Z:
                    return BlockLog.EnumAxis.Z;
                case Y:
                default:
                    return BlockLog.EnumAxis.Y;
            }
        }

        @Override
        public int hashCode() {
            return this.pos.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Branch && ((Branch) obj).pos.equals(this.pos);
        }
    }
}
