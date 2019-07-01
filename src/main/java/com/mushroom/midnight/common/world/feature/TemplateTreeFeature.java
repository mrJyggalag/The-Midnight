package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.MidnightFungiHatBlock;
import com.mushroom.midnight.common.registry.MidnightTags;
import com.mushroom.midnight.common.util.WorldUtil;
import com.mushroom.midnight.common.world.template.CompiledTemplate;
import com.mushroom.midnight.common.world.template.RotatedSettingConfigurator;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Collection;
import java.util.Random;
import java.util.function.Function;

public abstract class TemplateTreeFeature extends MidnightTreeFeature {
    private static final String ANCHOR_KEY = "origin";
    private static final String TRUNK_TOP_KEY = "trunk_top";
    private static final String TRUNK_CORNER_KEY = "trunk_corner";

    protected final ResourceLocation[] templates;
    protected final BlockState log;
    protected final BlockState leaf;

    private TemplateCompiler templateCompiler;

    protected TemplateTreeFeature(
            Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize,
            ResourceLocation[] templates,
            BlockState log, BlockState leaf
    ) {
        super(deserialize);
        this.templates = templates;
        this.log = log;
        this.leaf = leaf;
    }

    protected TemplateCompiler buildCompiler() {
        return TemplateCompiler.of(this.templates)
                .withAnchor(ANCHOR_KEY)
                .withSettingConfigurator(RotatedSettingConfigurator.INSTANCE)
                .withProcessor(this::processState)
                .withMarkerProcessor(this::processMarker);
    }

    @Override
    protected boolean place(IWorld world, Random random, BlockPos origin) {
        if (!isSoil(world, origin, this.getSapling())) {
            return false;
        }

        if (this.templateCompiler == null) {
            this.templateCompiler = this.buildCompiler();
        }

        CompiledTemplate template = this.templateCompiler.compile(world, random, origin);

        BlockPos trunkTop = template.lookupAny(TRUNK_TOP_KEY);
        Collection<BlockPos> trunkCorners = template.lookup(TRUNK_CORNER_KEY);
        if (trunkTop == null || trunkCorners.isEmpty()) {
            Midnight.LOGGER.warn("Template '{}' did not have required '{}' and '{}' data blocks", template, TRUNK_TOP_KEY, TRUNK_CORNER_KEY);
            return false;
        }

        BlockPos minCorner = WorldUtil.min(trunkCorners).add(1, 0, 1);
        BlockPos maxCorner = WorldUtil.max(trunkCorners).add(-1, 0, -1);

        if (!this.canGrow(world, minCorner, maxCorner) || !this.canFit(world, trunkTop, minCorner, maxCorner)) {
            return false;
        }

        this.setDirtAt(world, origin.down(), origin);

        template.addTo(world, random, 2 | 16);

        return true;
    }

    protected boolean canGrow(IWorld world, BlockPos minCorner, BlockPos maxCorner) {
        for (BlockPos pos : BlockPos.getAllInBoxMutable(minCorner, maxCorner)) {
            if (!isSoil(world, pos, this.getSapling())) {
                return false;
            }
        }
        return true;
    }

    protected boolean canFit(IWorld world, BlockPos trunkTop, BlockPos minCorner, BlockPos maxCorner) {
        BlockPos maxFit = new BlockPos(maxCorner.getX(), trunkTop.getY(), maxCorner.getZ());

        for (BlockPos pos : BlockPos.getAllInBoxMutable(minCorner, maxFit)) {
            if (!isAirOrLeaves(world, pos)) {
                return false;
            }
        }

        return true;
    }

    private Template.BlockInfo processState(IWorldReader world, BlockPos pos, Template.BlockInfo srcInfo, Template.BlockInfo info, PlacementSettings settings) {
        BlockState state = info.state;
        Block block = state.getBlock();
        if (block instanceof LogBlock) {
            Direction.Axis axis = state.get(LogBlock.AXIS);
            return new Template.BlockInfo(pos, this.log.with(LogBlock.AXIS, axis), null);
        } else if (block.isIn(MidnightTags.Blocks.FUNGI_STEMS)) {
            return new Template.BlockInfo(pos, this.log, null);
        } else if (block instanceof LeavesBlock || block instanceof MidnightFungiHatBlock) {
            return new Template.BlockInfo(pos, this.leaf, null);
        } else if (block == Blocks.STRUCTURE_BLOCK || block instanceof AirBlock) {
            return null;
        }
        return info;
    }

    protected void processMarker(IWorld world, BlockPos pos, String key) {
        if (key.equals(ANCHOR_KEY) || key.equals(TRUNK_TOP_KEY)) {
            this.setBlockState(world, pos, this.log);
        }
    }
}
