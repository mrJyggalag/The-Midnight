package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class DeadTreeFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_1"),
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_2"),
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_3"),
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_4"),
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_5"),
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_6")
    };

    public DeadTreeFeature(IBlockState log) {
        super(TEMPLATES, log, Blocks.AIR.getDefaultState());
    }

    public DeadTreeFeature() {
        this(ModBlocks.DEAD_WOOD_LOG.getDefaultState());
    }

    @Override
    protected TemplateCompiler buildCompiler(ResourceLocation[] templates) {
        return super.buildCompiler(templates)
                .withPostProcessor(new ShelfAttachProcessor(this::canReplace, ShelfAttachProcessor.SHELF_BLOCKS));
    }
}
