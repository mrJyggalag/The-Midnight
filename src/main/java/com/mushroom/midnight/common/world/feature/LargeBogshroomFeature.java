package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.feature.config.TemplateTreeConfig;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.function.Function;

public class LargeBogshroomFeature extends TemplateTreeFeature {
    public LargeBogshroomFeature(Function<Dynamic<?>, ? extends TemplateTreeConfig> deserialize) {
        super(deserialize);
    }

    @Override
    protected TemplateCompiler buildCompiler(TemplateTreeConfig config) {
        return super.buildCompiler(config)
                .withPostProcessor(new ShelfAttachProcessor(this::canPlaceShelf, ShelfAttachProcessor.SHELF_BLOCKS));
    }

    @Override
    protected void processMarker(IWorld world, BlockPos pos, String key, TemplateTreeConfig config) {
        if (key.equals("inside")) {
            this.setBlockState(world, pos, MidnightBlocks.MUSHROOM_INSIDE.getDefaultState());
        }
        super.processMarker(world, pos, key, config);
    }

    private boolean canPlaceShelf(IWorld world, BlockPos pos) {
        if (World.isOutsideBuildHeight(pos)) {
            return false;
        }

        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == MidnightBlocks.MUSHROOM_INSIDE) {
            return false;
        }

        return state.getBlock().isAir(state, world, pos) || state.isIn(BlockTags.LEAVES) || state.getMaterial() == Material.PLANTS;
    }
}
