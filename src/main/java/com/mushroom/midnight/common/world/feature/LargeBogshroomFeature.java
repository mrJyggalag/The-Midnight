package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LargeBogshroomFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/bogshroom")
    };

    public LargeBogshroomFeature(BlockState log, BlockState leaves) {
        super(TEMPLATES, log, leaves);
    }

    public LargeBogshroomFeature() {
        this(MidnightBlocks.BOGSHROOM_STEM.getDefaultState(), MidnightBlocks.BOGSHROOM_HAT.getDefaultState());
    }

    @Override
    protected TemplateCompiler buildCompiler(ResourceLocation[] templates) {
        return super.buildCompiler(templates)
                .withPostProcessor(new ShelfAttachProcessor(this::canPlaceShelf, ShelfAttachProcessor.SHELF_BLOCKS));
    }

    @Override
    protected void processData(World world, BlockPos pos, String key) {
        if (key.equals("inside")) {
            world.setBlockState(pos, MidnightBlocks.MUSHROOM_INDist.getDefaultState(), 2 | 16);
        }
        super.processData(world, pos, key);
    }

    private boolean canPlaceShelf(World world, BlockPos pos) {
        if (world.isOutsideBuildHeight(pos)) {
            return false;
        }

        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == MidnightBlocks.MUSHROOM_INSIDE) {
            return false;
        }

        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getMaterial() == Material.VINE;
    }
}
