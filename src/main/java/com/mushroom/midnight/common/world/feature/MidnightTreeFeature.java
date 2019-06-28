package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.AbstractWrappedWorld;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.common.IPlantable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public abstract class MidnightTreeFeature<T extends IFeatureConfig> extends AbstractTreeFeature<T> {
    private final ThreadLocal<T> config = new ThreadLocal<>();

    protected MidnightTreeFeature(Function<Dynamic<?>, ? extends T> deserialize) {
        super(deserialize, true);
        this.sapling = (IPlantable) MidnightBlocks.SHADOWROOT_SAPLING; // TODO
    }

    protected abstract boolean place(IWorld world, Random random, BlockPos origin, T config);

    @Override
    public final boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos origin, T config) {
        // AbstractTreeFeature doesn't pass us the config...
        this.config.set(config);
        return super.place(world, generator, random, origin, config);
    }

    @Override
    protected final boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader world, Random random, BlockPos origin, MutableBoundingBox bounds) {
        WorldWrapper wrapper = new WorldWrapper((IWorld) world, changedBlocks, bounds);
        return this.place(wrapper, random, origin, this.config.get());
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

    private static class WorldWrapper extends AbstractWrappedWorld {
        private final Set<BlockPos> logs;
        private final MutableBoundingBox bounds;

        private WorldWrapper(IWorld world, Set<BlockPos> logs, MutableBoundingBox bounds) {
            super(world);
            this.logs = logs;
            this.bounds = bounds;
        }

        @Override
        public boolean setBlockState(BlockPos pos, BlockState state, int flags) {
            boolean set = super.setBlockState(pos, state, flags);
            if (set && state.isIn(BlockTags.LOGS)) {
                this.logs.add(pos);
                this.bounds.expandTo(new MutableBoundingBox(pos, pos));
            }
            return set;
        }

        @Override
        public boolean removeBlock(BlockPos pos, boolean b) {
            if (super.removeBlock(pos, b)) {
                this.logs.remove(pos);
                return true;
            }
            return false;
        }

        @Override
        public boolean destroyBlock(BlockPos pos, boolean b) {
            if (super.destroyBlock(pos, b)) {
                this.logs.remove(pos);
                return true;
            }
            return false;
        }
    }
}
