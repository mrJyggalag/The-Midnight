package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
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

    private final Block[] shelfBlocks;

    public DeadTreeFeature(BlockState log, Block[] shelfBlocks) {
        super(TEMPLATES, log, Blocks.AIR.getDefaultState());
        this.shelfBlocks = shelfBlocks;
    }

    public DeadTreeFeature(Block[] shelfBlocks) {
        this(MidnightBlocks.DEAD_WOOD_LOG.getDefaultState(), shelfBlocks);
    }

    @Override
    protected TemplateCompiler buildCompiler(ResourceLocation[] templates) {
        return super.buildCompiler(templates)
                .withPostProcessor(new ShelfAttachProcessor(this::canReplace, this.shelfBlocks));
    }
}
