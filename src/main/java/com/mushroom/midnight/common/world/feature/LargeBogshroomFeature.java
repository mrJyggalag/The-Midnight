package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LargeBogshroomFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/bogshroom")
    };

    public LargeBogshroomFeature(IBlockState log, IBlockState leaves) {
        super(TEMPLATES, log, leaves);
    }

    public LargeBogshroomFeature() {
        this(ModBlocks.BOGSHROOM_STEM.getDefaultState(), ModBlocks.BOGSHROOM_HAT.getDefaultState());
    }

    @Override
    protected TemplateCompiler buildCompiler(ResourceLocation[] templates) {
        return super.buildCompiler(templates)
                .withPostProcessor(new ShelfAttachProcessor(this::canPlaceShelf));
    }

    @Override
    protected void processData(World world, BlockPos pos, String key) {
        if (key.equals("inside")) {
            world.setBlockState(pos, ModBlocks.MUSHROOM_INSIDE.getDefaultState(), 2 | 16);
        }
        super.processData(world, pos, key);
    }

    private boolean canPlaceShelf(World world, BlockPos pos) {
        if (world.isOutsideBuildHeight(pos)) {
            return false;
        }

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == ModBlocks.MUSHROOM_INSIDE) {
            return false;
        }

        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getMaterial() == Material.VINE;
    }
}
